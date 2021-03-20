package logic;
import java.util.*; 

import java.util.List;

public class Settler extends Traveler {
    
    private List<Stargate> stargates;

    @Override
    public void die()
    {
        /** Ha van teleportkapuja a telepesnek a halálakor, akkor az összes elpusztul a telepessel együtt. */
        if(stargates != null)
        {
            for(int i = 0; i < stargates.size(); i++)
            {
                stargates.get(i).die();
            }

        /** Miután elpusztította a kapuit, azután jelzi a controllernek, hogy ő meghalt és kéri, hogy húzza ki az élő telepesek listájából (a settlerDie függvénnyel) */
            controler.settlerDie(this);
        }
    }

    @Override
    public void step()
    {}

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

    public void createRobot()
    {
        /** Megkísérel a createRobot egy új robotot létrehozni. Ha nincs elég nyersanyag az inventory-ban, akkor null-al tér vissza. */
        Robot newrobot = inventory.createRobot(currentLocation);
        
        /** Ha sikeresen létrehozta az új robotot, akkor felveszi az aktuális objektumon lévő utazók közé az új robotot az addTraveler-el, 
         * majd pedig az addRobot-al szól a controler-nek, hogy adja hozzá az új robotot az aktív robotok listájához. */
        if(newrobot != null)
        {
            currentLocation.addTraveler(newrobot);
            controler.addRobot(newrobot);
        }

    }

    public void stargate()
    {
        /** Ha a stargate gomb megnyomásakor nincs teleportkapuja a telepesnek, akkor megpróbál létrehozni egy új teleportkapu-párt. */
        if(stargates == null)
        {
            createStargate();
        }
        /** Ha pedig a stargate gomb megnyomásakor van teleportkapuja, akkor lerak egyet, a tartózkodási helyére. */
        else
        {
            placeStargate();
        }
    }

    public void placeStargate()
    {
        /** Elhelyezi a lista 0. indexe alatt lévő teleportkaput a place függvénnyel, majd a lehelyezett teleportkaput a remove-al eltávolítja a teleportkapuk listájából */
        stargates.get(0).place(currentLocation);

        /** A teleportkapuk listájának a hossza a lerakott teleportkapu, a listából való eltávolítása előtt. */
        int  sizebefore = stargates.size();
        stargates.remove(0);

        /** Ha már az utolsó teleportkapu is le lett rakva (a sizebefore, azaz a teleportkapuk listája a lerakott teleportkapu eltávolítása előtti hossza, egyenlő 1-el),
         *  akkor null-ra állítja a listát. */
        if(sizebefore ==   1)
        {
            
            stargates = null;
        }
        
    }

    /** A what paraméter határozza meg, hogy milyen nyersanagot helyezne vissza a játékos. */
    public void replaceResource(String what)
    {
        /** Eltávolítja a removeResource a what paraméterben meghatározott nyersanyagot */
        Resource resource = inventory.removeResource(what);

        /** Jelzi, hogy sikeres volt-e a resource nyersanyag lehelyezése. */
        boolean replecement = currentLocation.putResource(resource);

        /** Ha sikertelen volt a lehelyezés, akkor  a kivett nyersanyagot visszateszi az inventory-ba. */
        if(replecement == false)
        {
            inventory.addResource(resource);
        }
    }

    public void createBase()
    {
        /** Lekéri az összes utazót a tartózkodási helyéről és létrehoz egy inventory-t, amiben számolja, hogy elég nyersanyaga van-e összesen a lista tagjainak. */
        List<Traveler> travelers = currentLocation.getTravelers();
        Inventory inventoryforbase = new Inventory();

        /** Hozzáadja a travelers tagjainak az inventoriait az inventoryforbase-hez. */
        for(int i = 0; i < travelers.size(); i++)
        {
            inventoryforbase.addInventory(travelers.get(i).getInventory());
        }

        /** Megkísérel egy bázist létrehozni, true-val tér vissza ha sikerült, false-al ha túl kevés volt a nyersanyag. */
        boolean sikere_e = inventoryforbase.createBase();
    }

    public void createStargate()
    {
        /** A createStargate null-al tér vissza nem volt elég nyersanyag a kapuk létrehozásához. */
        Stargate[] newgates = inventory.createStargate();

        /** Ha sikerült létrehozni a teleportkapu-párt, akkor őket hozzáadjuk a kapuk listájához. */
        if (newgates.length != 0)
        {
            for(int i = 0; i < newgates.length; i++)
            {
                stargates.add(newgates[i]);
            }
        }
        /** Egyéb esetben null-ra állítja a listát. */
        else
        {
            stargates = null;
        }
    }
}
