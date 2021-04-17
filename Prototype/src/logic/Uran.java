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

    @Override
    public void callBack(Inventory inv)
    {
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addUran(this);

        Logger.endFunctionLog();
    }
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
