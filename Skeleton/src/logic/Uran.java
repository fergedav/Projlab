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
     * Ezt h�vja meg az Inventory oszt�ly mikor a uran nyersanyagot el kell benne t�rolni,
     * megh�vja a param�ter�l kapott Inventory AddUran() f�ggv�ny�t.
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
     * Az aszteroida h�vja meg �s a nyersanyag v�grehajtja rajta a saj�t reakci�j�t,
     * az ur�n n�veli eggyel az exposure sz�ml�l�j�t.
     * Ha el�ri a 3-at elt�nik az aszteroid�b�l �s az aszteroida felrobban.
     * @param a Asteroida, amelyikben a nyersanyag tal�lhat�
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

    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Uran";
    }


}
