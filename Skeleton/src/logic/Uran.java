package logic;

public class Uran extends Resource {

    @Override
    public void callBack(Inventory inv) {
        inv.addUran(this);
    }
    @Override
	public void reaction(Asteroid a) {
        a.explosion();
    }

}
