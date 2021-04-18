package logic;

import skeleton.Logger;

public class Uran extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -786906863582651807L;
    /**
     * hanyszor erte az urant eddig napfeny
     */
    private int exposure = 0;
    /**
     * Ezt hívja meg az Inventory osztály mikor a uran nyersanyagot el kell benne tárolni,
     * meghívja a paraméterül kapott Inventory AddUran() függvényét.
     * @param inv adott inventory
     */
    @Override
    public void callBack(Inventory inv)
    {
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addUran(this);

        Logger.endFunctionLog();
    }
    /**
     * Az aszteroida hívja meg és a nyersanyag végrehajtja rajta a saját reakcióját,
     * az urán növeli eggyel az exposure számlálóját.
     * Ha eléri a 3-at eltûnik az aszteroidából és az aszteroida felrobban.
     * @param a Asteroida, amelyikben a nyersanyag található
     */
    @Override
	public void reaction(Asteroid a)
    {
        Logger.startFunctionLogComment(this, "reaction", "");

        exposure++;

        if(exposure >= 3)
        {
            a.explosion();
            a.retrieveResource();
        }

        Logger.endFunctionLog();
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Uran";
    }


}
