package logic;

public class Carbon extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -9198056484503708675L;

    @Override
    public void callBack(Inventory inv) {
        inv.addCarbon(this);
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Carbon";
    }



}
