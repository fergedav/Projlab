package logic;

public abstract class Traveler {

    protected Inventory inventory = new Inventory(10);

    protected Orbit currentLocation;
    protected Controler controler;

    protected Traveler()
    {
        controler = Controler.getInstance();
    }

    /** 
     * Az ásáshoz meghívja a drilled függvényt a tartózkodási helyére.
     */
    public void digging()
    {
        currentLocation.drilled();
    }

    public void move(int index)
    {
        /** Megkeresi az index paraméterben kapott szomszédját a tartózkodási helynek. */
        Orbit destination = currentLocation.getNeighbour(index);
        /** Eltávolítja magátz eddigi tartózkodási helyéről. */
        currentLocation.removeTraveler(this);
        /** Hozzáadja magát a célállomáshoz. */
        destination.addTraveler(this);
    }

    /** A Settler és a Robot magának implementálja. */
    public abstract void die();

    /** Urán robbanáskor hívódik, a Settler és a Robot magának implementálja. */
    public abstract void explosion();

    /** Visszaadja a saját inventory-ját */
    public Inventory getInventory()
    {
        return inventory;
    }

    /** A Settler és a Robot magának implementálja. */
    public abstract void step();
}
