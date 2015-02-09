package aquarium;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import aquarium.creatures.*;
import aquarium.ui.AquariumFrame;

public class Aquarium
{
	public static final int WIDTH = 900;
	public static final int HEIGHT = 300;
	public static final int SKY_HEIGHT = 300;
	
	private ArrayList<Creature> creatures;
	private Timer updateTimer;
	private AquariumFrame frame = null;
	
	public Aquarium()
	{
		this.creatures = new ArrayList<Creature>();
		
		ActionListener timerAction = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateCreatureLocations();
			}
		};
		this.updateTimer = new Timer(100, timerAction);
		this.updateTimer.setRepeats(true);
	}
	
	public ArrayList<Creature> getCreatures()
	{
		return creatures;
	}
	
	public void fillWithCreatures()
	{
		creatures.add(new BoringFish("Bob"));
	}

	public void empty()
	{
		creatures.clear();
	}
	
	public void setFrame(AquariumFrame frame)
	{
		this.frame = frame;
	}
	
	public void updateCreatureLocations()
	{
		for(Creature c : creatures)
		{
			c.updateLocation();
			
			BufferedImage img = c.getAppearance();
			int w = img.getWidth();
			int h = img.getHeight();
			// (x,y) are upper left coordinate here
			int x = c.getLocation().x - (w / 2);
			int y = c.getLocation().y - (h / 2);
			if(x <= 0)
			{
				c.hitLeftWall();
			}
			if(y <= 0)
			{
				c.hitSurface();
			}
			if(x >= WIDTH - w)
			{
				c.hitRightWall();
			}
			if(y >= HEIGHT - h)
			{
				c.hitFloor();
			}
		}
		
		if(frame != null)
		{
			frame.repaintCanvas();
		}
	}
	
	public void start()
	{
		updateTimer.start();
	}
	
	public void stop()
	{
		updateTimer.stop();
	}
}
