package aquarium.ui;

import java.awt.Dimension;
import javax.swing.JFrame;

import aquarium.Aquarium;

public class ControllerFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private ControllerPanel controllerPanel;
    
    public ControllerFrame(Aquarium aquarium)
    {
        super("Simulation");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 450));
        setResizable(false);
        
        this.controllerPanel = new ControllerPanel(aquarium);
        add(controllerPanel);
        pack();
    }
}
