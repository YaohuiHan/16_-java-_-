package system;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import javax.swing.*;
 
//public class queryFlight {
//	public static void main(String[] args) {
//		queryFlight1 queryFlight = new queryFlight1();
//	}
//}    //������main����

class queryFlight1 extends JFrame implements ActionListener {

	ArrayList<TempList> tempList;
	TempList temp;
	JTextField ID, startTime, arrivalTime, startCity, arrivalCity, price, flightStatus;
	JPanel content1, content2, content2_2, content3, content4;
	JButton b1, b2, b3;
	String ID_, startTime_, arrivalTime_, startCity_, arrivalCity_, price_, flightStatus_;
	String ID_2, startTime_2, arrivalTime_2, startCity_2, arrivalCity_2, price_2, flightStatus_2;
	int people_2;

	public queryFlight1() {

		Container c = this.getContentPane();

		c.setLayout(new GridLayout(4, 1)); // ���񲼾�����һ��

		content1 = new JPanel(new GridLayout(1, 8)); // ��һ�У����񲼾�һ�а���

		JLabel ID_3 = new JLabel("�����", SwingConstants.CENTER);
		JLabel startTime_3 = new JLabel("���ʱ��", SwingConstants.CENTER);
		JLabel arrivalTime_3 = new JLabel("����ʱ��", SwingConstants.CENTER);
		JLabel startCity_3 = new JLabel("��ɳ���", SwingConstants.CENTER);
		JLabel arrivalCity_3 = new JLabel("�������", SwingConstants.CENTER);
		JLabel price_3 = new JLabel("�۸�", SwingConstants.CENTER);
		JLabel Person_3 = new JLabel("ʣ��Ʊ��", SwingConstants.CENTER);

		content1.add(ID_3);
		content1.add(startTime_3);
		content1.add(arrivalTime_3);
		content1.add(startCity_3);
		content1.add(arrivalCity_3);
		content1.add(price_3);
		content1.add(Person_3);

		content2 = new JPanel(new GridLayout(2, 1));// �ڶ��У����񲼾֣�����ʾ�������������Ϣ��
		JLabel Temp1 = new JLabel("�����������Ϣ���в�ѯ", SwingConstants.CENTER);
		content2.add(Temp1);

		content3 = new JPanel(new GridLayout(2, 6));// �����У����񲼾�,���ղ�ѯ����

		ID = new JTextField(15);
		startTime = new JTextField(15);
		arrivalTime = new JTextField(15);
		startCity = new JTextField(15);
		arrivalCity = new JTextField(15);
		price = new JTextField(15);

		JLabel ID_ = new JLabel("�����", SwingConstants.CENTER);
		JLabel startTime_ = new JLabel("�˺����", SwingConstants.CENTER);
		JLabel arrivalTime_ = new JLabel("��ǰ����", SwingConstants.CENTER);
		JLabel startCity_ = new JLabel("��ɳ���", SwingConstants.CENTER);
		JLabel arrivalCity_ = new JLabel("�������", SwingConstants.CENTER);
		JLabel price_ = new JLabel("�˼۸�֮��", SwingConstants.CENTER);

		content3.add(ID_);
		content3.add(ID);
		content3.add(startTime_);
		content3.add(startTime);
		content3.add(arrivalTime_);
		content3.add(arrivalTime);
		content3.add(startCity_);
		content3.add(startCity);
		content3.add(arrivalCity_);
		content3.add(arrivalCity);
		content3.add(price_);
		content3.add(price);

		content4 = new JPanel(new GridLayout(1, 8));// ��4�У����񲼾�n�а��У�nΪȫ��������
		b1 = new JButton("��ѯ");
		b2 = new JButton("���");
		b3 = new JButton("�˳�");
		content4.add(b1);
		content4.add(b2);
		content4.add(b3);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		c.add(content3);
		c.add(content1);
		c.add(content2);
		c.add(content4);
		this.setBounds(200, 200, 800, 400);
		this.setVisible(true);
		this.setTitle("���������Ϣ");

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			query();
		}
		if (e.getSource() == b2) {
			clearForm();
		}
		if (e.getSource() == b3) {
			shutdown();
		}
	}

	private void query() {
		tempList = new ArrayList<TempList>();
		ID_ = ID.getText();
		startTime_ = startTime.getText();
		arrivalTime_ = arrivalTime.getText();
		startCity_ = startCity.getText();
		arrivalCity_ = arrivalCity.getText();
		price_ = price.getText();

		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date); // ��ǰʱ��

			if ((!(price_.equals(""))) && (!((Integer) Integer.parseInt(price_) instanceof Integer))) {
				JOptionPane.showMessageDialog(this, "�۸�������������");
			} else if (((!(startTime_.equals("")))
					&& (!(TimeFormatConversion.stringToDate(startTime_) instanceof Date)))
					|| ((!(arrivalTime_.equals("")))
							&& (!(TimeFormatConversion.stringToDate(arrivalTime_) instanceof Date)))) {
				JOptionPane.showMessageDialog(this, "ʱ���������ʽΪyyyy-MM-dd HH:mm:ss");
			}
			// ʱ���ж�
			else if ((!(startTime_.equals(""))) && (TimeCompare.twoStringTime(time, startTime_) < 1)) {

				JOptionPane.showMessageDialog(this, "��ȷ�����ʱ���ڵ�ǰʱ��֮��");
			}
			// ����0�ж�
			else if ((!(price_.equals(""))) && (Integer.parseInt(price_) < 1)) {
				JOptionPane.showMessageDialog(this, "��ȷ���۸����0");
			}

			else {
				// �����ݿ���ȡ����Ϣ���Ƚ�
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
				Statement stmt = conn.createStatement();

				String where_1, ID_1, startTime_1, arrivalTime_1, startCity_1, arrivalCity_1, price_1;

				if ((!(ID_.equals("")))) {
					ID_1 = "flightID LIKE '%" + ID_ + "%'";
				} else {
					ID_1 = "";
				}

				System.out.println(startTime_);

				if ((!(ID_.equals(""))) && (!(startTime_.equals("")))) {
					startTime_1 = "and UNIX_TIMESTAMP(startTime)>= UNIX_TIMESTAMP('" + startTime_ + "') ";
				} else if ((ID_.equals("")) && (!(startTime_.equals("")))) {
					startTime_1 = "    UNIX_TIMESTAMP(startTime)>= UNIX_TIMESTAMP('" + startTime_ + "') ";
				} else {
					startTime_1 = "";
				}

				if ((!((ID_.equals("")) && (startTime_.equals("")))) && (!(arrivalTime_.equals("")))) {
					arrivalTime_1 = "and UNIX_TIMESTAMP(arrivalTime)<= UNIX_TIMESTAMP('" + arrivalTime_ + "') ";
				} else if (((ID_.equals("")) && (startTime_.equals(""))) && (!(arrivalTime_.equals("")))) {
					arrivalTime_1 = "UNIX_TIMESTAMP(arrivalTime)<= UNIX_TIMESTAMP('" + arrivalTime_ + "') ";
				} else {
					arrivalTime_1 = "";
				}

				if ((!((ID_.equals("")) && (startTime_.equals("")) && (arrivalTime_.equals(""))))
						&& (!(startCity_.equals("")))) {
					startCity_1 = "and startCity='" + startCity_ + "'";
				} else if (((ID_.equals("")) && (startTime_.equals("")) && (arrivalTime_.equals("")))
						&& (!(startCity_.equals("")))) {
					startCity_1 = "startCity='" + startCity_ + "'";
				} else {
					startCity_1 = "";
				}

				if ((!((ID_.equals("")) && (startTime_.equals("")) && (startCity_.equals(""))
						&& (arrivalTime_.equals("")))) && (!(arrivalCity_.equals("")))) {
					arrivalCity_1 = "and arrivalCity='" + arrivalCity_ + "'";
				} else if (((ID_.equals("")) && (startTime_.equals("")) && (startCity_.equals(""))
						&& (arrivalTime_.equals(""))) && (!(arrivalCity_.equals("")))) {
					arrivalCity_1 = "arrivalCity='" + arrivalCity_ + "'";
				} else {
					arrivalCity_1 = "";
				}

				if ((!((ID_.equals("")) && (startTime_.equals("")) && (arrivalCity_.equals(""))
						&& (startCity_.equals("")) && (arrivalTime_.equals("")))) && (!(price_.equals("")))) {
					price_1 = "and price<'" + price_ + "'";
				} else if (((ID_.equals("")) && (startTime_.equals("")) && (arrivalCity_.equals(""))
						&& (startCity_.equals("")) && (arrivalTime_.equals(""))) && (!(price_.equals("")))) {
					price_1 = "price<'" + price_ + "'";
				} else {
					price_1 = "";
				}

				if ((arrivalCity_.equals("")) && (startCity_.equals("")) && (arrivalTime_.equals(""))
						&& (startTime_.equals("")) && (ID_.equals("")) && (price_.equals(""))) {
					where_1 = "";
				} else {
					where_1 = " where ";
				}

				String query = "select * from flight " + where_1 + ID_1 + startTime_1 + arrivalTime_1 + startCity_1
						+ arrivalCity_1 + price_1;
				ResultSet result = stmt.executeQuery(query);

				while (result.next()) {
					ID_2 = result.getString("flightID");
					startTime_2 = result.getString("startTime");// result.getTimestamp("startTime");�����ݿ���ȡ����ȷʱ��
					arrivalTime_2 = result.getString("arrivalTime");
					startCity_2 = result.getString("startCity");
					arrivalCity_2 = result.getString("arrivalCity");
					price_2 = result.getString("price");
					flightStatus_2 = result.getString("flightStatus");
					people_2 = result.getInt("seatCapacity") - result.getInt("currentPassengers");

					tempList.add(new TempList(ID_2, startTime_2, arrivalTime_2, startCity_2, arrivalCity_2, price_2,
							people_2, flightStatus_2));

				}

				for (int i = 0; i < tempList.size(); i++) {
					TempList user = (TempList) tempList.get(i);
					System.out.println("����ţ�" + user.getID() + "  ���ʱ�䣺" + user.getStartTime() + "  ����ʱ�䣺"
							+ user.getArrivalTime() + "  ��ɳ��У�" + user.getStartCity() + "  ������У�"
							+ user.getArrivalCity() + "  �۸�" + user.getPrice() + "  ʣ��Ʊ����" + user.getPeople_2()
							+ "  ����״̬��" + user.getFlightStatus());
				}
				result.beforeFirst(); // ��������α�ص����λ��
				if (!result.next()) {
					JOptionPane.showMessageDialog(this, "δ��ѯ����Ϣ");
				} else {
					JOptionPane.showMessageDialog(this, "��ѯ����ڿ���̨��ʾTAT");
				}
				result.close();
				stmt.close();
				conn.close();
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearForm() {
		ID.setText("");
		startTime.setText("");
		arrivalTime.setText("");
		startCity.setText("");
		arrivalCity.setText("");
		price.setText("");
	}

	private void shutdown() {
		this.dispose();
		JOptionPane.showMessageDialog(this, "���ڿ���ֱ̨����������Ҫ���еĲ������");
	}

}

class TempList {
	private String ID, startTime, arrivalTime, startCity, arrivalCity, price, flightStatus;
	private int people_2;

	public TempList(String ID, String startTime, String arrivalTime, String startCity, String arrivalCity, String price,
			int people_2, String flightStatus) {
		this.ID = ID;
		this.startTime = startTime;
		this.arrivalTime = arrivalTime;
		this.startCity = startCity;
		this.arrivalCity = arrivalCity;
		this.price = price;
		this.people_2 = people_2;
		this.flightStatus = flightStatus;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		this.ID = iD;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStartCity() {
		return startCity;
	}

	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getPeople_2() {
		return people_2;
	}

	public void setPeople_2(int people_2) {
		this.people_2 = people_2;
	}

}
