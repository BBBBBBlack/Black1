package new0117;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Scanner;

public class goods extends administration1{
    private String goods_id;
    private String goods_name;
    private int goods_price;
    public goods(){}
    public goods(String goods_id,String goods_name,int goods_price){
        this.goods_id=goods_id;
        this.goods_name=goods_name;
        this.goods_price=goods_price;
    }
    @Override
    public void add(Connection conn) throws SQLException {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the goods_id:");
        String id=sc.nextLine();
        System.out.println("Enter the goods_name:");
        String name=sc.nextLine();
        System.out.println("Enter the goods_price:");
        int price=sc.nextInt();
        String sql="insert into goods(goods_id,goods_name,goods_price)values(?,?,?)";
        PreparedStatement addition=conn.prepareStatement(sql);
        addition.setString(1,id);
        addition.setString(2,name);
        addition.setInt(3,price);
        addition.execute();
        System.out.println("Success");
    }

    @Override
    public String toString() {
        return "good_id = "+goods_id+" ,goods_name = "+goods_name+" ,goods_price = "+goods_price;
    }
}
