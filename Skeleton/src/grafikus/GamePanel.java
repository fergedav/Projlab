package grafikus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.*;
import java.awt.Font;
import java.awt.FontMetrics;

import logic.Asteroid;
import logic.Controller;
import logic.Orbit;
import logic.Resource;
import logic.Robot;
import logic.Settler;
import logic.Traveler;
import logic.Controller.GameState;

public class GamePanel extends JPanel implements IDrawable{

	/**
	 * Create the panel.
	 */
	public GamePanel() {
		setBackground(Color.BLACK);

		String basePath = "Skeleton/src/assets/";

		asteroidImage = new ImageIcon(basePath + "asteroid.png").getImage();
		stargateImage = new ImageIcon(basePath + "stargate.png").getImage();
		settlerImage = new ImageIcon(basePath + "settler.png").getImage();
		ufoImage = new ImageIcon(basePath + "ufo.png").getImage();
		robotImage = new ImageIcon(basePath + "robot.png").getImage();

		carbonImage = new ImageIcon(basePath + "carbon.png").getImage();
		ironImage = new ImageIcon(basePath + "iron.png").getImage();
		iceImage = new ImageIcon(basePath + "ice.png").getImage();
		uranImage = new ImageIcon(basePath + "uran.png").getImage();
	}

	Image asteroidImage;
	Image stargateImage;
	Image settlerImage;
	Image ufoImage;
	Image robotImage;
	Image carbonImage;
	Image ironImage;
	Image iceImage;
	Image uranImage;

	Settler currentSettler = null;
	/**
	 * random szög hogy ne mindig ugyanúgy legyenek az aszteroidák 
	 */
	int phi;
	/**
	 * hány pixel legyen a karakterek mérete
	 */
	int spriteSize = 96;
	/**
	 * Napfény színe
	 */
	Color lightColor = new  Color(220, 200, 10, 64);

	@Override
	public void Draw(Settler s)
	{
		currentSettler = s;
		phi = (int)(Math.random() * 360);
		repaint();
	}

	private void drawOrbit(Graphics g, Orbit o, int x, int y)
	{
		Image orbitImage = (o.getClass() == Asteroid.class) ? asteroidImage : stargateImage;

			g.drawImage(orbitImage,
					x, y,96,96, this);
			
			g.setColor(Color.WHITE);
			g.drawString(o.getPrefix(), x, y-4);

			drawTravelers(g, o, x, y);

			Resource core = o.getCore();
			if(core != null)
			{
				Image resourecImage = null;

				switch (core.toString()) {
					case "Carbon":
						resourecImage = carbonImage;
						break;
					case "Ice":
						resourecImage = iceImage;
						break;
					case "Iron":
						resourecImage = ironImage;
						break;
					case "Uran":
						resourecImage = uranImage;
						break;
				}

				int smallSize = spriteSize / 4;
				int offset = smallSize / 4;

				g.drawImage(resourecImage, 
				x + (1 * (smallSize+offset)) + offset, 
				y + (1 * (smallSize+offset)) + offset, 
				smallSize, smallSize, this);
			}

			if(o.getLight())
			{
				g.setColor(lightColor);
				g.fillRect(x, y, spriteSize, spriteSize);
			}
	}

	private void drawOrbits(Graphics g, Orbit o)
	{
		//Image orbitImage = (o.getClass() == Asteroid.class) ? asteroidImage : stargateImage;
		int xc = getWidth() / 2 - spriteSize / 2;
		int yc = getHeight() / 2 - spriteSize / 2;

		//center orbit
		drawOrbit(g, o, xc, yc);
		
		List<Orbit> neighbors = o.getNeighborList();
		int distance = 256;
		int numOfNeighbors = neighbors.size();
		
		for(int i = 0; i < neighbors.size(); i++)
		{
			Orbit neighbor = neighbors.get(i);

			double deg = 360 / numOfNeighbors * (i+1) + phi;
			deg = Math.min(deg, deg % 360);

			double rad = Math.toRadians(deg);
			int x = (int)(Math.cos(rad) * distance) + xc;
			int y = (int)(Math.sin(rad) * distance) + yc;
			
			drawOrbit(g, neighbor, x, y);
		}
	}

	private void drawTravelers(Graphics g, Orbit o, int x, int y)
	{
		List<Traveler> travelers = o.getTravelers();
		int j = 0;
		for (int i = 0; i < Math.min(travelers.size(), 8); i++)
		{
			//középsõ hely kihagyása a nyersanyagnak
			if(i == 4)
			{
				j++;
				continue;
			}

			Traveler t = travelers.get(i);
			Image travelerImage = (t.getClass() == Settler.class) ? settlerImage : (t.getClass() == Robot.class ? robotImage : ufoImage);

			int smallSize = spriteSize / 4;
			int offset = smallSize / 4;

			g.drawImage(travelerImage, 
			x + (j % 3 * (smallSize+offset)) + offset, 
			y + (j / 3 * (smallSize+offset)) + offset, 
			smallSize, smallSize, this);
			j++;
		}
	}

	@Override
    public void paintComponent(Graphics g) 
	{
    	super.paintComponent(g);

		if(Controller.getInstance().getGameState() == GameState.Lost)
		{
			Font f = new Font("Arial", Font.PLAIN, 32);
			g.setColor(Color.RED);
			Font old = g.getFont();
			g.setFont(f);
			FontMetrics m = g.getFontMetrics(f);
			g.drawString("GAME OVER", (getWidth() - m.stringWidth("GAME OVER")) / 2,( getHeight() - m.getHeight()) / 2 );
			g.setFont(old);
			return;
		}
		else if(Controller.getInstance().getGameState() == GameState.Won)
		{
			Font f = new Font("Arial", Font.PLAIN, 32);
			g.setColor(Color.GREEN);
			Font old = g.getFont();
			g.setFont(f);
			FontMetrics m = g.getFontMetrics(f);
			g.drawString("YOU WON!", (getWidth() - m.stringWidth("YOU WON!")) / 2,( getHeight() - m.getHeight()) / 2 );
			g.setFont(old);
			return;
		}

		if(currentSettler == null)
			return;

        drawOrbits(g, currentSettler.getcurrentLocation());
		int rounds = Controller.getInstance().getSunstormTime();
		g.setColor(Color.WHITE);
		g.drawString(rounds == 0 ? "Sunstorm arrived!" : "Sunstorm in " + rounds + " rounds.", 10, 20);
    }
	
}
