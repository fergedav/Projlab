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
     * Visszaadja a t�rolt nyersanyagok sz�m�t
     * 
     * @return az �sszes t�rolt nyersanyagok sz�ma
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
     * Hozz�ad egy nyersanyagot a t�rol�hoz ha van benne m�g hely.
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
     * Visszaad egy adott nev� nyersanyagot.
     * 
     * Visszaad egy adott nev� nyersanyagot, ha nincs olyan nev� 
     * vagy nincs t�bb bel�le akkor null-al t�r vissza.
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
     * Callback f�ggv�ny az ur�n hozz�ad�s�hoz.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addUran(Uran u)
    {

        Logger.startFunctionLogComment(this, "addUran", "");

        materials.get("Uran").add(u);

        Logger.endFunctionLog();

    }
    /**
     * Callback f�ggv�ny az sz�n hozz�ad�s�hoz.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addCarbon(Carbon c)
    {

        Logger.startFunctionLogComment(this, "addCarbon", "");

        materials.get("Carbon").add(c);

        Logger.endFunctionLog();

    }
    /**
     * Callback f�ggv�ny az vas hozz�ad�s�hoz.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addIron(Iron i)
    {

        Logger.startFunctionLogComment(this, "addIron", "");

        materials.get("Iron").add(i);      

        Logger.endFunctionLog();
        
    }
    /**
     * Callback f�ggv�ny az j�g hozz�ad�s�hoz.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
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

        //�sszesz�molja hogy megvannak-e a kell� anyagok a megfelel� list�kban.
        for(int i = 0; i < anyagok.length; i ++){
            if(!(materials.get(anyagok[i]).size() >= nums[i])) 
            return false;
        }
        //kiveszi a megfelel� sz�m� anyagokat a megfelel� list�b�l.
        for(int i = 0; i < anyagok.length; i ++){
            for(int j = 0; j < nums[i]; j++){
                materials.get(anyagok[i]).remove(0);
            }
        }
        return true;
    }

    /**
     * L�terehoz egy robotot, ha van el�g nyersanyag.
     * 
     * @param o -nem haszn�lt.
     * @return vagy a �trehozott robot, vagy null.
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
     * L�trehoz k�t stargate-t ha van r� el�g nyersanyag.
     * 
     * @return a kre�lt k�t stargate vagy null.
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
     * Megpr�b�l b�zist �p�teni.
     *
     * Ellen�rzi hogy van e az adott aszteroid�n el�g 
     * nyersanyag a telepesekn�l, hogy meg�p�ts�k az �rb�zist.
     * 
     * @return igaz ha siker�lt hamis ha nem siker�lt b�zist fel�p�teni.
     */
    public boolean createBase()
    {
        Logger.startFunctionLogComment(this, "createBase", "");

        Boolean b =  doIHave(3, 3, 3, 3);

        Logger.endFunctionLog();

        return b;
    }
    /**
     * Hozz�adja a kapott inventorit a saj�tj�hoz.
     * 
     * V�gig iter�l a m�sik Inventory t�rol�j�n, �s hozz�adja elemcsoportonk�nt a saj�tj�hoz.
     * 
     * @param other a m�sik inventory.
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
