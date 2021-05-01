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
     * @param start kezdõ orbitja a settlernek
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
     * Az aszteroidára, amin jelenleg tartózkodik, önmagával (saját magát adja meg paraméterként) 
     * meghívja a RemoveTraveler(Traveler t) metódust, 
     * majd a controller-nek is jelzi önmagával meghívva Settler_die(Settler s) metódust, 
     * majd törli önmagát.
     */
    @Override
    public void die()
    {
        /** kitörli magát az aszteroidából. */
        currentLocation.removeTraveler(this);

        /** Ha van teleportkapuja a telepesnek a halálakor, akkor az összes elpusztul a telepessel együtt. */
        if(stargates.size() != 0)
        {
            if(stargates.size() == 3)
            {
                stargates.get(2).die();
            }
           stargates.get(0).die();
           
        }

        /** Miután elpusztította a kapuit, azután jelzi a controllernek, hogy õ meghalt és kéri, hogy húzza ki az élõ telepesek listájából (a settlerDie függvénnyel) */
        controler.settlerDie(this);
    }
    /**
     * A játékos kiválaszthat egy mûveletet(fúrhat, bányászhat, mozoghat, építhet),
     * amit szeretne a telepesével elvégeztetni.
     * Jelenleg NEM használjuk a protoban.
     */
    @Override
    public void step()
    {}
    /**
     * Kibányássza az aszteroidában található nyersanyagot, ha át van fúrva annak kérge.
     */
    public void mining()
    {
        /** A retrieveResource null-al tér vissza, ha teleportkapun vagy üreges aszteroidán bányásznánk, egyéb esetben pedig a bányászott nyersanyaggal. */
        Resource res = currentLocation.retrieveResource();

        /** Ha nem üres aszteroidát próbált bányászni a telepes, akkor hozzáadja a res tartalmát az inventory-hoz. */
        if(res != null)
        {
            inventory.addResource(res);
        }
    }
    /**
     * Ha elegendõ nyersanyag áll a rendelkezésére, akkor létrehoz egy új robotot.
     */
    public void createRobot()
    {
        /** Megkísérel a createRobot egy új robotot létrehozni. 
         * Ha nincs elég nyersanyag az inventory-ban, akkor null-al tér vissza. 
         * A robot megkapja az orbitot ha sikeres a létrehozás, rárakja magát, és beregisztrál a controllerbe is*/
        inventory.createRobot(currentLocation);
    }
    /**
     * Ha van nála teleportkapu, akkor a soron következõ kaput pályára állítja az orbit körül,
     * ahol tartózkodik.
     */
    public void placeStargate()
    {
        if(stargates.size() > 0)
        {
            /** Elhelyezi a lista 0. indexe alatt lévõ teleportkaput a place függvénnyel, 
            * majd a lehelyezett teleportkaput a remove-al eltávolítja a teleportkapuk listájából */
            Stargate s = stargates.get(0);
            s.place(currentLocation);
            
            stargates.remove(s);
        }
    }

    /** 
     * A what paraméter határozza meg, hogy milyen nyersanagot helyezne vissza a játékos. 
     */
    public void replaceResource(String what)
    {
        /** Eltávolítja a removeResource a what paraméterben meghatározott nyersanyagot */
        Resource resource = inventory.removeResource(what);
        boolean replecement = false;
        if(resource != null){
             /** Jelzi, hogy sikeres volt-e a resource nyersanyag lehelyezése. */
            replecement = currentLocation.putResource(resource);

            /** Ha sikertelen volt a lehelyezés, akkor  a kivett nyersanyagot visszateszi az inventory-ba. */
            if(replecement == false)
            {
             inventory.addResource(resource);
            }
        }
    }
    /**
     * A telepes kezdeményezi a bázis felépítését azon az aszteroidán,
     * amelyiken jelenleg tartózkodik.
     * Ehhez összeszámolja az aszteroidán a többi telepesnél található nyersanyagokat is és ha van elég,
     * megépítik a bázist, ezzel megnyerik a játékot(GameEnd())
     */
    public void createBase()
    {
        /** Lekéri az összes utazót a tartózkodási helyérõl és létrehoz egy inventory-t, amiben számolja, hogy elég nyersanyaga van-e összesen a lista tagjainak. */
        List<Traveler> travelers = currentLocation.getTravelers();

        Inventory inventoryforbase = new Inventory(10000);


        /** Hozzáadja a travelers tagjainak az inventoriait az inventoryforbase-hez. */
        for(int i = 0; i < travelers.size(); i++)
        {
            inventoryforbase.addInventory(travelers.get(i).getInventory());
        }

        /** Megkísérel egy bázist létrehozni, true-val tér vissza ha sikerült, false-al ha túl kevés volt a nyersanyag. */
        if(inventoryforbase.createBase())
        {
            controler.endGame();
        }
    }
    /**
     * Ha jelenleg nincs vagy csak 1 darab teleportkapu van a telepesnél,
     * akkor ha elegendõ nyersanyag áll a rendelkezésére,
     * akkor létrehoz egy új teleportkapu-párt.
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
     * A telepes a robbanás során meghal, meghívja önmagára a Die() metódust.
     */
    @Override
    public void explosion() 
    {
        die();
    }
    
    /**
     * Visszatér a settlernél lévõ stargate listával
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
