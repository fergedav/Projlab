package grafikus;


import java.awt.event.*;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import logic.Controller;
import logic.Orbit;
import logic.Settler;

import javax.swing.JComboBox;

public class SettlerActionsPanel extends JPanel implements ActionListener, IDrawable{

	private JButton btnSettlerMove;
	private JButton btnSettlerDig;
	private JButton btnSettlerMine;
	private JButton btnCreateStargate;
	private	JButton btnPlaceStargate;
	private	JButton btnCreateRobot;
	private	JButton btnCreateBase;
	private	JComboBox<Orbit> comboBoxSettlerMove;
	private	JButton btnReplaceResource;
	private	JComboBox<String> comboBoxReplaceResource;
	private Settler currentSettler;

	/**
	 * Create the panel.
	 */
	public SettlerActionsPanel() {
		
		btnSettlerMove = new JButton("Move");
		btnSettlerMove.addActionListener(this);

		btnSettlerDig = new JButton("Dig Asteroid");
		btnSettlerDig.addActionListener(this);

		btnSettlerMine = new JButton("Mine Asteroid");
		btnSettlerMine.addActionListener(this);

		btnCreateStargate = new JButton("Create Stargate");
		btnCreateStargate.addActionListener(this);

		btnPlaceStargate = new JButton("Place Stargate");
		btnPlaceStargate.addActionListener(this);

		btnCreateRobot = new JButton("Create Robot");
		btnCreateRobot.addActionListener(this);

		btnCreateBase = new JButton("Create Base");
		btnCreateBase.addActionListener(this);

		comboBoxSettlerMove = new JComboBox<Orbit>();
		comboBoxSettlerMove.addActionListener(this);

		btnReplaceResource = new JButton("Replace Resource");
		btnReplaceResource.addActionListener(this);

		comboBoxReplaceResource = new JComboBox<String>();
		comboBoxReplaceResource.addItem("Iron");
		comboBoxReplaceResource.addItem("Carbon");
		comboBoxReplaceResource.addItem("Ice");
		comboBoxReplaceResource.addItem("Uran");
		comboBoxReplaceResource.addActionListener(this);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBoxSettlerMove, 0, 131, Short.MAX_VALUE)
						.addComponent(btnSettlerMove, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSettlerDig, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSettlerMine, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreateStargate, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPlaceStargate, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreateRobot, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCreateBase, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnReplaceResource, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
						.addComponent(comboBoxReplaceResource, 0, 121, Short.MAX_VALUE))
					.addGap(20))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCreateRobot, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCreateBase, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSettlerMove, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxSettlerMove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnSettlerDig, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSettlerMine, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnCreateStargate, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnPlaceStargate, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnReplaceResource, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxReplaceResource, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSettlerDig)
		{
			currentSettler.digging();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnSettlerMine)
		{
			currentSettler.mining();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnCreateStargate)
		{
			currentSettler.createStargate();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnPlaceStargate)
		{
			currentSettler.placeStargate();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnCreateRobot)
		{
			currentSettler.createRobot();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnCreateBase)
		{
			currentSettler.createBase();
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnReplaceResource)
		{
			currentSettler.replaceResource((String)comboBoxReplaceResource.getSelectedItem());
			Controller.getInstance().NextSetller();
		}
		if(e.getSource() == btnSettlerMove)
		{
			currentSettler.move(comboBoxReplaceResource.getSelectedIndex());
			Controller.getInstance().NextSetller();
		}
		
	}

	@Override
	public void Draw(Settler s) {
		currentSettler = s;
		comboBoxSettlerMove.removeAllItems();
		List<Orbit> n = s.getcurrentLocation().getNeighborList();
		for (Orbit o : n) {
			comboBoxSettlerMove.addItem(o);
		}
		
	}
}
