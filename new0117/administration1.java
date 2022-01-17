package new0117;
import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;
public abstract class administration1 {
        abstract void add(Connection conn) throws SQLException, ParseException;

        void delete(Connection conn, String sql) throws SQLException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the id you want to delete:");
            String id = sc.nextLine();
            PreparedStatement del = conn.prepareStatement(sql);
            del.setString(1, id);
            del.execute();
            System.out.println("Success");
        }

        <T> void getOneInstance(Connection conn, Class<T> clazz, String sql) throws Exception {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the id you want to know:");
            String id = sc.nextLine();
            PreparedStatement sel = conn.prepareStatement(sql);
            sel.setString(1, id);
            ResultSet rs = sel.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < col; i++) {
                    String colName = rsmd.getColumnLabel(i + 1);
                    Object colValues = rs.getObject(colName);
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValues);
                }
                System.out.println(t);
            }
        }

        <T> void getAllInstance(Connection conn, Class<T> clazz, String sql2) throws Exception {
            String sql = "select * from " + sql2;
            PreparedStatement sel = conn.prepareStatement(sql);
            ResultSet rs = sel.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            while (rs.next()) {
                T t = clazz.getDeclaredConstructor().newInstance();
                for (int i = 0; i < col; i++) {
                    String colName = rsmd.getColumnLabel(i + 1);
                    Object colValues = rs.getObject(colName);
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValues);
                }
                System.out.println(t);
            }
        }
    }

