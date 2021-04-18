package logic;

import skeleton.Logger;

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
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addIce(this);

        Logger.endFunctionLog();
    }
    /**
     * A jég elpárolog az aszteroidából így az üres lesz.
     * A paraméterként kapott aszteroidára a RetrieveResource() metódust hívja.
     * @param a Asteroida, amelyikben a nyersanyag található
     */
    @Override
	public void reaction(Asteroid a) 
    {
        Logger.startFunctionLogComment(this, "reaction", "");
        
        a.retrieveResource();

        Logger.endFunctionLog();
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Ice";
    }


}
