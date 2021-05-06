package logic;

public abstract class Resource implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6020870449194920981L;
	/**
	 * Ezt hívja meg az Inventory osztály mikor a nyersanyagot el kell benne tárolni.
	 * @param inv adott inventory, amihez hozzáadjuk
	 */
	public abstract void callBack(Inventory inv);
	/**
	 * Az aszteroida hívja meg és a nyersanyag végrehajtja rajta a saját reakcióját.
	 * @param a Asteroida, amelyikben a nyersanyag található
	 */
	public void reaction(Asteroid a) { }
}
