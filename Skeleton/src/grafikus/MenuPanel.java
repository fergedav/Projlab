package grafikus;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import logic.Asteroid;
import logic.Controller;
import logic.Orbit;
import logic.Resource;
import logic.Settler;
import logic.Stargate;

import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel implements ActionListener, IDrawable {
	private JTextField textFieldSave;
	private JTextField Load;
	private JButton btnStartGame;
	private	JButton btnEndGame;	
	private	JButton btnNewGame;
	private	JLabel lblCurrentLocationInfo;	
	private	JTextPane textPaneCurrentLocationInfo;
	public	JLabel lblSettlerInfo;	
	private	JTextPane textPaneSettlerInfo;	
	private	JButton btnSave;		
	private	JButton btnLoad;
	private Settler currentSettler;

	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		
		Font f = new Font("Arial", Font.PLAIN, 16);

		btnStartGame = new JButton("Start Game");
		btnStartGame.addActionListener(this);


		btnEndGame = new JButton("End Game");
		btnEndGame.addActionListener(this);


		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(this);


		lblCurrentLocationInfo = new JLabel("Current Location Info:");
		lblCurrentLocationInfo.setFont(f);
		
		textPaneCurrentLocationInfo = new JTextPane();
		textPaneCurrentLocationInfo.setFont(f);
		
		lblSettlerInfo = new JLabel("Settler Info:");
		lblSettlerInfo.setFont(f);
		
		textPaneSettlerInfo = new JTextPane();
		textPaneSettlerInfo.setFont(f);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(this);

		textFieldSave = new JTextField();
		textFieldSave.setColumns(10);
		
		Load = new JTextField();
		Load.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCurrentLocationInfo, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(119))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnLoad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnSave, Alignment.LEADING))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(Load, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
										.addComponent(textFieldSave, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
								.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
										.addComponent(btnStartGame)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnEndGame, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(btnNewGame, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblSettlerInfo, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
									.addComponent(textPaneCurrentLocationInfo, Alignment.LEADING)
									.addComponent(textPaneSettlerInfo, Alignment.LEADING)))
							.addGap(29))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStartGame)
						.addComponent(btnEndGame)
						.addComponent(btnNewGame))
					.addGap(18)
					.addComponent(lblCurrentLocationInfo, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPaneCurrentLocationInfo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(lblSettlerInfo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPaneSettlerInfo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(textFieldSave, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoad)
						.addComponent(Load, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);
		setLayout(groupLayout);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStartGame)
		{
			Controller.getNewControler();
			Controller.getInstance().startGame();
		}
		
	}

	public void setOrbitInfo(Orbit o)
	{
		String str = "";

		str = str + "Location: " + o.getPrefix() + "\n";
		str = str + "Neighbors:" + o.numOfNeighbor() + "\n";
		str = str + "In light: " + (o.getLight() ? "Yes" : "No") + "\n";

		//int[] c = o.getCoords();
		//str = str + "x: " + c[0] + ", y: " + c[1] + "\n";

		if(o.getClass() == Asteroid.class)
		{
			int l = o.getLayers();
			str = str + "Layers: " + ((l == 0) ? "Drilled" : l) + "\n";

			if(l != 0)
			{
				Resource r = o.getCore();
				str = str + "Core: " + (r == null ? "Empty" : r.toString()) + "\n";
			}
			
		}
		else if(o.getClass() == Stargate.class)
		{
			Stargate s = (Stargate)o;
			str = str + "Crazy: " + (s.getLight() ? "Yes" : "No") + "\n";
			str = str + "Exit: " + s.getMyTwin().getPrefix() + "\n";
			str = str + "Location: " + s.getMyStop().getPrefix() + "\n";
		}


		textPaneCurrentLocationInfo.setText(str);
	}


	public void setSettlerInfo(Settler s)
	{
		String str = "";

		str = str + "Settler: " + s.getPrefix() + "\n";
		//int[] c = s.getcurrentLocation().getCoords();
		//str = str + "x: " + c[0] + ", y: " + c[1] + "\n";
		str = str + "Carbon: " + s.getInventory().getNumOfCarbon() + "\n";
		str = str + "Ice: " + s.getInventory().getNumOfIce() + "\n";
		str = str + "Iron: " + s.getInventory().getNumOfIron() + "\n";
		str = str + "Uran: " + s.getInventory().getNumOfUran() + "\n";
		str = str + "Stargates: " + s.getStargates().size() + "\n";

		textPaneSettlerInfo.setText(str);
	}
	@Override
	public void Draw(Settler s) {
		currentSettler = s;
		setSettlerInfo(s);
		setOrbitInfo(s.getcurrentLocation());
	}
}
