package aquarium.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aquarium.Aquarium;
import aquarium.creatures.Creature;

public class ControllerPanel extends JPanel
{
    private Aquarium aquarium;
    private JList<Constructor> creatureList;
    private Vector<Constructor> creatureConstructors;
    
    public ControllerPanel(Aquarium aquarium)
    {
        super(new BorderLayout());
        this.aquarium = aquarium;
        
        this.creatureConstructors = findConstructibleCreatureClasses();
        initializeUI();
    }
    
    private String createStartStopButtonText()
    {
        if (aquarium.isRunning())
        {
            return "Stop";
        }
        
        return "Start";
    }
    
    private void initializeUI()
    {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton startStopButton = new JButton(createStartStopButtonText());
        startStopButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                if (aquarium.isRunning())
                {
                    aquarium.stop();
                }
                else
                {
                    aquarium.start();
                }
                
                startStopButton.setText(createStartStopButtonText());
            }
        });
        buttonPanel.add(startStopButton);
        
        JButton emptyButton = new JButton("Empty");
        emptyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aquarium.empty();
                
                startStopButton.setText(createStartStopButtonText());
            }
        });
        buttonPanel.add(emptyButton);
        
        JButton fillWithCreaturesButton = new JButton("Fill with Creatures");
        fillWithCreaturesButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aquarium.fillWithCreatures();
                
                startStopButton.setText(createStartStopButtonText());
            }
        });
        buttonPanel.add(fillWithCreaturesButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        creatureList = new JList<Constructor>(creatureConstructors);
        add(creatureList, BorderLayout.CENTER);
        
        JPanel addPanel = new JPanel(new FlowLayout());
        
        JButton addButton = new JButton("Add Selected Creature");
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                Constructor c = creatureList.getSelectedValue();
                if (c == null)
                {
                    JOptionPane.showMessageDialog(ControllerPanel.this, "Select a Creature constructor from the list.",
                        "No Creature selected",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String constructingMsg = "Constructing: " + c.toString();
                ArrayList<Object> parameters = new ArrayList<Object>();
                int numParam = 0;
                for (Class<?> paramCls : c.getParameterTypes())
                {
                    if (!paramCls.isPrimitive() && !paramCls.getName().equals("java.lang.String"))
                    {
                        JOptionPane.showMessageDialog(ControllerPanel.this,
                            "This constructor is too complex. Modify Aquarium.fillWithCreatures instead to use it.",
                            "Constructor too complex",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    boolean createdParamSuccessfully = false;
                    
                    while (!createdParamSuccessfully)
                    {                            
                        String paramVal = JOptionPane.showInputDialog(ControllerPanel.this,
                            constructingMsg + "\n\n" + "Input value for parameter " + numParam + " (" + paramCls.getSimpleName() + ")");
                        if (paramVal == null)
                        {
                            // User clicked cancel
                            return;
                        }
                        
                        if (paramCls.getName().equals("java.lang.String"))
                        {
                            parameters.add(paramVal);
                            createdParamSuccessfully = true;
                        }
                        else if (paramCls.getName().equals("int"))
                        {
                            try
                            {
                                Integer i = Integer.valueOf(paramVal);
                                parameters.add(i);
                                createdParamSuccessfully = true;
                            }
                            catch (NumberFormatException e)
                            {
                                JOptionPane.showMessageDialog(ControllerPanel.this,
                                    "That wasn't a valid integer. Please try again.",
                                    "Not a valid integer",
                                    JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                        }
                    }
                    
                    numParam++;
                }
                
                try
                {
                    Creature newCreature = (Creature)c.newInstance(parameters.toArray());
                    aquarium.addCreature(newCreature);
                }
                catch (InstantiationException|IllegalAccessException|InvocationTargetException e)
                {
                    e.printStackTrace();
                }
                
            }
        });
        addPanel.add(addButton);
        
        add(addPanel, BorderLayout.SOUTH);
    }
    
    private Vector<Constructor> findConstructibleCreatureClasses()
    {
        Vector<Constructor> constructors = new Vector<Constructor>();
        for(String c : findClassesInCreaturesPackage())
        {
            try
            {
                Class cl = Class.forName(c);
                if (Modifier.isAbstract(cl.getModifiers()))
                {
                    continue;
                }
                for (Constructor<?> co : cl.getConstructors())
                {
                     constructors.add(co);
                }
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        
        return constructors;
    }
    
    /**
     * Adapted from http://stackoverflow.com/questions/1810614/getting-all-classes-from-a-package.
     */
    private Vector<String> findClassesInCreaturesPackage()
    {
        URL root = Thread.currentThread().getContextClassLoader().getResource("aquarium/creatures");
        Vector<String> classes = new Vector<String>();
        
        try
        {
            File[] files = new File(URLDecoder.decode(root.getFile(), "UTF-8")).listFiles(new FilenameFilter()
            {
                public boolean accept(File dir, String name)
                {
                    return name.endsWith(".class");
                }
            });
            
            for (File file : files)
            {
                classes.add("aquarium.creatures." + file.getName().replaceAll(".class$", ""));
            }
            
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        return classes;
    }
}
