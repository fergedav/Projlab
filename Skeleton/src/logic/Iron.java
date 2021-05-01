package logic;

public class Iron extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -4027146222680543561L;
    /**
     * Ezt h�vja meg az Inventory oszt�ly mikor a vas nyersanyagot el kell benne t�rolni,
     * megh�vja a param�ter�l kapott Inventory AddIron() f�ggv�ny�t.
     *  @param inv adott inventory
     */
    @Override
    public void callBack(Inventory inv)
    {
        inv.addIron(this);
    }

    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Iron";
    }
}
