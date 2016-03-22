package aquarium.ui;

import javax.swing.JFrame;

import aquarium.Aquarium;

public class AquariumFrame extends JFrame
{
	private AquariumPanel aquariumPanel;

	public AquariumFrame(Aquarium aquarium, int width, int height)
	{
		super("AP Aquarium");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(false);

		this.aquariumPanel = new AquariumPanel(aquarium);
		add(aquariumPanel);
		pack();
	}
	
	public void repaintCanvas()
	{
		aquariumPanel.repaint();
	}
}
