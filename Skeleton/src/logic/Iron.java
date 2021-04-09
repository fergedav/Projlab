package logic;

import skeleton.Logger;

public class Iron extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -4027146222680543561L;

    @Override
    public void callBack(Inventory inv)
    {
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addIron(this);
    
        Logger.endFunctionLog();
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////




}
