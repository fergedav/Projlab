package logic;

import skeleton.Logger;

public abstract class Traveler {

    protected Inventory inventory;

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
        Logger.startFunctionLogComment(this, "digging", "");

        currentLocation.drilled();

        Logger.endFunctionLog();
    }

    public void move(int index)
    {
        Logger.startFunctionLogComment(this, "move", "");

        /** Megkeresi az index paraméterben kapott szomszédját a tartózkodási helynek. */
        Orbit destination = currentLocation.getNeighbour(index);
        /** Eltávolítja magátz eddigi tartózkodási helyéről. */
        currentLocation.removeTraveler(this);
        /** Hozzáadja magát a célállomáshoz. */
        destination.addTraveler(this);

        Logger.endFunctionLog();
    }

    /** A Settler és a Robot magának implementálja. */
    public abstract void die();

    /** Urán robbanáskor hívódik, a Settler és a Robot magának implementálja. */
    public abstract void explosion();

    /** Visszaadja a saját inventory-ját */
    public Inventory getInventory()
    {
        Logger.startFunctionLogComment(this, "getInventory", "");

        Inventory inv = inventory;

        Logger.endFunctionLog();
        
        return inv;
    }

    /** A Settler és a Robot magának implementálja. */
    public abstract void step();
}
