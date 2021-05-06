package logic;

public abstract class Resource implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6020870449194920981L;
	/**
	 * Ezt h�vja meg az Inventory oszt�ly mikor a nyersanyagot el kell benne t�rolni.
	 * @param inv adott inventory, amihez hozz�adjuk
	 */
	public abstract void callBack(Inventory inv);
	/**
	 * Az aszteroida h�vja meg �s a nyersanyag v�grehajtja rajta a saj�t reakci�j�t.
	 * @param a Asteroida, amelyikben a nyersanyag tal�lhat�
	 */
	public void reaction(Asteroid a) { }
}
