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
     * A t�rol� teljes m�rete
     */
    private final int size;

    /**
     * Adatstrukt�ra a nyersanyag fajt�k v�logatott t�rol�s�ra.
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
     * 
     * felvesz egy nyersanyagot a materials t�rol�ba, ha van m�g hely. Az adott nyersanyag CallBack() met�dus�t h�vja.
     */
    public void addResource(Resource r)
    {
        if(inventorySize() < size){
            r.callBack(this);
        }
    }
    /**
     * Visszaad egy adott nev� nyersanyagot.
     * 
     * Visszaad egy adott nev� nyersanyagot, ha nincs olyan nev� 
     * vagy nincs t�bb bel�le akkor null-al t�r vissza.
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
     * Callback f�ggv�ny az ur�n hozz�ad�s�hoz.
     * 
     * Az ur�n CallBack f�ggv�nye h�vja, hogy az ur�nt
     * a megfelel� helyre tegye a materials strukt�r�ban.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addUran(Uran u)
    {
        materials.get("Uran").add(u);
    }
    /**
     * Callback f�ggv�ny az sz�n hozz�ad�s�hoz.
     * 
     * A sz�n CallBack f�ggv�nye h�vja, hogy a szenet a megfelel� helyre tegye a materials strukt�r�ban.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addCarbon(Carbon c)
    {
        materials.get("Carbon").add(c);
    }
    /**
     * Callback f�ggv�ny az vas hozz�ad�s�hoz.
     * 
     * A vas CallBack f�ggv�nye h�vja, hogy a vasat a megfelel� helyre tegye a materials strukt�r�ban.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addIron(Iron i)
    {
        materials.get("Iron").add(i);
    }
    /**
     * Callback f�ggv�ny az j�g hozz�ad�s�hoz.
     * 
     * A j�g CallBack f�ggv�nye h�vja, hogy a jeget a megfelel� helyre tegye a materials strukt�r�ban.
     * 
     * @param u a hozz�adni k�v�nt nyersanyag.
     */
    public void addIce(Ice i)
    {
        materials.get("Ice").add(i);     
    }
    /**
     * 
     * Seg�df�ggv�ny, ellen�rzi, hogy van-e elegend� a megadott 
     * nyersanyagfajt�kb�l, ha van kit�rli �ket �s igazzal t�r vissza, 
     * ha nincs, akkor nem t�r�l semmit, �s igazzal t�r vissza
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
     * Ha van elegend� nyersanyag az oszt�lyban, l�trehoz egy robotot �s
     *  visszat�r vele, a felhaszn�lt nyersanyagot t�rli, egy�bk�nt null-t ad vissza.
     * 
     * @param o kezd� helye a robotnak ha sikeres a k�sz�t�s
     * @return vagy a �trehozott robot, vagy null.
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
     * L�trehoz k�t stargate-t ha van r� el�g nyersanyag.
     * 
     * Ha van elegend� nyersanyag  �s a j�t�kosn�l nincsen m�sik kapu
     *  vagy kapu-p�r �ppen, l�trehoz egy �sszekapcsolt kapu-p�rt, 
     * �s visszaadja, a felhaszn�lt nyersanyagot t�rli, egy�bl�nt null-t ad vissza.
     * 
     * @return a kre�lt k�t stargate vagy null.
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
     * Megpr�b�l b�zist �p�teni.
     *
     * Ellen�rzi hogy van e az adott aszteroid�n el�g 
     * nyersanyag a telepesekn�l, hogy meg�p�ts�k az �rb�zist.
     * 
     * @return igaz ha siker�lt hamis ha nem siker�lt b�zist fel�p�teni.
     */
    public boolean createBase()
    {
        Boolean b =  doIHave(3, 3, 3, 3);
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
