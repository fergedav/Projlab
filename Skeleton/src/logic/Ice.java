package logic;

public class Ice extends Resource {

    /**
	 *
	 */
	private static final long serialVersionUID = -6702522056977305777L;
    /**
     * Ezt h�vja meg az Inventory oszt�ly mikor a j�g nyersanyagot el kell benne t�rolni,
     * megh�vja a param�ter�l kapott Inventory AddIce() f�ggv�ny�t.
     * @param inv adott inventory
     */
	@Override
    public void callBack(Inventory inv) 
    {
        inv.addIce(this);
    }
    /**
     * A j�g elp�rolog az aszteroid�b�l �gy az �res lesz.
     * A param�terk�nt kapott aszteroid�ra a RetrieveResource() met�dust h�vja.
     * @param a Asteroida, amelyikben a nyersanyag tal�lhat�
     */
    @Override
	public void reaction(Asteroid a) 
    {
        a.retrieveResource();
    }

    @Override
    public String toString(){
        return "Ice";
    }
}
