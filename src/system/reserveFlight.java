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

		System.out.println("请输入用户名（身份证号）");
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

				System.out.println("请输入密码");
				password = input.next();

				System.out.println("请输入您的真实姓名");
				realName = input.next();

				sql = "insert passenger values(NULL,'" + realName + "','" + identityID + "','" + password + "')";
				int result_int = stmt.executeUpdate(sql);
				System.out.println("增加了" + result_int + "行乘客数据");

			} else {
				System.out.println("用户已注册");
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
		System.out.println("请输入用户名（身份证号）");
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

				System.out.println("请输入密码");
				this.password = input.next();

				if (this.password.equals(password)) {
					System.out.println(realName + "，您好！");
					passenger_1.createFlight_every();
					passenger_1.checkStatus();

					while (true) {
						System.out.println("请输入您想要进行的操作序号");
						System.out.println("1.查询航班  2.预订航班  3.退订航班  4.我的订单  5.修改密码  6.返回");
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
							System.out.println("请输入正确操作序号");
						}
					}

				} else {
					System.out.println("密码错误");
				}

			} else {
				System.out.println("请输入正确的操作序号");
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
		System.out.println("请输入您想要预订的航班号");
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
					// 订单表————加一行
					System.out.println("请支付" + price + "元");
					System.out.println("支付请输入'Y'");
					String buyStatus = input.next();
					if (buyStatus.equals("Y")) {
						System.out.println("已确认支付");

						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						createDate = df.format(new Date()); // new
															// Date()为获取当前系统时间
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
						System.out.println("增加了" + result_int + "行航班订单数据");

						sql = "update flightOrder set orderStatus = 'PAID' where passengerID = '" + passengerID + "' "
								+ "and createDate ='" + createDate + "'";
						result_int = stmt.executeUpdate(sql);
						if (result_int == 1) {
							System.out.println("订单状态已更改为PAID");
						}

						// 航班表————改当前已订人数,判断是否等于额定载量（更改航班状态）
						sql = "update flight set currentPassengers = currentPassengers + 1 where flightid = '"
								+ flightID + "'";
						result_int = stmt.executeUpdate(sql);
						System.out.println("改变了" + result_int + "条航班的当前人数数据");

						if (seatCapacity == (currentPassengers + 1)) {
							sql = "update flight set flightStatus = 'FULL' where flightid = '" + flightID + "'";
							stmt.executeUpdate(sql);
						} // 若当前乘客是最后一个，改变航班状态为已订满

					} else {
						System.out.println("您未支付，航班预定失败,请重新预订航班");
					}

				} else {
					System.out.println("该航班不可预订");
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

	public void unsubscribeFlight() {
		System.out.println("请输入您想要退订的航班号");
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

					System.out.println("您的退款为" + price + "元");
					System.out.println("退款已返还");

					// 订单表————减一行
					sql = "update flightorder set  orderstatus = 'CANCEL'where flightid = '" + flightID
							+ "' and passengerID = '" + passengerID + "'";
					int result_int = stmt.executeUpdate(sql);
					System.out.println("取消" + result_int + "行航班号为" + flightID + "乘客ID为" + passengerID + "的航班订单数据");

					// 航班表————改当前已订人数,判断是否需要更改航班状态
					sql = "update flight set currentPassengers = currentPassengers - 1 where flightid = '" + flightID
							+ "'";
					result_int = stmt.executeUpdate(sql);
					System.out.println("减少了" + result_int + "条航班的当前人数数据");

					if (seatCapacity == currentPassengers) {
						sql = "update flight set flightStatus = 'AVAILABLE' where flightid = '" + flightID + "'";
						stmt.executeUpdate(sql);
					} // 若当前乘客是最后一个，改变航班状态为可预订

				} else {
					System.out.println("飞机已经起飞，不可订退");
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
						"航班号：" + flightID + "  座位号：" + seat + "  预定时间：" + createDate + "  订单状态：" + orderStatus);

			}
			result.beforeFirst(); // 结果集的游标回到最初位置
			if (!result.next()) {
				System.out.println("您没有订单");
			} else {
				System.out.println("您可以根据航班号查询该航班的其他信息");
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

			sql = "update passenger set password = '" + password + "' where identityID = '" + identityID + "'";

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

}
