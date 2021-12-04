package work;

public abstract class Cat {
    String name;
    int age;
    double price;

    public Cat() {
    }

    public Cat(String name, int age, double price) {
        this.name = name;
        this.age = age;
        this.price = price;
    }

    public abstract String toString();
}

