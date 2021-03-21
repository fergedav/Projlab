package logic;

import java.util.ArrayList;
import java.util.List;


import skeleton.Logger;

public class Controler {
    ///Singleton stuff
    private static Controler instance;
    private boolean gameIsOn;
    private Space space;


    private Controler()
    {
        Logger.startFunctionLogComment(this, "Controler", "<<create>>");
        space = Space.getNewSpace();
        Logger.endFunctionLog();
    }

     /**
     * L�trhoz �s visszaadja, vagy ha m�r volt l�trehozva akkor visszaadja az els� l�trehozott controllert.
     * 
     * @return a Controler objektum
     */
    public static Controler getInstance()
    {
        if(instance == null)
        instance = new Controler();
        return instance;
    }
    /**
     *  tesztel�shez sz�ks�ges, �j p�ld�nyt ad vissza mindig.
     */ 
    public static Controler getNewControler()
    {
        instance = new Controler();
        return instance;
    }

    /// Singleton ends here


    
    /**
     * Csak a tesztel�s sor�n kell.
     * @return ha controller space ja.
     */
    public Space getSpace()
    {
        return space;
    }

    /**
     * a j�t�kban  �ppen aktu�lisan l�tez� �sszes Settler t�pus� objektumot t�rol� priv�t lista.
     */

    private List<Settler> settlers = new ArrayList<>();
    
    /**
     * a j�t�kban �ppen aktu�lisan l�v� �sszes Robot t�pus� objektumot t�rol� priv�t lista.
     */
    private List<Robot> robots = new ArrayList<>();

    public void initGame()
    {}

    /**
     * Elind�tja �s futtatja a j�t�kot.
     * 
     */
    public void startGame()
    {
        Logger.startFunctionLogComment(this, "startGame", "");

        gameIsOn = true;
        while(gameIsOn){

            space.step();
            for(Robot r : robots){ r.step();}
            for(Settler s : settlers){s.step();}
        }

        Logger.endFunctionLog();
    }
    /**
     * le�ll�tja a j�tkot.
     */
    public void endGame()
    {   
        Logger.startFunctionLogComment(this, "endGame", "");
        gameIsOn = false;
        Logger.endFunctionLog();

    }

    /**
     * a param�terk�nt megkapott robotot hozz�adja a robots list�j�hoz
     * @param r
     */
    public void addRobot(Robot r)
    {
        Logger.startFunctionLogComment(this, "addRobot", "");

        robots.add(r);

        Logger.endFunctionLog();
    }

    /**
     * Jegyzi egy telepes hal�l�t
     * 
     * Ha egy telepes meghal, akkor megh�vja  ezt a Settler_die() f�ggv�nyt, 
     * ami elt�vol�tja a settlers list�b�l a param�terk�nt kapott Settler objektumot
     * �s �t�rja a Settler_alive v�ltoz� �rt�k�t.
     * Ha nem marad t�bb settler, j�t�k v�g�t h�v.
     * 
     * @param s
     */
    public void settlerDie(Settler s)
    {
        Logger.startFunctionLogComment(this, "settlerDie", "");

        settlers.remove(s);

        if(settlers.size()==0)
            endGame();

        Logger.endFunctionLog();
    }

    /**
     * ha egy robot meghal, akkor megh�vja ezt a Robot_die() f�ggv�nyt, ami elt�vol�tja a robots list�b�l az param�terk�nt kapott
     * Robot objektumot.
     * @param r
     */
    public void robotDie(Robot r)
    {
        Logger.startFunctionLogComment(this, "robotDie", "");

        robots.remove(r);

        Logger.endFunctionLog();
    }
    /**
     * Hozz�adja a param�terk�nt kapott telepest a controller telepeseket tartalmaz� list�j�hoz
     * @param s
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
    }
}
