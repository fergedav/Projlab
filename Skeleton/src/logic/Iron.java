package logic;

import skeleton.Logger;

public class Iron extends Resource {

    @Override
    public void callBack(Inventory inv) {
        //inv.addIron(this);
        Logger.log(inv, "inv", "addIron", "", this);
    }

}
