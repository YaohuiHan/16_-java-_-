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
		System.out.println("请输入用户名（身份证号）");
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

				System.out.println("请输入密码");
				this.password = input.next();

				if (this.password.equals(password)) {
					System.out.println(realName + "管理员，您好！");
					passenger_1.createFlight_every();
					passenger_1.checkStatus();

					while (true) {
						System.out.println("请输入您想要进行操作的序号");
						System.out.println("1.创建航班  2.修改航班  3.发布航班  4.删除航班  5.高级查询  6.普通查询  7.添加管理员  8.修改密码  9.返回");
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
							System.out.println("请输入正确的操作序号");
						}
					}

				} else {
					System.out.println("密码错误");
				}

			} else {
				System.out.println("用户不存在");
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

		System.out.println("请输入用户名（身份证号）");
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

				System.out.println("请输入密码");
				password = input.next();

				System.out.println("请输入您的真实姓名");
				realName = input.next();

				sql = "insert Admin values(NULL,'" + realName + "','" + identityID + "','" + password + "')";
				int result_int = stmt.executeUpdate(sql);
				System.out.println("增加了" + result_int + "行管理员数据");

			} else {
				System.out.println("管理员已注册");
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

		System.out.println("请输入新密码");
		Scanner input = new Scanner(System.in);
		String password = input.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement stmt = conn.createStatement();

			sql = "update admin set password = '" + password + "' where identityID = '" + identityID + "'";

			int result = stmt.executeUpdate(sql);

			System.out.println("改变了" + result + "条个人数据");

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

		System.out.println("请输入您想要查询的航班号");
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
						"姓名：" + realName_ + "  座位号：" + seat + "  预定时间：" + createDate + "  订单状态：" + orderStatus);

			}
			result.beforeFirst(); // 结果集的游标回到最初位置
			if (!result.next()) {
				System.out.println("此航班号没有订单");
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
		System.out.println("请输入您想要发布的航班号");
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
					System.out.println("发布了" + result_int + "行航班数据");
				} else {
					System.out.println("航班不可发布");
				}

			} else {
				System.out.println("请输正确航班号");
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
