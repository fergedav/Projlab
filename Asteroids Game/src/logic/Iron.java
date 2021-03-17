package logic;

public class Iron extends Resource {

    @Override
    public void callBack(Inventory inv) {
        inv.addIron(this);
        //Logger.log(inv, "addIron", "adding iron to inv", this);
    }

}
