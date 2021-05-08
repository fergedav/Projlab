package logic;

import java.util.Random;

public class Ufo extends Traveler {

    /**
     *
     */
    private static final long serialVersionUID = -6867257992338262330L;

    /**
     * Ufo konstruktor, egy �j Ufo l�trehoz�sa, Orbitra helyez�se �s beregisztr�l�sa a Controller-be, �s az orbitra is felteszi mag�t.
     * @param start kezd� Orbit
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
     * Eld�nti hogy hanyadik szomsz�dra l�p az Ufo
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
     * L�pteti az Uf�t
     */
    private void ufoMoves()
    {
        move(whereTo());
    }

    @Override
    /**
     * Meghal az Ufo, kiveteti mag�t a Controllerb�l is
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
     * L�p az ufo: ha tud b�ny�szni akkor b�ny�szik, am�gy mozog egy szomsz�dra
     */
    public void step() 
    {
        if(!mining())
            ufoMoves();   
    }
    
    @Override
    /**
     * Nem tud f�rni az Ufo
     */
    public void digging()
    {
        //Ufo nem fur :(
    }

    /**
     * B�ny�szik az Ufo, ha meg van f�rva az aszteroida elrakja mag�nak az anyagot
     * @return
     */
    private boolean mining()
    {
        Resource r = currentLocation.retrieveResource();

        //kiszedte, elrakja
        if(r != null)
            inventory.addResource(r);

        //sikeres volt a b�ny�szat, ha r nem null akkor megkaptam a nyeranyagot
        return r != null;
    }

    public static int id_counter = 0;
}
