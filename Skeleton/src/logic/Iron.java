package logic;

import skeleton.Logger;

public class Iron extends Resource {

    @Override
    public void callBack(Inventory inv)
    {
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addIron(this);
    
        Logger.endFunctionLog();
    }

}
