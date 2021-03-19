package logic;

public class Traveler {

    protected Inventory inventory;
    protected Orbit currentLocation;
    protected Controler controler;

    public Traveler()
    {
        controler = Controler.getInstance();
    }

    public void digging()
    {}

    public void move(int index)
    {}

    public void die()
    {}

    public void explosion()
    {}

    public Inventory getInventory()
    {
        return inventory;
    }

    public void step()
    {}
}
