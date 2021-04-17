package logic;

public abstract class Resource implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6020870449194920981L;
	public abstract void callBack(Inventory inv);
	public void reaction(Asteroid a) { }

	
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

	public abstract String toString();


}
