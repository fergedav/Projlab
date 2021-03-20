package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final int size;

    private HashMap<String, ArrayList<Resource>> materials;

    public Inventory(int meret){
        size = meret;

        materials = new HashMap<String, ArrayList<Resource>>();

        materials.put("Uran", new ArrayList<Resource>());
        materials.put("Carbon", new ArrayList<Resource>());
        materials.put("Iron", new ArrayList<Resource>());
        materials.put("Ice", new ArrayList<Resource>());
        
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
        if(inventorySize() < size){
            r.callBack(this);
        }

    }
    /**
     * Visszaad egy adott nevű nyersanyagot.
     * 
     * Visszaad egy adott nevű nyersanyagot, ha nincs olyan nevű 
     * vagy nincs több belőle akkor null-al tér vissza.
     */
    public Resource removeResource(String rName)
    {
       try{
            if(materials.get(rName).size() > 0)
            return materials.get(rName).remove(0);
       }catch(Exception e){}

       return null;
        
    }

    /**
     * Callback függvény az urán hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addUran(Uran u)
    {
        materials.get("Uran").add(u);
    }
    /**
     * Callback függvény az szén hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addCarbon(Carbon c)
    {
        materials.get("Carbon").add(c);
    }
    /**
     * Callback függvény az vas hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addIron(Iron i)
    {
        materials.get("Iron").add(i);
    }
    /**
     * Callback függvény az jég hozzáadásához.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addIce(Ice i)
    {
        materials.get("Ice").add(i);
    }

    private boolean doIHave(int Uran, int Carbon, int Iron, int Ice){
        int[] nums = {Uran, Carbon, Iron, Ice};
        String[] anyagok = {"Uran","Carbon","Iron","Ice"};

        //összeszámolja hogy megvannak-e a kellő anyagok a megfelelő listákban.
        for(int i = 0; i < anyagok.length; i ++){
            if(!(materials.get(anyagok[i]).size() >= nums[i])) 
            return false;
        }
        //kiveszi a megfelelő számú anyagokat a megfelelő listából.
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
        if(doIHave(1, 1, 1, 0))
            return new Robot();
        return null;
    }

    /**
     * Létrehoz két stargate-t ha van rá elég nyersanyag.
     * 
     * @return a kreált két stargate vagy null.
     */
    public List<Stargate> createStargate()
    {
        if(doIHave(1, 0, 2, 1)){
            List<Stargate> list = new ArrayList<Stargate>();
                Stargate a = new Stargate();
                Stargate b = new Stargate();

                a.entagle(b);
                b.entagle(a);

                list.add(a);
                list.add(b);
                return list;
        }

        return null;
    }


    /**
     * Megpróbál bázist építeni.
     *
     * Ellenőrzi hogy van e az adott aszteroidán elég 
     * nyersanyag a telepeseknél, hogy megépítsék az űrbázist.
     * 
     * @return igaz ha sikerült hamis ha nem sikerült bázist felépíteni.
     */
    public boolean createBase()
    {
        return doIHave(3, 3, 3, 3);
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
        String[] anyagok = {"Uran","Carbon","Iron","Ice"};

        for(int i = 0; i < anyagok.length; i ++){
            while(other.materials.get(anyagok[i]).size() > 0){
                switch(anyagok[i]){
                    
                    case "Uran":
                        materials.get(anyagok[i]).add(new Uran());
                        break;
                    case "Carbon":
                        materials.get(anyagok[i]).add(new Carbon());
                        break;
                    case "Iron":
                        materials.get(anyagok[i]).add(new Iron());
                        break;
                    case "Ice":
                        materials.get(anyagok[i]).add(new Ice());
                        break;
                    default: break;
                }
            }
        }

        
    }
}
