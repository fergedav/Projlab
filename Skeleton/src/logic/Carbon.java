package logic;

public class Carbon extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -9198056484503708675L;
    /**
     * Ezt h�vja meg az Inventory oszt�ly mikor a sz�n nyersanyagot el kell benne t�rolni,
     * megh�vja a param�ter�l kapott Inventory AddCarbon() f�ggv�ny�t.
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
