package skeleton;

public class Test {
    private static int counter = 0;
    private int number;

    public Test()
    {
        number = counter++;
    }

    public void f1()
    {
        System.out.println(number);
    }

    public boolean f2(int n)
    {
        //f1();
        Logger.log(this, "t1", "f1", "");
        return n == 0;
    }
}
