package logic;

public class Ice extends Resource {

    @Override
    public void callBack(Inventory inv) {
        inv.addIce(this);
    }
    @Override
	public void reaction(Asteroid a) {
        a.retrieveResource();
    }

}
