package logic;


import java.util.ArrayList;
import java.util.List;
import skeleton.Logger;

public class Settler extends Traveler {
    
    /**
	 *
	 */
	private static final long serialVersionUID = 1002966729770761380L;
	//0-3 stargetes
    private List<Stargate> stargates = new ArrayList<>();

    public Settler(Orbit start)
    {
        super();
        Logger.startFunctionLogComment(this, "Settler", "<<create>>");
        currentLocation = start;
        currentLocation.addTraveler(this);
        inventory = new Inventory(10);
        setPrefix("settler_"+id_counter++);
        Logger.endFunctionLog();
    }

    @Override
    public void die()
    {
        Logger.startFunctionLogComment(this, "die", "");

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

        Logger.endFunctionLog();
    }

    @Override
    public void step()
    {}

    public void mining()
    {
        Logger.startFunctionLogComment(this, "mining", "");
        /** A retrieveResource null-al tér vissza, ha teleportkapun vagy üreges aszteroidán bányásznánk, egyéb esetben pedig a bányászott nyersanyaggal. */
        Resource res = currentLocation.retrieveResource();

        /** Ha nem üres aszteroidát próbált bányászni a telepes, akkor hozzáadja a res tartalmát az inventory-hoz. */
        if(res != null)
        {
            inventory.addResource(res);
        }
        Logger.endFunctionLog();
    }

    public void createRobot()
    {
        Logger.startFunctionLogComment(this, "createRobot", "");
        /** Megkísérel a createRobot egy új robotot létrehozni. Ha nincs elég nyersanyag az inventory-ban, akkor null-al tér vissza. */
        Robot newrobot = inventory.createRobot(currentLocation);
        
        /** Ha sikeresen létrehozta az új robotot, akkor felveszi az aktuális objektumon lévõ utazók közé az új robotot az addTraveler-el, 
         * majd pedig az addRobot-al szól a controler-nek, hogy adja hozzá az új robotot az aktív robotok listájához. */
        if(newrobot != null)
        {
            currentLocation.addTraveler(newrobot);
            controler.addRobot(newrobot);
        }
        Logger.endFunctionLog();
    }

    public void placeStargate()
    {
        Logger.startFunctionLogComment(this, "placeStargate", "");

        if(stargates.size() > 0)
        {
            /** Elhelyezi a lista 0. indexe alatt lévõ teleportkaput a place függvénnyel, 
            * majd a lehelyezett teleportkaput a remove-al eltávolítja a teleportkapuk listájából */
            Stargate s = stargates.get(0);
            s.place(currentLocation);
            
            stargates.remove(s);
        }
        
        Logger.endFunctionLog();
        
    }

    /** A what paraméter határozza meg, hogy milyen nyersanagot helyezne vissza a játékos. */
    public void replaceResource(String what)
    {
        Logger.startFunctionLogComment(this, "replaceResource", "");

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
        Logger.endFunctionLog();
    }

    public void createBase()
    {
        Logger.startFunctionLogComment(this, "createBase", "");
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
        Logger.endFunctionLog();
    }

    public void createStargate()
    {
        Logger.startFunctionLogComment(this, "createStargate", "");

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

        Logger.endFunctionLog();
    }

    @Override
    public void explosion() 
    {
        Logger.startFunctionLogComment(this, "explosion", "");
        die();
        Logger.endFunctionLog();
    }
    
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<Stargate> getStargates()
    {
        return stargates;
    }
    public void addOneStargate (Stargate s)
    {
        if(stargates.size()>0)
            stargates.add(0, s);
        else
            stargates.add(s);
    }

    public void SettlerInfo()
    {
        Controller c = Controller.getInstance();
        int settlerId = c.indexSettler(this);
        System.out.println(
            "SettlerId: "+ this.prefix+" Location: "+ c.indexOrbit(currentLocation) + " Coords: " + this.currentLocation.getCoords()[0] + " " + this.currentLocation.getCoords()[1] 
            + " Resources: Uran: " +  this.inventory.getNumOfUran() + " Ice: " + this.inventory.getNumOfIce() + " Iron: " + this.inventory.getNumOfIron()
            + " Carbon: " + this.inventory.getNumOfCarbon() + " Gates: " + this.stargates.size());
    }

    public static int id_counter = 0;

    public void addFreeStargatePair()
    {
        List<Stargate> newgates = inventory.giveFreeStargates();
        stargates.addAll(newgates);
    }
}
