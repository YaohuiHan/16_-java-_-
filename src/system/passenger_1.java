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

public class passenger_1 {

	public static void main(String[] args) {
		passenger_1 passenger = new passenger_1();
	}

	public passenger_1() {
		passenger_1.createFlight_every();
		passenger_1.checkStatus();

		System.out.println("您好！");
		Scanner input = new Scanner(System.in);
		reserveFlight reserveFlight = new reserveFlight();

		while (true) {
			System.out.println("请输入您想要进行操作的序号");
			System.out.println("1.注册  2.登录  3.查询航班  4.退出系统");
			String number = input.next();
			if (number.equals("1")) {
				reserveFlight.passengerSignUp();
			} else if (number.equals("2")) {
				while (true) {
					System.out.println("请输入您想要登录的身份序号");
					System.out.println("1.乘客  2.管理员  3.返回");
					String number_ = input.next();
					if (number_.equals("1")) {
						reserveFlight.passengerLogin();
					} else if (number_.equals("2")) {
						Admin Admin = new Admin();
					} else if (number_.equals("3")) {
						break;
					} else {
						System.out.println("请输入正确的序号");
					}
				}
			} else if (number.equals("3")) {
				queryFlight1 queryFlight = new queryFlight1();
			} else if (number.equals("4")) {
				break;
			} else {
				System.out.println("请输入正确的操作序号");
			}
		}
	}

	public static void checkStatus() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement Stmt = conn.createStatement();
			Statement Stmt1 = conn.createStatement();

			String query = "select * from flight where flightStatus <> 'TERMINATE'";
			ResultSet Result = Stmt.executeQuery(query);

			Date date = new Date();

			while (Result.next()) {

				Timestamp StartTime = Result.getTimestamp("starttime");

				if ((StartTime.getTime() - date.getTime() + 1000 * 60 * 60 * 2) < 0) {
					String sql = "update flight set flightStatus = 'TERMINATE' ";
					Stmt1.executeUpdate(sql);
				}
			}

			Result.close();
			Stmt.close();
			Stmt1.close();
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

	public static void createFlight_every() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/Project?characterEncoding=utf8&useSSL=false", "root", "");
			Statement Stmt = conn.createStatement();

			String query = "select * from flight where flightStatus <> 'TERMINATE'";
			ResultSet Result = Stmt.executeQuery(query);
			String StartTime = null;
			ArrayList tempList = new ArrayList();

			tempList.add("flightid");

			while (Result.next()) {
				StartTime = Result.getString("flightid");
				tempList.add(StartTime);
			}

			SimpleDateFormat df_ = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

			long time;
			String nextDate, flightid_1, flightid_2, flightid_3, flightid_4, flightid_5, flightid_6;
			for (int i = 1; i < 4; i++) {
				Date date = new Date();
				time = (date.getTime() / 1000) + 60 * 60 * 24 * i; // 秒
				date.setTime(time * 1000); // 毫秒
				nextDate = df_.format(date);

				flightid_1 = "cz001_" + nextDate;
				if (!tempList.contains(flightid_1)) {
					Stmt.executeUpdate("insert flight values('" + flightid_1 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','ShenZhen','Beijing','998','0','200','UNPUBLISHED')");
				}
				flightid_2 = "cz002_" + nextDate;
				if (!tempList.contains(flightid_2)) {
					Stmt.executeUpdate("insert flight values('" + flightid_2 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','Beijing','ShenZhen','998','0','200','UNPUBLISHED')");
				}
				flightid_3 = "cz003_" + nextDate;
				if (!tempList.contains(flightid_3)) {
					Stmt.executeUpdate("insert flight values('" + flightid_3 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','ShangHai','Beijing','998','0','200','UNPUBLISHED')");
				}
				flightid_4 = "cz004_" + nextDate;
				if (!tempList.contains(flightid_4)) {
					Stmt.executeUpdate("insert flight values('" + flightid_4 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','Beijing','ShangHai','998','0','200','UNPUBLISHED')");
				}
				flightid_5 = "cz005_" + nextDate;
				if (!tempList.contains(flightid_5)) {
					Stmt.executeUpdate("insert flight values('" + flightid_5 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','ShenZhen','ShangHai','998','0','200','UNPUBLISHED')");
				}
				flightid_6 = "cz006_" + nextDate;
				if (!tempList.contains(flightid_6)) {
					Stmt.executeUpdate("insert flight values('" + flightid_6 + "','" + nextDate + " 12:00:00','"
							+ nextDate + " 14:00:00','ShangHai','ShenZhen','998','0','200','UNPUBLISHED')");
				}

			}

			Result.close();
			Stmt.close();
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
