package logic;

import skeleton.Logger;

public class Ice extends Resource {

    /**
	 *
	 */
	private static final long serialVersionUID = -6702522056977305777L;
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

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Ice";
    }


}
