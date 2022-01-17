package new0117;


public class output {
    public static void menu(){
        for (int j = 0; j < 40; j++) {
            System.out.print("*");
        }
        System.out.print("\n");
        for (int j = 0; j < 16; j++) {
            System.out.print(" ");
        }
        System.out.println("System");
        System.out.println("Please enter your selection:");
        System.out.println("a.add goods       b.add order");
        System.out.println("c.delete goods    d.delete order");
        System.out.println("e.check the goods f.check the order");
        System.out.println("g.output all the goods");
        System.out.println("h.output all the orders");
        System.out.println("i.exit");
        for (int j = 0; j < 40; j++) {
            System.out.print("*");
        }
        System.out.print("\n");
    }
}

