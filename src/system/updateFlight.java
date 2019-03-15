package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;
 
//public class updateFlight {
//	public static void main(String[] args) {	
//		updateFlight1 up =new updateFlight1();
//		}
//	}      //������main����

class updateFlight1 {

	String ID_1, arrivalTime_1, startCity_1, arrivalCity_1, price_1, currentPassengers_1, seatCapacity_1, status_1, sql;
	Date startTime_1;
	int number, result;

	public updateFlight1() {
		passenger_1.checkStatus();
		updateFlight_0();
		if (!(status_1 == null)) {

			System.out.println("����ţ�" + ID_1 + "  ���ʱ�䣺" + startTime_1 + "  ����ʱ�䣺" + arrivalTime_1 + "  ��ɳ��У�"
					+ startCity_1 + "  ������У�" + arrivalCity_1 + "  �۸�" + price_1 + "  �Ѷ�����" + currentPassengers_1
					+ "   �������" + seatCapacity_1 + "   ����״̬��" + status_1);

			Date date = new Date();
			Date dateNow = new Date();

			if (status_1.equals("UNPUBLISHED")) {
				updateFlight_1();
			} else if (status_1.equals("AVAILABLE") || status_1.equals("FULL")) {
				updateFlight_1(1);
			} else {
				System.out.println("��ֹ���಻���޸�");
			}
		} else {
			System.out.println("û�в�ѯ����������ĺ���");
		}
	}

	public void updateFlight_0() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			Scanner input = new Scanner(System.in);
			System.out.println("������������Ҫ�޸ĵĺ����ID");
			String flightID = input.next();

			String query = "select * from flight where flightID = '" + flightID + "'";
			ResultSet result = stmt.executeQuery(query);

			if (result.next()) {

				ID_1 = result.getString("flightID");
				startTime_1 = result.getTimestamp("startTime");// result.getTimestamp("startTime");�����ݿ���ȡ����ȷʱ��
				arrivalTime_1 = result.getString("arrivalTime");
				startCity_1 = result.getString("startCity");
				arrivalCity_1 = result.getString("arrivalCity");
				price_1 = result.getString("price");
				currentPassengers_1 = result.getString("currentPassengers");
				seatCapacity_1 = result.getString("seatCapacity");
				status_1 = result.getString("flightstatus");
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

	public void updateFlight_1() {
		System.out.println("����������Ҫ�޸ĵ����ݵĴ���");
		System.out.println("1.����� 2.���ʱ�� 3.����ʱ�� 4.��ɳ��� 5.������� 6.�۸� 7.�����");
		Scanner input = new Scanner(System.in);

		try {
			number = input.nextInt();

			System.out.println("�������޸�����");

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = DriverManager.getConnection(
						"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
				Statement stmt = conn.createStatement();

				if (number < 6 && number > 0) {

					String string = input.next();

					if (number == 1) {
						sql = "update flight set flightid = '" + string + "' where flightid = '" + ID_1 + "'";
					} else if (number == 2) {
						sql = "update flight set startTime = '" + string + "' where flightid = '" + ID_1 + "'";
					} else if (number == 3) {
						sql = "update flight set arrivalTime = '" + string + "' where flightid = '" + ID_1 + "'";
					} else if (number == 4) {
						sql = "update flight set startCity = '" + string + "' where flightid = '" + ID_1 + "'";
					} else if (number == 5) {
						sql = "update flight set arrivalCity = '" + string + "' where flightid = '" + ID_1 + "'";
					}

					result = stmt.executeUpdate(sql);

				} else if (number < 8 && number > 5) {
					try {
						int string = input.nextInt();
						if (number == 6) {
							sql = "update flight set price = '" + string + "' where flightid = '" + ID_1 + "'";
						} else if (number == 7) {
							sql = "update flight set seatCapacity = '" + string + "' where flightid = '" + ID_1 + "'";
						} else {
						}
						result = stmt.executeUpdate(sql);
					} catch (Exception e) {
						System.out.println("��������ȷ���޸�����");
					}

				} else {
					System.out.println("��������ȷ�����ݴ��ź�����");
				}

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
		} catch (Exception e) {
			System.out.println("��������ȷ�Ĳ������");
		}

	}

	public void updateFlight_1(int i) {
		System.out.println("����������Ҫ�޸����ݵĲ������");
		System.out.println("1.�۸� 2.�����");
		Scanner input = new Scanner(System.in);

		try {
			number = input.nextInt();

			System.out.println("�������޸�����");
			try {
				int string = input.nextInt();

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = DriverManager.getConnection(
							"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
					Statement stmt = conn.createStatement();

					if (number == 1) {
						sql = "update flight set price = '" + string + "' where flightid = '" + ID_1 + "'";
					} else if (number == 2) {
						sql = "update flight set seatCapacity = '" + string + "' where flightid = '" + ID_1 + "'";
					} else {
						System.out.println("��������ȷ�Ĳ�����ź�����");
					}

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
			} catch (Exception e) {
				System.out.println("��������ȷ���޸�����");
			}
		} catch (Exception e) {
			System.out.println("��������ȷ�Ĳ������");
		}

	}

}
