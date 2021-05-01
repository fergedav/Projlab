package logic;

public class Carbon extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -9198056484503708675L;
    /**
     * Ezt hívja meg az Inventory osztály mikor a szén nyersanyagot el kell benne tárolni,
     * meghívja a paraméterül kapott Inventory AddCarbon() függvényét.
     *  @param inv adott inventory
     */
    @Override
    public void callBack(Inventory inv) {
        inv.addCarbon(this);
    }

    @Override
    public String toString(){
        return "Carbon";
    }



}
