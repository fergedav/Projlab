package logic;

import skeleton.Logger;

public class Ice extends Resource {

    @Override
    public void callBack(Inventory inv) 
    {
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addIce(this);

        Logger.endFunctionLog();
    }
    @Override
	public void reaction(Asteroid a) 
    {
        Logger.startFunctionLogComment(this, "reaction", "");
        
        a.retrieveResource();

        Logger.endFunctionLog();
    }

}
