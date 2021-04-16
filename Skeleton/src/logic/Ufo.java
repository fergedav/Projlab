package logic;

import java.util.Random;

public class Ufo extends Traveler {

    /**
     *
     */
    private static final long serialVersionUID = -6867257992338262330L;
    private Inventory inv;

    public Ufo()
    {
        //remelhetoleg kelloen nagy
        inv = new Inventory(10000);
        setPrefix("ufo_"+id_counter++);
    }

    private int whereTo()
    {
        int n = 0;
        if(behavior)
        {
            int num = currentLocation.numOfNeighbor();
            Random r = new Random(num);
            n = r.nextInt(num);
        }
        return n;
    }


    private void ufoMoves()
    {
        move(whereTo());
    }

    @Override
    public void die() {
        currentLocation.removeTraveler(this);
        controler.ufoDie(this);
    }

    @Override
    public void explosion() {
        die();   
    }

    @Override
    public void step() {
        if(!mining())
            ufoMoves();
        
    }
    
    @Override
    public void digging()
    {
        //Ufo nem fur :(
    }

    private boolean mining()
    {
        //nem kene null check mert az van a step-ben, mining csak nem null core eseten hivodik, 
        //de explicit hivasok miatt inkabb rakok
        Resource r = currentLocation.retrieveResource();
        if(r != null) inv.addResource(r);

        return r != null;
    }
    
    //PROTO FÜGGVÉNYEK INNENTÕL//////////////////////////////////////////////////////////////////////////////////////////////////////

 /**
     * Determinisztikus - random viselkedes
     */
    private boolean behavior;
    /**
     * Determinisztikus - random viselkedeshez
     * 
     */
    public void setBehavior(boolean det_rand)
    {
        behavior = det_rand;
    }

    public void setLocation(Orbit o){
        currentLocation = o;
    }


    public void ufoInfo(){
        Controller c = Controller.getInstance();
        int ufoId = c.indexUfo(this);
        System.out.println(
            "UfoId: Ufo_"+ ufoId+" Location: "+ c.indexOrbit(currentLocation)+" Resources: Uran: "+
            this.getInventory().getNumOfUran()+" Ice: "+ this.getInventory().getNumOfIce()+ " Iron: "+
            this.getInventory().getNumOfIron()+" Carbon: "+ this.getInventory().getNumOfCarbon()
        );
    }

    public static int id_counter = 0;
}
