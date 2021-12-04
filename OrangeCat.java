package work;

public  class OrangeCat extends Cat{

    public OrangeCat(String name, int age, double price,boolean isFat) {
        super(name, age, 200);
        this.isFat=isFat;
    }
    public OrangeCat(){

    }
    boolean isFat;
    public String toString() {
        return "Name:"+this.name+"\nAge:"+this.age+"\nPrice:"+this.price+"\nisFat:"+this.isFat;
    }
}
