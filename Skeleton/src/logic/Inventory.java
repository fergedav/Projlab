package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import skeleton.Logger;

public class Inventory {
    private final int size;

    private HashMap<String, ArrayList<Resource>> materials;

    public Inventory(int meret){
        Logger.startFunctionLogComment(this, "Inventory", "<<create>>");
        size = meret;

        materials = new HashMap<String, ArrayList<Resource>>();

        materials.put("Uran", new ArrayList<Resource>());
        materials.put("Carbon", new ArrayList<Resource>());
        materials.put("Iron", new ArrayList<Resource>());
        materials.put("Ice", new ArrayList<Resource>());

        Logger.endFunctionLog();
        
    }
    /** 
     * Visszaadja a tárolt nyersanyagok számát
     * 
     * @return az összes tárolt nyersanyagok száma
     */
    private int inventorySize()
    {
        int sum = 0;
        for(Map.Entry<String, ArrayList<Resource>> list : materials.entrySet()){
            sum += list.getValue().size(); 
        }
        return sum;
    }
    
    /** 
     * Hozzáad egy nyersanyagot a tárolóhoz ha van benne még hely.
     */
    public void addResource(Resource r)
    {
        Logger.startFunctionLogComment(this, "addResource", "");
        // TODO ez itt nem <= kene legyen?
        if(inventorySize() < size){
            r.callBack(this);
        }
        Logger.endFunctionLog();
    }
    /**
     * Visszaad egy adott nevû nyersanyagot.
     * 
     * Visszaad egy adott nevû nyersanyagot, ha nincs olyan nevû 
     * vagy nincs több belõle akkor null-al tér vissza.
     */
    public Resource removeResource(String rName)
    {
        Logger.startFunctionLogComment(this, "removeResource", "");

       try{
            if(materials.get(rName).size() > 0)
            {
                Resource r = materials.get(rName).remove(0);

                Logger.endFunctionLog();

                return r;
            }
       }catch(Exception e){

       }

       Logger.endFunctionLog();

       return null;
        
    }

    /**
     * Callback függvény az urán hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addUran(Uran u)
    {

        Logger.startFunctionLogComment(this, "addUran", "");

        materials.get("Uran").add(u);

        Logger.endFunctionLog();

    }
    /**
     * Callback függvény az szén hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addCarbon(Carbon c)
    {

        Logger.startFunctionLogComment(this, "addCarbon", "");

        materials.get("Carbon").add(c);

        Logger.endFunctionLog();

    }
    /**
     * Callback függvény az vas hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addIron(Iron i)
    {

        Logger.startFunctionLogComment(this, "addIron", "");

        materials.get("Iron").add(i);      

        Logger.endFunctionLog();
        
    }
    /**
     * Callback függvény az jég hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addIce(Ice i)
    {
        Logger.startFunctionLogComment(this, "addIce", "");

        materials.get("Ice").add(i);     

        Logger.endFunctionLog();
    }

    private boolean doIHave(int Uran, int Carbon, int Iron, int Ice){
        int[] nums = {Uran, Carbon, Iron, Ice};
        String[] anyagok = {"Uran","Carbon","Iron","Ice"};

        //összeszámolja hogy megvannak-e a kellõ anyagok a megfelelõ listákban.
        for(int i = 0; i < anyagok.length; i ++){
            if(!(materials.get(anyagok[i]).size() >= nums[i])) 
            return false;
        }
        //kiveszi a megfelelõ számú anyagokat a megfelelõ listából.
        for(int i = 0; i < anyagok.length; i ++){
            for(int j = 0; j < nums[i]; j++){
                materials.get(anyagok[i]).remove(0);
            }
        }
        return true;
    }

    /**
     * Léterehoz egy robotot, ha van elég nyersanyag.
     * 
     * @param o -nem használt.
     * @return vagy a étrehozott robot, vagy null.
     */
    public Robot createRobot(Orbit o)
    {
        Logger.startFunctionLogComment(this, "createRobot", "");   

        if(doIHave(1, 1, 1, 0))
        {
            Logger.endFunctionLog();
            return new Robot();
        }
        Logger.endFunctionLog();
        return null;
    }

    /**
     * Létrehoz két stargate-t ha van rá elég nyersanyag.
     * 
     * @return a kreált két stargate vagy null.
     */
    public List<Stargate> createStargate()
    {
        Logger.startFunctionLogComment(this, "createStargate", "");

        if(doIHave(1, 0, 2, 1)){
            List<Stargate> list = new ArrayList<Stargate>();
            Stargate a = new Stargate();
            Stargate b = new Stargate();

            a.entagle(b);
            b.entagle(a);

            list.add(a);
            list.add(b);

            Logger.endFunctionLog();

            return list;
        }
        Logger.endFunctionLog();
        
        return null;
    }


    /**
     * Megpróbál bázist építeni.
     *
     * Ellenõrzi hogy van e az adott aszteroidán elég 
     * nyersanyag a telepeseknél, hogy megépítsék az ûrbázist.
     * 
     * @return igaz ha sikerült hamis ha nem sikerült bázist felépíteni.
     */
    public boolean createBase()
    {
        Logger.startFunctionLogComment(this, "createBase", "");

        Boolean b =  doIHave(3, 3, 3, 3);

        Logger.endFunctionLog();

        return b;
    }
    /**
     * Hozzáadja a kapott inventorit a sajátjához.
     * 
     * Végig iterál a másik Inventory tárolóján, és hozzáadja elemcsoportonként a sajátjához.
     * 
     * @param other a másik inventory.
     */
    public void addInventory(Inventory other)
    {
        Logger.startFunctionLogComment(this, "addInventory", "");

        String[] anyagok = {"Uran","Carbon","Iron","Ice"};

        for(int i = 0; i < anyagok.length; i ++){
            materials.get(anyagok[i]).addAll(other.materials.get(anyagok[i]));
        }
        
        Logger.endFunctionLog();
        
    }

}
