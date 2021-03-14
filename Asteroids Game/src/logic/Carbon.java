package logic;

public class Carbon extends Resource {

    @Override
    public void callBack(Inventory inv) {
        inv.addCarbon(this);
    }
}
