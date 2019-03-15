package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;
 
public class reserveFlight {

	String sql, passengerID, flightID, createDate, orderStatus, realName, identityID, password;
	int seat;

	public void passengerSignUp() {

		System.out.println("�������û��������֤�ţ�");
		Scanner input = new Scanner(System.in);
		identityID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from passenger where identityID = '" + identityID + "'";
			ResultSet result = stmt.executeQuery(sql);

			if (!result.next()) {

				System.out.println("����������");
				password = input.next();

				System.out.println("������������ʵ����");
				realName = input.next();

				sql = "insert passenger values(NULL,'" + realName + "','" + identityID + "','" + password + "')";
				int result_int = stmt.executeUpdate(sql);
				System.out.println("������" + result_int + "�г˿�����");

			} else {
				System.out.println("�û���ע��");
			}

			result.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void passengerLogin() {
		System.out.println("�������û��������֤�ţ�");
		Scanner input = new Scanner(System.in);
		identityID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from passenger where identityID = '" + identityID + "'";
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {
				identityID = result.getString("identityID");
				passengerID = result.getString("passengerID");
				realName = result.getString("realName");
				String password = result.getString("password");

				System.out.println("����������");
				this.password = input.next();

				if (this.password.equals(password)) {
					System.out.println(realName + "�����ã�");
					passenger_1.createFlight_every();
					passenger_1.checkStatus();

					while (true) {
						System.out.println("����������Ҫ���еĲ������");
						System.out.println("1.��ѯ����  2.Ԥ������  3.�˶�����  4.�ҵĶ���  5.�޸�����  6.����");
						String number = input.next();
						if (number.equals("1")) {
							queryFlight1 queryFlight = new queryFlight1();
						} else if (number.equals("2")) {
							reserveFlight_();
						} else if (number.equals("3")) {
							unsubscribeFlight();
						} else if (number.equals("4")) {
							myFlight();
						} else if (number.equals("5")) {
							updatePassword();
						} else if (number.equals("6")) {
							break;
						} else {
							System.out.println("��������ȷ�������");
						}
					}

				} else {
					System.out.println("�������");
				}

			} else {
				System.out.println("��������ȷ�Ĳ������");
			}

			result.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reserveFlight_() {
		System.out.println("����������ҪԤ���ĺ����");
		Scanner input = new Scanner(System.in);
		flightID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from flight where flightid = '" + flightID + "'";
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {
				int price = result.getInt("price");
				int currentPassengers = result.getInt("currentPassengers");
				int seatCapacity = result.getInt("seatCapacity");
				String status = result.getString("flightStatus");
				Timestamp StartTime = result.getTimestamp("startTime");
				Date date = new Date();

				if (status.equals("AVAILABLE") && currentPassengers < seatCapacity
						&& (StartTime.getTime() - date.getTime()) > 0) {
					// ��������������һ��
					System.out.println("��֧��" + price + "Ԫ");
					System.out.println("֧��������'Y'");
					String buyStatus = input.next();
					if (buyStatus.equals("Y")) {
						System.out.println("��ȷ��֧��");

						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
						createDate = df.format(new Date()); // new
															// Date()Ϊ��ȡ��ǰϵͳʱ��
						System.out.println(createDate);

						sql = "select seat from flightOrder where flightID ='" + flightID + "'";
						ResultSet resultSeat = stmt.executeQuery(sql);

						ArrayList seatList = new ArrayList();
						while (resultSeat.next()) {
							int tempSeat = resultSeat.getInt("seat");
							seatList.add(tempSeat);
						}
						for (int i = 1; i <= seatCapacity; i++) {
							if (!seatList.contains(i)) {
								seat = i;
							}
						}

						sql = "insert flightOrder values(NULL,'" + passengerID + "','" + seat + "','" + flightID + "','"
								+ createDate + "','UNPAID')";
						int result_int = stmt.executeUpdate(sql);
						System.out.println("������" + result_int + "�к��ඩ������");

						sql = "update flightOrder set orderStatus = 'PAID' where passengerID = '" + passengerID + "' "
								+ "and createDate ='" + createDate + "'";
						result_int = stmt.executeUpdate(sql);
						if (result_int == 1) {
							System.out.println("����״̬�Ѹ���ΪPAID");
						}

						// ������������ĵ�ǰ�Ѷ�����,�ж��Ƿ���ڶ���������ĺ���״̬��
						sql = "update flight set currentPassengers = currentPassengers + 1 where flightid = '"
								+ flightID + "'";
						result_int = stmt.executeUpdate(sql);
						System.out.println("�ı���" + result_int + "������ĵ�ǰ��������");

						if (seatCapacity == (currentPassengers + 1)) {
							sql = "update flight set flightStatus = 'FULL' where flightid = '" + flightID + "'";
							stmt.executeUpdate(sql);
						} // ����ǰ�˿������һ�����ı亽��״̬Ϊ�Ѷ���

					} else {
						System.out.println("��δ֧��������Ԥ��ʧ��,������Ԥ������");
					}

				} else {
					System.out.println("�ú��಻��Ԥ��");
				}

			} else {
				System.out.println("������ȷ�����");
			}

			result.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void unsubscribeFlight() {
		System.out.println("����������Ҫ�˶��ĺ����");
		Scanner input = new Scanner(System.in);
		flightID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from flightorder where flightid = '" + flightID + "' and passengerID = '" + passengerID
					+ "'";
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {

				sql = "select * from flight where flightID ='" + flightID + "'";
				ResultSet resultStartTime = stmt.executeQuery(sql);
				int currentPassengers = 0, seatCapacity = 0, price = 0;

				Date date = new Date();
				Date dateNow = new Date();

				if (resultStartTime.next()) {
					currentPassengers = resultStartTime.getInt("currentPassengers");
					seatCapacity = resultStartTime.getInt("seatCapacity");
					price = resultStartTime.getInt("price");
					Timestamp StartTime = resultStartTime.getTimestamp("startTime");
					date = StartTime;

				}

				if ((date.getTime() - dateNow.getTime()) > 0) {

					System.out.println("�����˿�Ϊ" + price + "Ԫ");
					System.out.println("�˿��ѷ���");

					// ��������������һ��
					sql = "update flightorder set  orderstatus = 'CANCEL'where flightid = '" + flightID
							+ "' and passengerID = '" + passengerID + "'";
					int result_int = stmt.executeUpdate(sql);
					System.out.println("ȡ��" + result_int + "�к����Ϊ" + flightID + "�˿�IDΪ" + passengerID + "�ĺ��ඩ������");

					// ������������ĵ�ǰ�Ѷ�����,�ж��Ƿ���Ҫ���ĺ���״̬
					sql = "update flight set currentPassengers = currentPassengers - 1 where flightid = '" + flightID
							+ "'";
					result_int = stmt.executeUpdate(sql);
					System.out.println("������" + result_int + "������ĵ�ǰ��������");

					if (seatCapacity == currentPassengers) {
						sql = "update flight set flightStatus = 'AVAILABLE' where flightid = '" + flightID + "'";
						stmt.executeUpdate(sql);
					} // ����ǰ�˿������һ�����ı亽��״̬Ϊ��Ԥ��

				} else {
					System.out.println("�ɻ��Ѿ���ɣ����ɶ���");
				}

			} else {
				System.out.println("������ȷ�����");
			}

			result.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void myFlight() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from flightOrder where passengerID = '" + passengerID + "'";
			ResultSet result = stmt.executeQuery(sql);

			while (result.next()) {
				Timestamp createDate = result.getTimestamp("createDate");
				flightID = result.getString("flightID");
				orderStatus = result.getString("orderStatus");
				seat = result.getInt("seat");

				System.out.println(
						"����ţ�" + flightID + "  ��λ�ţ�" + seat + "  Ԥ��ʱ�䣺" + createDate + "  ����״̬��" + orderStatus);

			}
			result.beforeFirst(); // ��������α�ص����λ��
			if (!result.next()) {
				System.out.println("��û�ж���");
			} else {
				System.out.println("�����Ը��ݺ���Ų�ѯ�ú����������Ϣ");
			}

			result.close();
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updatePassword() {

		System.out.println("������������");
		Scanner input = new Scanner(System.in);
		String password = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "update passenger set password = '" + password + "' where identityID = '" + identityID + "'";

			int result = stmt.executeUpdate(sql);

			System.out.println("�ı���" + result + "����������");

			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
