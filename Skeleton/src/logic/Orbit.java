package logic;

import java.util.ArrayList;
import java.util.List;

import skeleton.Logger;

public abstract class Orbit {
    protected List<Orbit> neighbours;
    protected List<Traveler> travelers;
    protected boolean inLight = false;
    protected int layers = 0;
    protected int x; //senki nem tárolja a koordinátákat tömbben xd
    protected int y;

    protected Orbit() {
        neighbours = new ArrayList<Orbit>();
        travelers = new ArrayList<Traveler>();
        
    }

    /**
     * Virtuális függvény. 
     * Hozzáadja a paraméterként kapott Traveler-t az Orbit travelers listájához és visszatér önmagával.
     * @param t érkezett traveler
     * @return saját maga
     */
    public Orbit addTraveler(Traveler t)
    {
        Logger.startFunctionLogComment(this, "addTraveler", "");
        travelers.add(t);
        Logger.endFunctionLog();
        return this;
    }


    /**
     * Visszatér az Orbiton jelenleg tartózkodó utazók listájával
     * @return Orbiton jelenleg tartózkodó utazók
     */
    public List<Traveler> getTravelers()
    {
        Logger.startFunctionLogComment(this, "getTravelers", "");
        Logger.endFunctionLog();
        return travelers;
    }

    /**
     * Eltávolítja a paraméterként kapott Traveler-t az Orbit travelers listájából.
     * @param t Traveler
     */
    public void removeTraveler(Traveler t)
    {
        Logger.startFunctionLogComment(this, "removeTraveler", "");
        travelers.remove(t);
        Logger.endFunctionLog();
    }

    /**
     * A paraméterként kapott Orbit-ot hozzáadja a szomszédsági listájához.
     * @param o Orbit
     */
    public void addNeighbour(Orbit o)
    {
        Logger.startFunctionLogComment(this, "addNeighbour", "");
        neighbours.add(o);
        Logger.endFunctionLog();
    }

    /**
     * Kitörli a paraméterként kapott Orbitot az objektum szomszédsági listájából.
     * @param o Orbit
     */
    public void removeNeighbour(Orbit o)
    {
        Logger.startFunctionLogComment(this, "removeNeighbour", "");
        neighbours.remove(o);
        Logger.endFunctionLog();
    }


    /**
     * Visszatér az orbit i-edik szomszédjával. Ha i-edik szomszéd nem létezik null-t ad vissza.
     * @param i szomszéd sorszáma
     * @return szomszédos Orbit
     */
    public Orbit getNeighbour(int i)
    {
        Logger.startFunctionLogComment(this, "getNeighbour", "");

        try {
            Orbit o = neighbours.get(i);
            Logger.endFunctionLog();
            return o;
        } catch (Exception e) {
            Logger.endFunctionLog();
            return null;
        }

        
    }

    public abstract void drilled();

    /**
     * visszadja a tartalmazott nyersanyagot
     * @return Resource 
     */
    public Resource retrieveResource()
    {
        Logger.startFunctionLogComment(this, "retrieveResource", "");
        Logger.endFunctionLog();
        return null;
    }

    /**
     * Virtuális függvény. Alapértelmezésben false-t ad vissza.
     * @param r
     * @return
     */
    public boolean putResource(Resource r)
    {
        Logger.startFunctionLogComment(this, "putResource", "");
        Logger.endFunctionLog();
        return false;
    }

    /**
     * Alapértelmezésben meghívja a travelers listája összes elemére a Die() függvényt.
     */
    public void sunstormArrive(int[] coords )
    {
        Logger.startFunctionLogComment(this, "sunstormArrive", "");
        
        if(!(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        )) return;

        for (Traveler t : travelers) {
            t.die();
        }

        Logger.endFunctionLog();
    }

    /**
     * Eldönti, hogy a koordinátái alapján beleesik-e a jelzett négyzetbe. 
     * Ha igen, akkor beállítja true-ra a inLight attribútumot, ha nem beállítja false-ra.
     * @param x1 left
     * @param y1 top
     * @param x2 right
     * @param y2 bottom
     */
    public void sunLightArrive(int[] coords )
    {

        if( coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y)
        {
            inLight = true;
        }
        else
        {
            inLight = false;
        }

    }

    /**
     * Még nem átfurt rétegek száma
     * @return rétegek
     */
    public int getLayers()
    {
        Logger.startFunctionLogComment(this, "getLayers", "");
        Logger.endFunctionLog();
        return layers;
    }

    public int numOfNeighbor()
    {   
        //ezt nem kell logolni.
        //Logger.startFunctionLogComment(this, "numOfNeighbor", "");
        //Logger.endFunctionLog();L
        return neighbours.size();
    }

}
