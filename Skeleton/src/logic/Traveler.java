package logic;

public abstract class Traveler implements java.io.Serializable {

    /**
     * Szerializ�l�
     */
    private static final long serialVersionUID = 1409581797041834036L;
    /**
     * Az utaz� k�szlete.
     */
    protected Inventory inventory;
    /**
     * Az Orbit amelyen az utaz� jelenleg tart�zkodik.
     */
    protected Orbit currentLocation;
    /**
     * A j�t�k ir�ny�t�ja, sz�mon tartja a lesz�rmazottakat.
     */
    protected Controller controler;

    /**
     * prefix a traveler azonos�t�s�ra
     */
    String prefix;
    /**
     * Konstruktor
     */
    protected Traveler()
    {
        controler = Controller.getInstance();
    }

    /** 
     * Az �s�shoz megh�vja a drilled f�ggv�nyt a tart�zkod�si hely�re.
     */
    public void digging()
    {
        currentLocation.drilled();
    }
    /**
     * A tart�zkod�si hely�nek i-edik szomsz�dj�ra megpr�b�l �tl�pni.
     * Ha nem l�tezik i-edik szomsz�d helyben marad.
     * @param index int index
     */
    public void move(int index)
    {
        /** Megkeresi az index param�terben kapott szomsz�dj�t a tart�zkod�si helynek. */
        Orbit destination = currentLocation.getNeighbour(index);
        /** Elt�vol�tja mag�tz eddigi tart�zkod�si hely�r�l. */
        currentLocation.removeTraveler(this);
        /** Hozz�adja mag�t a c�l�llom�shoz. */
        currentLocation = destination.addTraveler(this);
    }

    /** A Settler �s a Robot mag�nak implement�lja. */
    public abstract void die();

    /** Ur�n robban�skor h�v�dik, a Settler �s a Robot mag�nak implement�lja. */
    public abstract void explosion();

    /** Visszaadja a saj�t inventory-j�t */
    public Inventory getInventory()
    {
        Inventory inv = inventory;
        return inv;
    }

    /** A Settler �s a Robot mag�nak implement�lja. */
    public abstract void step();

    /**
     * Visszater az adott Orbithoz tartozo prefixxel
     * @return String prefix
     */
    public String getPrefix()
    {
        return prefix;
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
