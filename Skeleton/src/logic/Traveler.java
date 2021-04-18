package logic;

import skeleton.Logger;

public abstract class Traveler implements java.io.Serializable {

    /**
     * Szerializáló
     */
    private static final long serialVersionUID = 1409581797041834036L;
    /**
     * Az utazó készlete.
     */
    protected Inventory inventory;
    /**
     * Az Orbit amelyen az utazó jelenleg tartózkodik.
     */
    protected Orbit currentLocation;
    /**
     * A játék irányítója, számon tartja a leszármazottakat.
     */
    protected Controller controler;
    /**
     * Konstruktor
     */
    protected Traveler()
    {
        controler = Controller.getInstance();
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
    /**
     * A tartózkodási helyének i-edik szomszédjára megpróbál átlépni.
     * Ha nem létezik i-edik szomszéd helyben marad.
     * @param index int index
     */
    public void move(int index)
    {
        Logger.startFunctionLogComment(this, "move", "");

        /** Megkeresi az index paraméterben kapott szomszédját a tartózkodási helynek. */
        Orbit destination = currentLocation.getNeighbour(index);
        /** Eltávolítja magátz eddigi tartózkodási helyérõl. */
        currentLocation.removeTraveler(this);
        /** Hozzáadja magát a célállomáshoz. */
        currentLocation = destination.addTraveler(this);

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

    
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    // perfix
    String prefix;
    /**
     * Visszater az adott Orbithoz tartozo prefixxel
     * @return String prefix
     */
    public String getPrefix()
    {
        return prefix;
    }
    /**
     * Módosítja a prefixet a parameterben kapott stringgel.
     * @param s prefix uj erteke
     */
    public void setPrefix(String s)
    {
        prefix = s;
        //TODO Controllerben beírni megfelleõ perixet, amikor hozzaadja a listahoz robot/ufo/settler
    }
    /**
     * Megadja  traveler aktuális tartozkodási helyét
     * @return aktualis obrit, tartozkodasi hely
     */
    public Orbit getcurrentLocation()
    {
        return currentLocation;
    }

}
