package skeleton;

import logic.*;

public class SkeletonMain {
    public static void main(String[] args) {
        //Iron i = new Iron();
        //Inventory inv = new Inventory();

        //inv.addResource(i);
        //Logger.log(inv, "addResource", "epik komment", i);

        Test t1 = new Test();
        Test t2 = new Test();

        Logger.log(t1, "f1", "f1 hívása");
        boolean b = Logger.log(t2, "f2", "f2 hívása", 0);
        System.out.println(b);
    }
}
