package system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
 
class deleteFlight {

	// public static void main(String[] args) {
	// deleteFlight deleteFlight =new deleteFlight();
	// } //������main����

	public deleteFlight() {
		System.out.println("����������Ҫɾ���ĺ����");
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

				if (StartTime.equals("UNPUBLISHED") || StartTime.equals("TERMINATE")) {

					String sql = "delete from flight where flightid= '" + string + "'";
					int result_int = stmt.executeUpdate(sql);
					System.out.println("ɾ��" + result_int + "�к�������");
				} else {
					System.out.println("���಻��ɾ��");
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
