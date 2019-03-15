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
//	}      //测试用main方法

class updateFlight1 {

	String ID_1, arrivalTime_1, startCity_1, arrivalCity_1, price_1, currentPassengers_1, seatCapacity_1, status_1, sql;
	Date startTime_1;
	int number, result;

	public updateFlight1() {
		passenger_1.checkStatus();
		updateFlight_0();
		if (!(status_1 == null)) {

			System.out.println("航班号：" + ID_1 + "  起飞时间：" + startTime_1 + "  到达时间：" + arrivalTime_1 + "  起飞城市："
					+ startCity_1 + "  到达城市：" + arrivalCity_1 + "  价格" + price_1 + "  已订人数" + currentPassengers_1
					+ "   额定载量：" + seatCapacity_1 + "   航班状态：" + status_1);

			Date date = new Date();
			Date dateNow = new Date();

			if (status_1.equals("UNPUBLISHED")) {
				updateFlight_1();
			} else if (status_1.equals("AVAILABLE") || status_1.equals("FULL")) {
				updateFlight_1(1);
			} else {
				System.out.println("终止航班不可修改");
			}
		} else {
			System.out.println("没有查询到您所输入的航班");
		}
	}

	public void updateFlight_0() {

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			Scanner input = new Scanner(System.in);
			System.out.println("请输入您所需要修改的航班的ID");
			String flightID = input.next();

			String query = "select * from flight where flightID = '" + flightID + "'";
			ResultSet result = stmt.executeQuery(query);

			if (result.next()) {

				ID_1 = result.getString("flightID");
				startTime_1 = result.getTimestamp("startTime");// result.getTimestamp("startTime");从数据库中取出精确时间
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
		System.out.println("请输入您想要修改的内容的代号");
		System.out.println("1.航班号 2.起飞时间 3.到达时间 4.起飞城市 5.到达城市 6.价格 7.额定载量");
		Scanner input = new Scanner(System.in);

		try {
			number = input.nextInt();

			System.out.println("请输入修改内容");

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
						System.out.println("请输入正确的修改内容");
					}

				} else {
					System.out.println("请输入正确的内容代号和内容");
				}

				System.out.println("改变了" + result + "条航班数据");

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
			System.out.println("请输入正确的操作序号");
		}

	}

	public void updateFlight_1(int i) {
		System.out.println("请输入您想要修改内容的操作序号");
		System.out.println("1.价格 2.额定载量");
		Scanner input = new Scanner(System.in);

		try {
			number = input.nextInt();

			System.out.println("请输入修改内容");
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
						System.out.println("请输入正确的操作序号和内容");
					}

					int result = stmt.executeUpdate(sql);
					System.out.println("改变了" + result + "条航班数据");

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
				System.out.println("请输入正确的修改内容");
			}
		} catch (Exception e) {
			System.out.println("请输入正确的操作序号");
		}

	}

}
