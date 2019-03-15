package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

public class Admin {

	String sql, AdminID, realName, identityID, password;

	public Admin() {
		System.out.println("�������û��������֤�ţ�");
		Scanner input = new Scanner(System.in);
		identityID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from Admin where identityID = '" + identityID + "'";
			ResultSet result = stmt.executeQuery(sql);

			if (result.next()) {
				identityID = result.getString("identityID");
				AdminID = result.getString("AdminID");
				realName = result.getString("realName");
				String password = result.getString("password");

				System.out.println("����������");
				this.password = input.next();

				if (this.password.equals(password)) {
					System.out.println(realName + "����Ա�����ã�");
					passenger_1.createFlight_every();
					passenger_1.checkStatus();

					while (true) {
						System.out.println("����������Ҫ���в��������");
						System.out.println("1.��������  2.�޸ĺ���  3.��������  4.ɾ������  5.�߼���ѯ  6.��ͨ��ѯ  7.��ӹ���Ա  8.�޸�����  9.����");
						String number = input.next();
						if (number.equals("1")) {
							createFlight1 kk = new createFlight1();
						} else if (number.equals("2")) {
							updateFlight1 up = new updateFlight1();
						} else if (number.equals("3")) {
							announceFlight();
						} else if (number.equals("4")) {
							deleteFlight deleteFlight = new deleteFlight();
						} else if (number.equals("5")) {
							superQuery();
						} else if (number.equals("6")) {
							queryFlight1 queryFlight = new queryFlight1();
						} else if (number.equals("7")) {
							AdminSignUp();
						} else if (number.equals("8")) {
							updatePassword();
						} else if (number.equals("9")) {
							break;
						} else {
							System.out.println("��������ȷ�Ĳ������");
						}
					}

				} else {
					System.out.println("�������");
				}

			} else {
				System.out.println("�û�������");
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

	public void AdminSignUp() {

		System.out.println("�������û��������֤�ţ�");
		Scanner input = new Scanner(System.in);
		identityID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "select * from Admin where identityID = '" + identityID + "'";
			ResultSet result = stmt.executeQuery(sql);

			if (!result.next()) {

				System.out.println("����������");
				password = input.next();

				System.out.println("������������ʵ����");
				realName = input.next();

				sql = "insert Admin values(NULL,'" + realName + "','" + identityID + "','" + password + "')";
				int result_int = stmt.executeUpdate(sql);
				System.out.println("������" + result_int + "�й���Ա����");

			} else {
				System.out.println("����Ա��ע��");
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

			sql = "update admin set password = '" + password + "' where identityID = '" + identityID + "'";

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

	public void superQuery() {

		System.out.println("����������Ҫ��ѯ�ĺ����");
		Scanner input = new Scanner(System.in);
		String flightID = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();
			Statement stmt1 = conn.createStatement();

			sql = "select * from flightOrder where flightID = '" + flightID + "'";
			ResultSet result = stmt.executeQuery(sql);

			String realName_ = null;
			ResultSet resultPassenger = null;
			while (result.next()) {

				Timestamp createDate = result.getTimestamp("createDate");
				String orderStatus = result.getString("orderStatus");
				int seat = result.getInt("seat");
				int passengerID = result.getInt("passengerID");

				sql = "select * from passenger where passengerID = '" + passengerID + "'";
				resultPassenger = stmt1.executeQuery(sql);
				if (resultPassenger.next()) {
					realName_ = resultPassenger.getString("realName");
				}

				System.out.println(
						"������" + realName_ + "  ��λ�ţ�" + seat + "  Ԥ��ʱ�䣺" + createDate + "  ����״̬��" + orderStatus);

			}
			result.beforeFirst(); // ��������α�ص����λ��
			if (!result.next()) {
				System.out.println("�˺����û�ж���");
			} else {
				resultPassenger.close();
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

	public void announceFlight() {
		System.out.println("����������Ҫ�����ĺ����");
		Scanner input = new Scanner(System.in);
		String string = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			String query = "select * from flight where flightID = '" + string + "'";
			ResultSet result = stmt.executeQuery(query);

			if (result.next()) {

				String StartTime = result.getString("flightstatus");

				if (StartTime.equals("UNPUBLISHED")) {

					String sql = "update flight set flightStatus = 'AVAILABLE' where flightid ='" + string + "'";
					int result_int = stmt.executeUpdate(sql);
					System.out.println("������" + result_int + "�к�������");
				} else {
					System.out.println("���಻�ɷ���");
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

}
