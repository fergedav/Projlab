package logic;

import java.util.ArrayList;
import java.util.List;


import skeleton.Logger;

public class Controler {
    ///Singleton stuff
    private static Controler instance;
    private boolean gameIsOn;

    private Controler()
    {}

     /**
     * Létrhoz és visszaadja, vagy ha már volt létrehozva akkor visszaadja az első létrehozott controllert.
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
     *  teszteléshez szükséges, új példányt ad vissza mindig.
     */ 
    public static Controler getNewInstance()
    {
        return new Controler();
    }

    /// Singleton ends here


    /**
     * 
     */
    private Space space = Space.getInstance();

    /**
     * Csak a tesztelés során kell.
     * @return ha controller space ja.
     */
    public Space getSpace()
    {
        return space;
    }

    /**
     * a játékban  éppen aktuálisan létező összes Settler típusú objektumot tároló privát lista.
     */

    private List<Settler> settlers = new ArrayList<>();
    
    /**
     * a játékban éppen aktuálisan lévő összes Robot típusú objektumot tároló privát lista.
     */
    private List<Robot> robots = new ArrayList<>();

    public void initGame()
    {}

    /**
     * Elindítja és futtatja a játékot.
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
     * leállítja a játkot.
     */
    public void endGame()
    {   
        gameIsOn = false;
    }

    /**
     * a paraméterként megkapott robotot hozzáadja a robots listájához
     * @param r
     */
    public void addRobot(Robot r)
    {
        Logger.startFunctionLogComment(this, "addRobot", "");

        robots.add(r);

        Logger.endFunctionLog();
    }

    /**
     * Jegyzi egy telepes halálát
     * 
     * Ha egy telepes meghal, akkor meghívja  ezt a Settler_die() függvényt, 
     * ami eltávolítja a settlers listából a paraméterként kapott Settler objektumot
     * és átírja a Settler_alive változó értékét.
     * Ha nem marad több settler, játék végét hív.
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
     * ha egy robot meghal, akkor meghívja ezt a Robot_die() függvényt, ami eltávolítja a robots listából az paraméterként kapott
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
     * Hozzáadja a paraméterként kapott telepest a controller telepeseket tartalmazó listájához
     * @param s
     */
    public void addSettler(Settler s)
    {
        settlers.add(s);
    }
}
