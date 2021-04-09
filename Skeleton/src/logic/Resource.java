package logic;

public abstract class Resource implements java.io.Serializable {
	public abstract void callBack(Inventory inv);
	public void reaction(Asteroid a) { }
}
