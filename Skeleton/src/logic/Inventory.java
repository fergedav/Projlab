package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5282074853486280495L;

    /**
     * A tároló teljes mérete
     */
    private final int size;

    /**
     * Adatstruktúra a nyersanyag fajták válogatott tárolására.
     */
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
     * 
     * felvesz egy nyersanyagot a materials tárolóba, ha van még hely. Az adott nyersanyag CallBack() metódusát hívja.
     */
    public void addResource(Resource r)
    {
        if(inventorySize() < size){
            r.callBack(this);
        }
    }
    /**
     * Visszaad egy adott nevû nyersanyagot.
     * 
     * Visszaad egy adott nevû nyersanyagot, ha nincs olyan nevû 
     * vagy nincs több belõle akkor null-al tér vissza.
     */
    public Resource removeResource(String rName)
    {
       try
       {
            if(materials.get(rName).size() > 0)
            {   
                Resource r = null;

                if (materials.get(rName).size() > 0)
                    r = materials.get(rName).remove(0);
                return r;
            }
       }
       catch(Exception e)
       {

       }
       return null;
        
    }

    /**
     * Callback függvény az urán hozzáadásához.
     * 
     * Az urán CallBack függvénye hívja, hogy az uránt
     * a megfelelõ helyre tegye a materials struktúrában.
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
     * A szén CallBack függvénye hívja, hogy a szenet a megfelelõ helyre tegye a materials struktúrában.
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
     * A vas CallBack függvénye hívja, hogy a vasat a megfelelõ helyre tegye a materials struktúrában.
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
     * A jég CallBack függvénye hívja, hogy a jeget a megfelelõ helyre tegye a materials struktúrában.
     * 
     * @param u a hozzáadni kívánt nyersanyag.
     */
    public void addIce(Ice i)
    {
        materials.get("Ice").add(i);     
    }
    /**
     * 
     * Segédfüggvény, ellenõrzi, hogy van-e elegendõ a megadott 
     * nyersanyagfajtákból, ha van kitörli õket és igazzal tér vissza, 
     * ha nincs, akkor nem töröl semmit, és igazzal tér vissza
     * 
     * @param Uran
     * @param Carbon
     * @param Iron
     * @param Ice
     * @return
     */
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
     * Ha van elegendõ nyersanyag az osztályban, létrehoz egy robotot és
     *  visszatér vele, a felhasznált nyersanyagot törli, egyébként null-t ad vissza.
     * 
     * @param o kezdõ helye a robotnak ha sikeres a készítés
     * @return vagy a étrehozott robot, vagy null.
     */
    public Robot createRobot(Orbit o)
    {
        if(doIHave(1, 1, 1, 0))
        {
            return new Robot(o);
        }
        return null;
    }

    /**
     * Létrehoz két stargate-t ha van rá elég nyersanyag.
     * 
     * Ha van elegendõ nyersanyag  és a játékosnál nincsen másik kapu
     *  vagy kapu-pár éppen, létrehoz egy összekapcsolt kapu-párt, 
     * és visszaadja, a felhasznált nyersanyagot törli, egyéblént null-t ad vissza.
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
     * Ellenõrzi hogy van e az adott aszteroidán elég 
     * nyersanyag a telepeseknél, hogy megépítsék az ûrbázist.
     * 
     * @return igaz ha sikerült hamis ha nem sikerült bázist felépíteni.
     */
    public boolean createBase()
    {
        Boolean b =  doIHave(3, 3, 3, 3);
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
        String[] anyagok = {"Uran","Carbon","Iron","Ice"};

        for(int i = 0; i < anyagok.length; i ++){
            materials.get(anyagok[i]).addAll(other.materials.get(anyagok[i]));
        }
    }
    public int getNumOfUran(){
        return materials.get("Uran").size();
    }
    public int getNumOfIce(){
        return materials.get("Ice").size();
    }
    public int getNumOfIron(){
        return materials.get("Iron").size();
    }
    public int getNumOfCarbon(){
        return materials.get("Carbon").size();
    }
}
