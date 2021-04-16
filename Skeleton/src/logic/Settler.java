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

        Logger.endFunctionLog();
    }

    @Override
    public void step()
    {}

    public void mining()
    {
        Logger.startFunctionLogComment(this, "mining", "");
        /** A retrieveResource null-al t�r vissza, ha teleportkapun vagy �reges aszteroid�n b�ny�szn�nk, egy�b esetben pedig a b�ny�szott nyersanyaggal. */
        Resource res = currentLocation.retrieveResource();

        /** Ha nem �res aszteroid�t pr�b�lt b�ny�szni a telepes, akkor hozz�adja a res tartalm�t az inventory-hoz. */
        if(res != null)
        {
            inventory.addResource(res);
        }
        Logger.endFunctionLog();
    }

    public void createRobot()
    {
        Logger.startFunctionLogComment(this, "createRobot", "");
        /** Megk�s�rel a createRobot egy �j robotot l�trehozni. Ha nincs el�g nyersanyag az inventory-ban, akkor null-al t�r vissza. */
        Robot newrobot = inventory.createRobot(currentLocation);
        
        /** Ha sikeresen l�trehozta az �j robotot, akkor felveszi az aktu�lis objektumon l�v� utaz�k k�z� az �j robotot az addTraveler-el, 
         * majd pedig az addRobot-al sz�l a controler-nek, hogy adja hozz� az �j robotot az akt�v robotok list�j�hoz. */
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
            /** Elhelyezi a lista 0. indexe alatt l�v� teleportkaput a place f�ggv�nnyel, 
            * majd a lehelyezett teleportkaput a remove-al elt�vol�tja a teleportkapuk list�j�b�l */
            Stargate s = stargates.get(0);
            s.place(currentLocation);
            
            stargates.remove(s);
        }
        
        Logger.endFunctionLog();
        
    }

    /** A what param�ter hat�rozza meg, hogy milyen nyersanagot helyezne vissza a j�t�kos. */
    public void replaceResource(String what)
    {
        Logger.startFunctionLogComment(this, "replaceResource", "");

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
        Logger.endFunctionLog();
    }

    public void createBase()
    {
        Logger.startFunctionLogComment(this, "createBase", "");
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
    
    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

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
