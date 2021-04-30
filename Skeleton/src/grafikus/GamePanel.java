package grafikus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.*;

import logic.Asteroid;
import logic.Orbit;
import logic.Robot;
import logic.Settler;
import logic.Traveler;

public class GamePanel extends JPanel implements IDrawable{

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setBackground(Color.DARK_GRAY);
		try {
			asteroidImage = new ImageIcon("Skeleton/src/assets/asteroid.png").getImage();
			stargateImage = new ImageIcon("Skeleton/src/assets/stargate.png").getImage();
			settlerImage = new ImageIcon("Skeleton/src/assets/settler.png").getImage();
			ufoImage = new ImageIcon("Skeleton/src/assets/ufo.png").getImage();
			robotImage = new ImageIcon("Skeleton/src/assets/robot.png").getImage();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	Image asteroidImage;
	Image stargateImage;
	Image settlerImage;
	Image ufoImage;
	Image robotImage;

	Settler currentSettler = null;

	@Override
	public void Draw(Settler s)
	{
		//TODO j˜t˜k rajzol˜sa mindennel
		currentSettler = s;
		revalidate();
	}

	private void drawOrbits(Graphics g, Orbit o)
	{
		Image orbitImage = (o.getClass() == Asteroid.class) ? asteroidImage : stargateImage;
		int xc = getWidth() / 2 - orbitImage.getWidth(this) / 2;
		int yc = getHeight() / 2 - orbitImage.getHeight(this) / 2;

		//center orbit
		g.drawImage(orbitImage, xc, yc, this);

		drawTravelers(g, o, xc, yc);
		
		List<Orbit> neighbors = o.getNeighborList();
		int scale = 64;
		int numOfNeighbors = neighbors.size();

		for(int i = 0; i < neighbors.size(); i++)
		{
			double deg = Math.toRadians(360 / numOfNeighbors * i);
			int x = (int)Math.cos(deg) * scale + (getWidth() / 2);
			int y = (int)Math.sin(deg) * scale + (getHeight() / 2);

			g.drawImage((neighbors.get(i).getClass() == Asteroid.class) ? asteroidImage : stargateImage,
					x, y, this);
			
			drawTravelers(g, neighbors.get(i), x, y);
		}
	}

	private void drawTravelers(Graphics g, Orbit o, int x, int y)
	{
		List<Traveler> travelers = o.getTravelers();
		for (int i = 0; i < Math.min(travelers.size(), 9); i++)
		{
			Traveler t = travelers.get(i);
			Image travelerImage = (t.getClass() == Settler.class) ? settlerImage : (t.getClass() == Robot.class ? robotImage : ufoImage);

			g.drawImage(travelerImage, 
			x + (9 % 3 * 20), 
			y + (9 / 3 * 20), 
			16, 16, this);
		}
	}

	@Override
    public void paintComponent(Graphics g) 
	{
    	super.paintComponent(g);

		if(currentSettler == null)
			return;

        drawOrbits(g, currentSettler.getcurrentLocation());
    }
	
}
