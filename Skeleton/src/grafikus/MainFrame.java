
package grafikus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import logic.Controller;
import logic.Settler;



public class MainFrame extends JFrame implements IDrawable{
	
	MenuPanel menupanel;
	SettlerActionsPanel settlerpanel;
	GamePanel gamepanel;
	
	public static Settler currentSettler;

	public MainFrame() 
	{
		menupanel = new MenuPanel();
		settlerpanel = new SettlerActionsPanel();
		gamepanel = new GamePanel();
		
		setBackground(Color.BLUE);
		setTitle("Asteroids");
		setPreferredSize(new Dimension(1000, 800));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new JMenuBar());
		setResizable(false);

		add(menupanel, BorderLayout.EAST);
		add(settlerpanel, BorderLayout.SOUTH);
		
		add(gamepanel, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		Controller.getInstance().setFrame(this);
	}

	@Override
	public void Draw(Settler s) {
		if (s == null){
			gamepanel.Draw(s);
			return;
		}

		currentSettler = s;
		menupanel.Draw(s);
		gamepanel.Draw(s);
		settlerpanel.Draw(s);
	}
	
}
