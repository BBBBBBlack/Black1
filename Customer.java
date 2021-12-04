package work;

import java.time.LocalDate;

public class Customer {
    String name;
    int times;
    LocalDate today;
    public Customer(String name,int times,LocalDate today){
        this.name=name;
        this.times=times;
        this.today=today;
    }
    public String toString(){
        return "Name:"+this.name+"\n"+"Times:"+this.times+"\n"+"Date:"+this.today+"\n";
    }
}
