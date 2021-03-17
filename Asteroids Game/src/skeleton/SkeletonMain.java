package skeleton;

import logic.*;

public class SkeletonMain {
    public static void main(String[] args) {
        Iron i = new Iron();
        Inventory inv = new Inventory();
        boolean isLogging = false;

        inv.addIron(i);
        Logger.log(inv, "inv", "addIron", "", i);

        //inv.addResource(i);
        //Logger.log(inv, "addResource", "epik komment", i);

        Test t1 = new Test();
        Test t2 = new Test();

        //t1.f1();
        Logger.log(t1, "t1", "f1", "f1 hívása");

        //boolean bb = t2.f2(0);
        boolean b = Logger.log(t2, "t2", "f2", "f2 hívása", 0);


        System.out.println(b);
    }
}
