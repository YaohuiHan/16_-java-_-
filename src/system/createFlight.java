package system;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
 
//public class createFlight{
//	public static void main(String[] args){
//		createFlight1 kk = new createFlight1();
//	}
//}//������ main����

class createFlight1 extends JFrame implements ActionListener {

	JTextField ID, startTime, arrivalTime, startCity, arrivalCity, price, currentPassengers, seatCapacity;

	JButton b1, b2, b3;

	String ID_, startTime_, arrivalTime_, startCity_, arrivalCity_, price_, currentPassengers_, seatCapacity_;

	public createFlight1() {
		Container c = this.getContentPane();
		c.setLayout(null);
		JLabel label = new JLabel("¼�뺽����Ϣ");
		label.setFont(new Font("TRUE", Font.TRUETYPE_FONT, 20));
		label.setBounds(190, 15, 500, 100);
		c.add(label);
		ID = new JTextField(15);
		ID.setBounds(220, 115, 125, 15);
		startTime = new JTextField(15);
		startTime.setBounds(220, 140, 125, 15);
		arrivalTime = new JTextField(15);
		arrivalTime.setBounds(220, 165, 125, 15);
		startCity = new JTextField(15);
		startCity.setBounds(220, 190, 125, 15);
		arrivalCity = new JTextField(15);
		arrivalCity.setBounds(220, 215, 125, 15);
		price = new JTextField(15);
		price.setBounds(220, 240, 125, 15);
		seatCapacity = new JTextField(15);
		seatCapacity.setBounds(220, 265, 125, 15);

		c.add(ID);
		c.add(startTime);
		c.add(arrivalTime);
		c.add(startCity);
		c.add(arrivalCity);
		c.add(price);
		c.add(seatCapacity);

		JLabel label1 = new JLabel("�����");
		label1.setBounds(150, 72, 100, 100);
		JLabel label2 = new JLabel("���ʱ��");
		label2.setBounds(150, 97, 500, 100);
		JLabel label3 = new JLabel("����ʱ��");
		label3.setBounds(150, 122, 500, 100);
		JLabel label4 = new JLabel("��ɳ���");
		label4.setBounds(150, 147, 500, 100);
		JLabel label5 = new JLabel("�������");
		label5.setBounds(150, 172, 500, 100);
		JLabel label7 = new JLabel("�۸�");
		label7.setBounds(150, 197, 500, 100);
		JLabel label9 = new JLabel("����");
		label9.setBounds(150, 222, 500, 100);

		c.add(label1);
		c.add(label2);
		c.add(label3);
		c.add(label4);
		c.add(label5);
		c.add(label7);
		c.add(label9);

		b1 = new JButton("¼��");
		b1.setBounds(100, 320, 100, 30);
		b2 = new JButton("���");
		b2.setBounds(200, 320, 100, 30);
		b3 = new JButton("�˳�");
		b3.setBounds(300, 320, 100, 30);

		c.add(b1);
		c.add(b2);
		c.add(b3);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		this.setBounds(400, 100, 500, 500);
		this.setVisible(true);
		this.setTitle("¼������Ϣ");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			addFI();
		}
		if (e.getSource() == b2) {
			clearForm();
		}
		if (e.getSource() == b3) {
			shutdown();
		}
	}

	private void addFI() {
		ID_ = ID.getText();
		startTime_ = startTime.getText();
		arrivalTime_ = arrivalTime.getText();
		startCity_ = startCity.getText();
		arrivalCity_ = arrivalCity.getText();
		price_ = price.getText();
		seatCapacity_ = seatCapacity.getText();

		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sdf.format(date); // ��ǰʱ��
			if (ID_.length() == 0 || startTime_.length() == 0 || arrivalTime_.length() == 0 || startCity_.length() == 0
					|| arrivalCity_.length() == 0 || price_.length() == 0 || seatCapacity_.length() == 0) {
				JOptionPane.showMessageDialog(this, "�����������Ϣ");
			} else if (TimeCompare.twoStringTime(time, startTime_) < 7200) {
				JOptionPane.showMessageDialog(this, "��ȷ�����ʱ���ڵ�ǰʱ����Сʱ֮��");
			} else if (TimeCompare.twoStringTime(startTime_, arrivalTime_) < 1) {
				JOptionPane.showMessageDialog(this, "��ȷ������ʱ�������ʱ��֮��");
			} else if (Integer.parseInt(price_) < 1) {
				JOptionPane.showMessageDialog(this, "��ȷ���۸����0");
			} else if (Integer.parseInt(seatCapacity_) < 1) {
				JOptionPane.showMessageDialog(this, "��ȷ����������0");
			} else {
				// ��ʼ��������ʱ����Ԥ������һ��Ϊ0
				// �����ݿ���ȡ����Ϣ���Ƚ�
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
				Statement stmt = conn.createStatement();
				// �ظ��ж�
				String query = "select * from flight where flightid = '" + ID_ + "'";
				ResultSet result = stmt.executeQuery(query);
				if (result.next())
					JOptionPane.showMessageDialog(this, "¼������Ϣ�ظ�");
				else {
					stmt.executeUpdate("insert flight values('" + ID_ + "','" + startTime_ + "','" + arrivalTime_
							+ "','" + startCity_ + "','" + arrivalCity_ + "','" + price_ + "','0','" + seatCapacity_
							+ "','UNPUBLISHED')");
					JOptionPane.showMessageDialog(this, "¼������Ϣ�ɹ�");
					this.dispose();
				}
				result.close();
				stmt.close();
				conn.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "ʱ���������ʽΪyyyy-MM-dd HH:mm:ss");
		}
	}

	private void clearForm() {
		ID.setText("");
		startTime.setText("");
		arrivalTime.setText("");
		startCity.setText("");
		arrivalCity.setText("");
		price.setText("");
		seatCapacity.setText("");
	}

	private void shutdown() {
		this.dispose();
	}
}
