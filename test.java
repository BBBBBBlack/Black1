package work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        MyCatCafe m=new MyCatCafe();
        m.cats=new ArrayList<>();
        m.customers=new ArrayList<>();
        System.out.println("Buy some cats");
        m.buying();
        m.buying();
        System.out.println(m.cats.get(0));
        System.out.println(m.cats.get(1));
        System.out.println("Service:");
        System.out.println("Customer's name:");
        String name=sc.next();
        System.out.println("Times:");
        int times=sc.nextInt();
        Customer cus=new Customer(name,times,LocalDate.now());
        m.human(cus);
        m.isClose();
    }
}
