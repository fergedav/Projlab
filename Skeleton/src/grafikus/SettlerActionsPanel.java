package grafikus;


import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import logic.Controller;
import logic.Settler;

import javax.swing.JComboBox;

public class SettlerActionsPanel extends JPanel implements ActionListener{

	private JButton btnSettlerDig;

	/**
	 * Create the panel.
	 */
	public SettlerActionsPanel() {
		
		JButton btnSettlerMove = new JButton("Move");
		
		btnSettlerDig = new JButton("Dig Asteroid");
		btnSettlerDig.addActionListener(this);
		JButton btnSettlerMine = new JButton("Mine Asteroid");
		
		JButton btnCreateStargate = new JButton("Create Stargate");
		
		JButton btnPlaceStargate = new JButton("Place Stargate");
		
		JButton btnCreateRobot = new JButton("Create Robot");
		
		JButton btnCreateBase = new JButton("Create Base");
		
		JComboBox comboBoxSettlerMove = new JComboBox();
		
		JButton btnReplaceResource = new JButton("Replace Resource");
		
		JComboBox comboBoxReplaceResource = new JComboBox();

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

int c = 0;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSettlerDig)
		{
			MainFrame.currentSettler.digging();
			btnSettlerDig.setText(""+c++);
			Controller.getInstance().NextSetller();
		}
	}

	void setSettler(Settler s)
	{

	}
}
