package logic;

import skeleton.Logger;

public abstract class Traveler implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1409581797041834036L;

    protected Inventory inventory;

    protected Orbit currentLocation;
    protected Controller controler;

    protected Traveler()
    {
        controler = Controller.getInstance();
    }

    /** 
     * Az �s�shoz megh�vja a drilled f�ggv�nyt a tart�zkod�si hely�re.
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

        /** Megkeresi az index param�terben kapott szomsz�dj�t a tart�zkod�si helynek. */
        Orbit destination = currentLocation.getNeighbour(index);
        /** Elt�vol�tja mag�tz eddigi tart�zkod�si hely�r�l. */
        currentLocation.removeTraveler(this);
        /** Hozz�adja mag�t a c�l�llom�shoz. */
        destination.addTraveler(this);

        Logger.endFunctionLog();
    }

    /** A Settler �s a Robot mag�nak implement�lja. */
    public abstract void die();

    /** Ur�n robban�skor h�v�dik, a Settler �s a Robot mag�nak implement�lja. */
    public abstract void explosion();

    /** Visszaadja a saj�t inventory-j�t */
    public Inventory getInventory()
    {
        Logger.startFunctionLogComment(this, "getInventory", "");

        Inventory inv = inventory;

        Logger.endFunctionLog();
        
        return inv;
    }

    /** A Settler �s a Robot mag�nak implement�lja. */
    public abstract void step();

    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

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
     * M�dos�tja a prefixet a parameterben kapott stringgel.
     * @param s prefix uj erteke
     */
    public void setPrefix(String s)
    {
        prefix = s;
        //TODO Controllerben be�rni megfelle� perixet, amikor hozzaadja a listahoz robot/ufo/settler
    }
    /**
     * Megadja  traveler aktu�lis tartozkod�si hely�t
     * @return aktualis obrit, tartozkodasi hely
     */
    public Orbit getcurrentLocation()
    {
        return currentLocation;
    }

}
