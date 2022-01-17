package new0117;

import java.sql.*;
import java.util.Scanner;

public class connect_close {
    public static Connection build_connection() throws Exception {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter user:");
        String user=sc.nextLine();
        System.out.println("Enter your password:");
        String password=sc.nextLine();

        Class clazz=Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver=(Driver)clazz.getDeclaredConstructor().newInstance();
        String url="jdbc:mysql://localhost:3306/administration";
        DriverManager.registerDriver(driver);
        Connection conn=DriverManager.getConnection(url,user,password);
        return conn;
    }
    public static void close_connection(Connection conn,PreparedStatement pre){
        try {
            pre.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

