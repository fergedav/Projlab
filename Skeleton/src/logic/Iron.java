package logic;

public class Iron extends Resource {

    /**
     *
     */
    private static final long serialVersionUID = -4027146222680543561L;
    /**
     * Ezt hívja meg az Inventory osztály mikor a vas nyersanyagot el kell benne tárolni,
     * meghívja a paraméterül kapott Inventory AddIron() függvényét.
     *  @param inv adott inventory
     */
    @Override
    public void callBack(Inventory inv)
    {
        inv.addIron(this);
    }

    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Iron";
    }
}
