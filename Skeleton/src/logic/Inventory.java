package logic;

import java.util.ArrayList;
import java.util.List;

import skeleton.Logger;


public class Inventory {
    private final int size = 10;
    private List<Resource> materials = new ArrayList<>();
    

    public void addResource(Resource r)
    {

        if(materials.size() < size)
            Logger.log(r, "r", "callBack", "epik callback", this);
    }

    public Resource removeResource(String rName)
    {
        return null;
    }

    public void addUran(Uran u)
    {

    }

    public void addIron(Iron i)
    {
        
    }
    public void addCarbon(Carbon c)
    {
        
    }
    public void addIce(Ice i)
    {
        
    }

    public Robot createRobot(Orbit o)
    {
        return null;
    }
    
    public Stargate[] createStargate()
    {
        return null;
    }

    public Resource[] countResources()
    {
        return null;
    }

    public boolean createBase()
    {
        return false;
    }

    public void addInventory(Inventory i)
    {

    }
}
