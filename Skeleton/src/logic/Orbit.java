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
        travelers = new ArrayList<>();
        
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
        neighbours.add(o);
    }

    /**
     * Kitörli a paraméterként kapott Orbitot az objektum szomszédsági listájából.
     * @param o Orbit
     */
    public void removeNeighbour(Orbit o)
    {
        neighbours.remove(o);
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

    public void drilled()
    { }

    /**
     * visszadja a tartalmazott nyersanyagot
     * @return Resource 
     */
    public Resource retrieveResource()
    {
        return null;
    }

    /**
     * Virtuális függvény. Alapértelmezésben false-t ad vissza.
     * @param r
     * @return
     */
    public boolean putResource(Resource r)
    {
        return false;
    }

    /**
     * Alapértelmezésben meghívja a travelers listája összes elemére a Die() függvényt.
     */
    public void sunstormArrive()
    {
        for (Traveler t : travelers) {
            t.die();
        }
    }

    /**
     * Eldönti, hogy a koordinátái alapján beleesik-e a jelzett négyzetbe. 
     * Ha igen, akkor beállítja true-ra a inLight attribútumot, ha nem beállítja false-ra.
     * @param x1 left
     * @param y1 top
     * @param x2 right
     * @param y2 bottom
     */
    public void sunLightArrive(int x1, int y1, int x2, int y2)
    {
        if(x >= x1 && y >= y1 &&
            x <= x2 && y <= y2)
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
        return layers;
    }

    public int numOfNeighbor()
    {
        return neighbours.size();
    }

}
