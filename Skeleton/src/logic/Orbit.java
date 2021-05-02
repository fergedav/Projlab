package logic;

import java.util.ArrayList;
import java.util.List;

public abstract class Orbit implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8842665086008545927L;
    /**
     * szomsz�dokat tartalmaz� lista
     */
    protected List<Orbit> neighbours;
    /**
     * a rajta �ll� travelereket tartalmaz� lista
     */
    protected List<Traveler> travelers;
    /**
     * megmondja, hogy az orbit napf�nyben van-e.
     */
    protected boolean inLight = false;
    /**
     * az orbit k�rge
     */
    protected int layers = 0;
    /**
     * az orbit x koordin�t�ja
     */
    protected int x; 
    /**
     * az orbit y koordin�t�ja
     */
    protected int y;
    /**
     * prefix az adott orbit azonos�t�s�ra
     */
    String prefix;
    /**
    * konstruktor, l�trehozza a szomsz�dok, �s travelerek list�j�t.
    */
    protected Orbit() {
        neighbours = new ArrayList<Orbit>();
        travelers = new ArrayList<Traveler>();
        
    }

    /**
     * Virtu�lis f�ggv�ny. 
     * Hozz�adja a param�terk�nt kapott Traveler-t az Orbit travelers list�j�hoz �s visszat�r �nmag�val.
     * @param t �rkezett traveler
     * @return saj�t maga
     */
    public Orbit addTraveler(Traveler t)
    {
        travelers.add(t);
        return this;
    }


    /**
     * Visszat�r az Orbiton jelenleg tart�zkod� utaz�k list�j�val
     * @return Orbiton jelenleg tart�zkod� utaz�k
     */
    public List<Traveler> getTravelers()
    {
        return travelers;
    }

    /**
     * Elt�vol�tja a param�terk�nt kapott Traveler-t az Orbit travelers list�j�b�l.
     * @param t Traveler
     */
    public void removeTraveler(Traveler t)
    {
        travelers.remove(t);
    }

    /**
     * A param�terk�nt kapott Orbit-ot hozz�adja a szomsz�ds�gi list�j�hoz.
     * @param o Orbit
     */
    public Orbit addNeighbour(Orbit o)
    {
        neighbours.add(o);
        return this;
    }

    /**
     * Kit�rli a param�terk�nt kapott Orbitot az objektum szomsz�ds�gi list�j�b�l.
     * @param o Orbit
     */
    public void removeNeighbour(Orbit o)
    {
        neighbours.remove(o);
    }

    /**
     * Visszat�r az orbit i-edik szomsz�dj�val. Ha i-edik szomsz�d nem l�tezik null-t ad vissza.
     * @param i szomsz�d sorsz�ma
     * @return szomsz�dos Orbit
     */
    public Orbit getNeighbour(int i)
    {
        try 
        {
            Orbit o = neighbours.get(i);
            return o;
        } 
        catch (Exception e) 
        {
            return this;
        }

        
    }

    public abstract void drilled();

    /**
     * visszadja a tartalmazott nyersanyagot
     * @return Resource 
     */
    public Resource retrieveResource()
    {
        return null;
    }

    /**
     * Virtu�lis f�ggv�ny. Alap�rtelmez�sben false-t ad vissza.
     * @param r
     * @return
     */
    public boolean putResource(Resource r)
    {
        return false;
    }

    /**
     * Alap�rtelmez�sben megh�vja a travelers list�ja �sszes elem�re a Die() f�ggv�nyt.
     */
    public void sunstormArrive(int[] coords )
    {
        if(!(
            coords[0] <= x &&
            coords[1] <= y &&
            coords[2] >= x &&
            coords[3] >= y
        )) return;

        ArrayList<Traveler> copy = new ArrayList<>();
        copy.addAll(travelers);
        for (Traveler t : copy) {
            t.die();
        }
    }

    /**
     * Eld�nti, hogy a koordin�t�i alapj�n beleesik-e a jelzett n�gyzetbe. 
     * Ha igen, akkor be�ll�tja true-ra a inLight attrib�tumot, ha nem be�ll�tja false-ra.
     * @param int[] coords 
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
     * M�g nem �tfurt r�tegek sz�ma
     * @return r�tegek
     */
    public int getLayers()
    {
        return layers;
    }

    public int numOfNeighbor()
    {   
        return neighbours.size();
    }
    
    /**
     * Visszadja az orbit koordinantatit 
     * x,y kooridnata
     * @return [x,y]
     */
    public int[] getCoords()
    {
        int i[]= {x,y};
        return i;
    }
    /**
     * Visszadja az orbit aktualis napfeny allapotata 
     * @return boolean true, ha napfeny van
     */
    public boolean getLight()
    {
        return inLight;
    }    

    /**
     * Visszater az adott Orbithoz tartozo prefixxel
     * @return String prefix
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * alap�rtelmezett getter, az asteroid overrideolja
     * @return null
     */
    public Resource getCore()
    {
        return null;
    }

    /**
     * Egy m�solt lista az Orbit szomsz�dair�l
     * @return a lista
     */
    public List<Orbit> getNeighborList()
    {
        return new ArrayList<Orbit>(neighbours);
    }

    /**
     * kell a comboBox megjelen�t�shez
     */
    @Override
    public String toString()
    {
        return prefix;
    }
}
