package logic;

import java.util.Random;

public class Ufo extends Traveler {

    /**
     *
     */
    private static final long serialVersionUID = -6867257992338262330L;

    /**
     * Ufo konstruktor, egy új Ufo létrehozása, Orbitra helyezése és beregisztrálása a Controller-be, és az orbitra is felteszi magát.
     * @param start kezdõ Orbit
     */
    public Ufo(Orbit start)
    {
        //remelhetoleg kelloen nagy
        inventory = new Inventory(10000);
        prefix = "ufo_"+id_counter++;
        currentLocation = start;
        currentLocation.addTraveler(this);
        Controller.getInstance().addUfo(this);
    }

    /**
     * Eldönti hogy hanyadik szomszédra lép az Ufo
     * @return
     */
    private int whereTo()
    {
        int n = 0;
        int num = currentLocation.numOfNeighbor();

        if(num == 0) 
            return 0;

        Random r = new Random();
        n = r.nextInt(num);
        return n;
    }

    /**
     * Lépteti az Ufót
     */
    private void ufoMoves()
    {
        move(whereTo());
    }

    @Override
    /**
     * Meghal az Ufo, kiveteti magát a Controllerbõl is
     */
    public void die() 
    {
        currentLocation.removeTraveler(this);
        controler.ufoDie(this);
    }

    @Override
    /**
     * felrobban az Ufo, ugyan az mint a meghal
     */
    public void explosion() 
    {
        die();   
    }

    @Override
    /**
     * Lép az ufo: ha tud bányászni akkor bányászik, amúgy mozog egy szomszédra
     */
    public void step() 
    {
        if(!mining())
            ufoMoves();   
    }
    
    @Override
    /**
     * Nem tud fúrni az Ufo
     */
    public void digging()
    {
        //Ufo nem fur :(
    }

    /**
     * Bányászik az Ufo, ha meg van fúrva az aszteroida elrakja magának az anyagot
     * @return
     */
    private boolean mining()
    {
        Resource r = currentLocation.retrieveResource();

        //kiszedte, elrakja
        if(r != null)
            inventory.addResource(r);

        //sikeres volt a bányászat, ha r nem null akkor megkaptam a nyeranyagot
        return r != null;
    }

    public static int id_counter = 0;
}
