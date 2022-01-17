package new0117;

import java.sql.*;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws Exception {
        Connection conn = connect_close.build_connection();
        goods good = new goods();
        order orders = new order();
        Scanner sc = new Scanner(System.in);
        output.menu();
        char sel=sc.next().charAt(0);
        while(sel!='i'){
            switch(sel){
                case 'a':
                    good.add(conn);
                    break;
                case 'b':
                    orders.add(conn);
                    break;
                case 'c':
                    good.delete(conn,"delete from goods where goods_id=?");
                    break;
                case 'd':
                    orders.delete(conn,"delete from `order` where order_id=?");
                    break;
                case 'e':
                    good.getOneInstance(conn,goods.class,"select * from goods where goods_id=?");
                    break;
                case 'f':
                    orders.getOneInstance(conn,order.class,"select * from `order` from where order_id=?");
                    break;
                case 'g':
                    good.getAllInstance(conn,goods.class,"goods");
                    break;
                case 'h':
                    orders.getAllInstance(conn,order.class,"`order`");
                    break;
                default:
                    System.out.println("Wrong");
                    break;
            }
            output.menu();
            if(sc.hasNext()){
                sc.nextLine();
            }
            sel=sc.next().charAt(0);
        }
        sc.close();
    }
}

