package logic;

public class Ice extends Resource {

    /**
	 *
	 */
	private static final long serialVersionUID = -6702522056977305777L;
    /**
     * Ezt hívja meg az Inventory osztály mikor a jég nyersanyagot el kell benne tárolni,
     * meghívja a paraméterül kapott Inventory AddIce() függvényét.
     * @param inv adott inventory
     */
	@Override
    public void callBack(Inventory inv) 
    {
        inv.addIce(this);
    }
    /**
     * A jég elpárolog az aszteroidából így az üres lesz.
     * A paraméterként kapott aszteroidára a RetrieveResource() metódust hívja.
     * @param a Asteroida, amelyikben a nyersanyag található
     */
    @Override
	public void reaction(Asteroid a) 
    {
        a.retrieveResource();
    }

    @Override
    public String toString(){
        return "Ice";
    }
}
