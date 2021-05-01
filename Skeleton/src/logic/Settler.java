package logic;

import java.util.ArrayList;
import java.util.List;

public class Settler extends Traveler {
    
    /**
	 *
	 */
	private static final long serialVersionUID = 1002966729770761380L;
	//0-3 stargetes
    private List<Stargate> stargates = new ArrayList<>();
    /**
     * Konstruktor
     * @param start kezd� orbitja a settlernek
     */
    public Settler(Orbit start)
    {
        super();
        currentLocation = start.addTraveler(this);
        inventory = new Inventory(10);
        prefix = "settler_"+id_counter++;
        Controller.getInstance().addSettler(this);
    }
    /**
     * A telepes meghal. 
     * 
     * Az aszteroid�ra, amin jelenleg tart�zkodik, �nmag�val (saj�t mag�t adja meg param�terk�nt) 
     * megh�vja a RemoveTraveler(Traveler t) met�dust, 
     * majd a controller-nek is jelzi �nmag�val megh�vva Settler_die(Settler s) met�dust, 
     * majd t�rli �nmag�t.
     */
    @Override
    public void die()
    {
        /** kit�rli mag�t az aszteroid�b�l. */
        currentLocation.removeTraveler(this);

        /** Ha van teleportkapuja a telepesnek a hal�lakor, akkor az �sszes elpusztul a telepessel egy�tt. */
        if(stargates.size() != 0)
        {
            if(stargates.size() == 3)
            {
                stargates.get(2).die();
            }
           stargates.get(0).die();
           
        }

        /** Miut�n elpuszt�totta a kapuit, azut�n jelzi a controllernek, hogy � meghalt �s k�ri, hogy h�zza ki az �l� telepesek list�j�b�l (a settlerDie f�ggv�nnyel) */
        controler.settlerDie(this);
    }
    /**
     * A j�t�kos kiv�laszthat egy m�veletet(f�rhat, b�ny�szhat, mozoghat, �p�thet),
     * amit szeretne a telepes�vel elv�geztetni.
     * Jelenleg NEM haszn�ljuk a protoban.
     */
    @Override
    public void step()
    {}
    /**
     * Kib�ny�ssza az aszteroid�ban tal�lhat� nyersanyagot, ha �t van f�rva annak k�rge.
     */
    public void mining()
    {
        /** A retrieveResource null-al t�r vissza, ha teleportkapun vagy �reges aszteroid�n b�ny�szn�nk, egy�b esetben pedig a b�ny�szott nyersanyaggal. */
        Resource res = currentLocation.retrieveResource();

        /** Ha nem �res aszteroid�t pr�b�lt b�ny�szni a telepes, akkor hozz�adja a res tartalm�t az inventory-hoz. */
        if(res != null)
        {
            inventory.addResource(res);
        }
    }
    /**
     * Ha elegend� nyersanyag �ll a rendelkez�s�re, akkor l�trehoz egy �j robotot.
     */
    public void createRobot()
    {
        /** Megk�s�rel a createRobot egy �j robotot l�trehozni. 
         * Ha nincs el�g nyersanyag az inventory-ban, akkor null-al t�r vissza. 
         * A robot megkapja az orbitot ha sikeres a l�trehoz�s, r�rakja mag�t, �s beregisztr�l a controllerbe is*/
        inventory.createRobot(currentLocation);
    }
    /**
     * Ha van n�la teleportkapu, akkor a soron k�vetkez� kaput p�ly�ra �ll�tja az orbit k�r�l,
     * ahol tart�zkodik.
     */
    public void placeStargate()
    {
        if(stargates.size() > 0)
        {
            /** Elhelyezi a lista 0. indexe alatt l�v� teleportkaput a place f�ggv�nnyel, 
            * majd a lehelyezett teleportkaput a remove-al elt�vol�tja a teleportkapuk list�j�b�l */
            Stargate s = stargates.get(0);
            s.place(currentLocation);
            
            stargates.remove(s);
        }
    }

    /** 
     * A what param�ter hat�rozza meg, hogy milyen nyersanagot helyezne vissza a j�t�kos. 
     */
    public void replaceResource(String what)
    {
        /** Elt�vol�tja a removeResource a what param�terben meghat�rozott nyersanyagot */
        Resource resource = inventory.removeResource(what);
        boolean replecement = false;
        if(resource != null){
             /** Jelzi, hogy sikeres volt-e a resource nyersanyag lehelyez�se. */
            replecement = currentLocation.putResource(resource);

            /** Ha sikertelen volt a lehelyez�s, akkor  a kivett nyersanyagot visszateszi az inventory-ba. */
            if(replecement == false)
            {
             inventory.addResource(resource);
            }
        }
    }
    /**
     * A telepes kezdem�nyezi a b�zis fel�p�t�s�t azon az aszteroid�n,
     * amelyiken jelenleg tart�zkodik.
     * Ehhez �sszesz�molja az aszteroid�n a t�bbi telepesn�l tal�lhat� nyersanyagokat is �s ha van el�g,
     * meg�p�tik a b�zist, ezzel megnyerik a j�t�kot(GameEnd())
     */
    public void createBase()
    {
        /** Lek�ri az �sszes utaz�t a tart�zkod�si hely�r�l �s l�trehoz egy inventory-t, amiben sz�molja, hogy el�g nyersanyaga van-e �sszesen a lista tagjainak. */
        List<Traveler> travelers = currentLocation.getTravelers();

        Inventory inventoryforbase = new Inventory(10000);


        /** Hozz�adja a travelers tagjainak az inventoriait az inventoryforbase-hez. */
        for(int i = 0; i < travelers.size(); i++)
        {
            inventoryforbase.addInventory(travelers.get(i).getInventory());
        }

        /** Megk�s�rel egy b�zist l�trehozni, true-val t�r vissza ha siker�lt, false-al ha t�l kev�s volt a nyersanyag. */
        if(inventoryforbase.createBase())
        {
            controler.endGame();
        }
    }
    /**
     * Ha jelenleg nincs vagy csak 1 darab teleportkapu van a telepesn�l,
     * akkor ha elegend� nyersanyag �ll a rendelkez�s�re,
     * akkor l�trehoz egy �j teleportkapu-p�rt.
     */
    public void createStargate()
    {
        if(stargates.size() <= 1)
        {
            List<Stargate> newgates = inventory.createStargate();

            if (newgates != null)
            {
                stargates.addAll(newgates);
                
                controler.addStargate(newgates.get(0));
                controler.addStargate(newgates.get(1));
            } 
        }
    }
    /**
     * A telepes a robban�s sor�n meghal, megh�vja �nmag�ra a Die() met�dust.
     */
    @Override
    public void explosion() 
    {
        die();
    }
    
    /**
     * Visszat�r a settlern�l l�v� stargate list�val
     * @return stargate lista
     */
    public List<Stargate> getStargates()
    {
        return stargates;
    }

    /**
     * Id -ja a settlernek
     */
    public static int id_counter = 0;
}
