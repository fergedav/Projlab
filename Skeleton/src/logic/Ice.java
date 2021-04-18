package logic;

import skeleton.Logger;

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
        Logger.startFunctionLogComment(this, "callBack", "");

        inv.addIce(this);

        Logger.endFunctionLog();
    }
    /**
     * A j�g elp�rolog az aszteroid�b�l �gy az �res lesz.
     * A param�terk�nt kapott aszteroid�ra a RetrieveResource() met�dust h�vja.
     * @param a Asteroida, amelyikben a nyersanyag tal�lhat�
     */
    @Override
	public void reaction(Asteroid a) 
    {
        Logger.startFunctionLogComment(this, "reaction", "");
        
        a.retrieveResource();

        Logger.endFunctionLog();
    }

    //PROTO F�GGV�NYEK INNENT�L//////////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString(){
        return "Ice";
    }


}
