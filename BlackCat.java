package work;

public  class BlackCat extends Cat{
    public BlackCat(String name,int age,double price){
        super(name, age, 350);
    }
    public BlackCat(){

    }
    public String toString() {
        return "Name:"+this.name+"\nAge:"+this.age+"\nPrice:"+this.price;
    }
}
