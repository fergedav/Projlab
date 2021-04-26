package grafikus;

import java.awt.Color;

import javax.swing.*;

import logic.Settler;

public class GamePanel extends JPanel implements IDrawable{

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setBackground(Color.DARK_GRAY);

	}

	@Override
	public void Draw(Settler s)
	{
		//TODO játék rajzolása mindennel
	}

}
