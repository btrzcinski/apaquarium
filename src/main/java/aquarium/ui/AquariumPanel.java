package aquarium.ui;

import javax.swing.JPanel;

import aquarium.Aquarium;
import aquarium.creatures.Creature;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class AquariumPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private static Color SKY_COLOR = new Color(108, 199, 248);
    private static Color WATER_COLOR = new Color(34, 46, 214);
    
    private Aquarium aquarium;
    
    public AquariumPanel(Aquarium aquarium)
    {
        super();
        this.aquarium = aquarium;
    }
    
    public Dimension getPreferredSize()
    {
        return new Dimension(Aquarium.WIDTH, Aquarium.HEIGHT + Aquarium.SKY_HEIGHT);
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        // Background
        g.setColor(SKY_COLOR);
        g.fillRect(0, 0, Aquarium.WIDTH, Aquarium.SKY_HEIGHT);
        g.setColor(WATER_COLOR);
        g.fillRect(0, Aquarium.SKY_HEIGHT, Aquarium.WIDTH, Aquarium.HEIGHT);
        
        List<Creature> creatures = aquarium.getCreatures();
        synchronized (creatures)
        {
            for(Creature c : creatures)
            {
                BufferedImage img = c.getAppearance();
                int w = img.getWidth();
                int h = img.getHeight();
                // Center the image on the Creature's (x,y)
                int x = c.getLocation().x - (w / 2);
                int y = c.getLocation().y - (h / 2) + Aquarium.SKY_HEIGHT;
                
                g.drawImage(img, x, y, null);
            }
        }
    }

}
