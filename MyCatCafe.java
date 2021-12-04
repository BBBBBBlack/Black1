package work;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MyCatCafe implements CatCafe{
    double money=10000;
    Scanner s=new Scanner(System.in);
    ArrayList<Cat> cats;
    ArrayList<Customer> customers;
    public void buying() {
        System.out.println("Cat's name:");
        String name=s.next();
        System.out.println("Cat's age:");
        int age=s.nextInt();
        System.out.println("Orange or black(orange1  black2):");
        int kind=s.nextInt();
        if(kind==1){
            System.out.println("Is it fat?(true or false");
            boolean f=s.nextBoolean();
            Cat c=new OrangeCat(name,age,200,f);
            this.money-=c.price;
            cats.add(c);
        }
        if(kind==2){
            Cat c=new BlackCat(name,age,350);
            this.money-=c.price;
            cats.add(c);
        }
        if(money<0){
            throw new InsufficientBalanceException("余额不足");
        }
    }

    public void human(Customer cus){
        Random r=new Random();
        customers.add(cus);
        //System.out.println(cus.times);
        for(int i=0;i<cus.times;i++){
            int x=Math.abs(r.nextInt()%cats.size());
            System.out.println(cats.get(x).toString());
        }
        if(cats==null){
            throw new CatNotFoundException("There are no cat.");
        }
        this.money+=15*cus.times;
    }
    public void isClose(){
        int benefit=0;
        for(int i=0;i<customers.size();i++){
            if(customers.get(i).today.equals(LocalDate.now())){
                System.out.println(customers.get(i).toString());
                benefit+=15*customers.get(i).times;
            }
            System.out.println("Benefit:"+benefit);
        }
    }
}
