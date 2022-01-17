package new0117;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Scanner;

public class order extends administration1{
    private String order_id;
    private LocalDateTime order_date;
    private String goods_id;
    public order(){}
    public order(String order_id,LocalDateTime order_date,String goods_id){
        this.order_id=order_id;
        this.order_date=order_date;
        this.goods_id=goods_id;
    }
    @Override
    public void add(Connection conn) throws SQLException, ParseException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the order_id:");
        String id=sc.nextLine();
        System.out.println("Enter the order_date as:yyyy-MM-dd:");
        String sdate=sc.nextLine();
        System.out.println("Enter the goods_id:");
        String id2=sc.nextLine();
        String sql="insert into `order`(order_id,order_date,goods_id)values(?,?,?)";
        PreparedStatement addition=conn.prepareStatement(sql);
        addition.setString(1,id);
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date=df.parse(sdate);
        addition.setDate(2,new Date(date.getTime()));
        addition.setString(3,id2);
        addition.execute();
        System.out.println("Success");
    }

    @Override
    public String toString() {
        return "order_id = "+order_id+" ,order_date = "+order_date+
                " ,good_id = "+goods_id;
    }
}
