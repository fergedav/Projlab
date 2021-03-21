package logic;


import java.util.List;
import skeleton.Logger;

public class Settler extends Traveler {
    
    private List<Stargate> stargates;

    public Settler(Orbit start)
    {
        super();
        Logger.startFunctionLogComment(this, "Settler", "<<create>>");
        currentLocation = start;
        currentLocation.addTraveler(this);
        inventory = new Inventory(10);
        Logger.endFunctionLog();
    }

    @Override
    public void die()
    {
        Logger.startFunctionLogComment(this, "die", "");

        /** kit�rli mag�t az aszteroid�b�l. */
        currentLocation.removeTraveler(this);

        /** Ha van teleportkapuja a telepesnek a hal�lakor, akkor az �sszes elpusztul a telepessel egy�tt. */
        if(stargates != null)
        {
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

    public void stargate()
    {
        Logger.startFunctionLogComment(this, "stargate", "");
        /** Ha a stargate gomb megnyom�sakor nincs teleportkapuja a telepesnek, akkor megpr�b�l l�trehozni egy �j teleportkapu-p�rt. */
        if(stargates == null)
        {
            createStargate();
        }
        /** Ha pedig a stargate gomb megnyom�sakor van teleportkapuja, akkor lerak egyet, a tart�zkod�si hely�re. */
        else
        {
            placeStargate();
        }
        Logger.endFunctionLog();
    }

    private void placeStargate()
    {
        Logger.startFunctionLogComment(this, "placeStargate", "");
        /** Elhelyezi a lista 0. indexe alatt l�v� teleportkaput a place f�ggv�nnyel, majd a lehelyezett teleportkaput a remove-al elt�vol�tja a teleportkapuk list�j�b�l */
        stargates.get(0).place(currentLocation);
        currentLocation.addNeighbour(stargates.get(0));
        stargates.remove(0);

        /** A teleportkapuk list�j�nak a hossza a lerakott teleportkapu, a list�b�l val� elt�vol�t�sa el�tt. */
        int  sizebefore = stargates.size();        

        /** Ha m�r az utols� teleportkapu is le lett rakva (a sizebefore, azaz a teleportkapuk list�ja a lerakott teleportkapu elt�vol�t�sa el�tti hossza, egyenl� 1-el),
         *  akkor null-ra �ll�tja a list�t. */
        if(sizebefore ==   1)
        {            
            stargates = null;
        }
        Logger.endFunctionLog();
        
    }

    /** A what param�ter hat�rozza meg, hogy milyen nyersanagot helyezne vissza a j�t�kos. */
    public void replaceResource(String what)
    {
        Logger.startFunctionLogComment(this, "replaceResource", "");
        /** Elt�vol�tja a removeResource a what param�terben meghat�rozott nyersanyagot */
        Resource resource = inventory.removeResource(what);

        /** Jelzi, hogy sikeres volt-e a resource nyersanyag lehelyez�se. */
        boolean replecement = currentLocation.putResource(resource);

        /** Ha sikertelen volt a lehelyez�s, akkor  a kivett nyersanyagot visszateszi az inventory-ba. */
        if(replecement == false)
        {
            inventory.addResource(resource);
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

    private void createStargate()
    {
        Logger.startFunctionLogComment(this, "createStargate", "");
        /** A createStargate null-al t�r vissza nem volt el�g nyersanyag a kapuk l�trehoz�s�hoz. */
        List<Stargate> newgates = inventory.createStargate();

        /** Ha siker�lt l�trehozni a teleportkapu-p�rt, akkor �ket hozz�adjuk a kapuk list�j�hoz. */
        if (newgates != null)
        {
           stargates = newgates;
        }
        /** Egy�b esetben null-ra �ll�tja a list�t. */
        else
        {
            stargates = null;
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
}
