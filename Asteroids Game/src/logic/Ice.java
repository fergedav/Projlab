package logic;

public class Ice extends Resource {

    @Override
    public void callBack(Inventory inv) {
        inv.addIce(this);
    }

}
