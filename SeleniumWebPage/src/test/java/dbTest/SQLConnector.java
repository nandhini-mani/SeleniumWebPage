package dbTest;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class SQLConnector {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		String dbURL = "jdbc:mysql://localhost:3306/dbtest";
		
		String userName = "root";
		String password = "Nandhu04!";
		String query = "select * from student";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(dbURL,userName,password);
		Statement stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery(query);
		
		while(rs.next()){
			int rollNo = rs.getInt(1);
			String firstName = rs.getString(2);
			String lastName = rs.getString(3);
			String grade = rs.getString(4);
			System.out.println(rollNo+"    "+firstName+"   "+lastName+"   "+grade);
		}

		con.close();
	}

}
