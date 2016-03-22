package aquarium.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import java.util.List;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import aquarium.Aquarium;
import aquarium.creatures.Creature;

public class ControllerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	private class CreatureViewModel
    {
        private Creature creature;
        
        public CreatureViewModel(Creature creature)
        {
            this.creature = creature;
        }
        
        public Creature getCreature()
        {
            return this.creature;
        }
        
        public String toString()
        {
            return creature.getName() + " (" + creature.getClass().getSimpleName() + ")";
        }
    }
    
    private Aquarium aquarium;
    private JList<Constructor<?>> constructorList;
    private JList<CreatureViewModel> creatureList;
    
    public ControllerPanel(Aquarium aquarium)
    {
        super(new BorderLayout());
        this.aquarium = aquarium;
        
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
                creatureList.setListData(createViewModelsForCreatures());
            }
        });
        buttonPanel.add(emptyButton);
        
        JButton fillWithCreaturesButton = new JButton("Fill with Creatures");
        fillWithCreaturesButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                aquarium.fillWithCreatures();
                creatureList.setListData(createViewModelsForCreatures());
            }
        });
        buttonPanel.add(fillWithCreaturesButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        JPanel addCreaturesPanel = new JPanel(new BorderLayout());
        constructorList = new JList<Constructor<?>>(findConstructibleCreatureClasses());
        addCreaturesPanel.add(constructorList, BorderLayout.CENTER);
        
        JPanel addButtonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Selected Creature");
        addButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                Constructor<?> c = constructorList.getSelectedValue();
                if (c == null)
                {
                    JOptionPane.showMessageDialog(ControllerPanel.this, "Select a Creature constructor from the list.",
                        "No Creature selected",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                createNewCreature(c);
                creatureList.setListData(createViewModelsForCreatures());
            }
        });
        addButtonPanel.add(addButton);
        addCreaturesPanel.add(addButtonPanel, BorderLayout.SOUTH);
        
        JPanel removeCreaturesPanel = new JPanel(new BorderLayout());
        creatureList = new JList<CreatureViewModel>(createViewModelsForCreatures());
        removeCreaturesPanel.add(creatureList, BorderLayout.CENTER);
        
        JPanel removeButtonPanel = new JPanel(new FlowLayout());
        JButton removeButton = new JButton("Remove Selected Creature");
        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                CreatureViewModel c = creatureList.getSelectedValue();
                if (c == null)
                {
                    JOptionPane.showMessageDialog(ControllerPanel.this, "Select a Creature to remove from the list.",
                        "No Creature selected",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                aquarium.removeCreature(c.getCreature());
                creatureList.setListData(createViewModelsForCreatures());
            }
        });
        removeButtonPanel.add(removeButton);
        removeCreaturesPanel.add(removeButtonPanel, BorderLayout.SOUTH);
        
        JPanel addRemoveCreaturesPanel = new JPanel(new GridLayout(2, 1));
        addRemoveCreaturesPanel.add(addCreaturesPanel);
        addRemoveCreaturesPanel.add(removeCreaturesPanel);
        
        add(addRemoveCreaturesPanel, BorderLayout.CENTER);
    }
    
    private void createNewCreature(Constructor<?> c)
    {
        String constructingMsg = "Constructing: " + c.toString();
        ArrayList<Object> parameters = new ArrayList<Object>();
        int numParam = 0;
        for (Class<?> paramCls : c.getParameterTypes())
        {
            if (!paramCls.isPrimitive() && !paramCls.getName().equals("java.lang.String"))
            {
                JOptionPane.showMessageDialog(this,
                    "This constructor is too complex. Modify Aquarium.fillWithCreatures instead to use it.",
                    "Constructor too complex",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean createdParamSuccessfully = false;
            
            while (!createdParamSuccessfully)
            {                            
                String paramVal = JOptionPane.showInputDialog(this,
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
                else if (paramCls.getName().equals("boolean"))
                {
                    String lowerCaseVal = paramVal.toLowerCase();
                    if (lowerCaseVal.equals("true") || lowerCaseVal.equals("false"))
                    {
                        Boolean b = Boolean.valueOf(paramVal);
                        parameters.add(b);
                        createdParamSuccessfully = true;                        
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid boolean. Type the word 'true' or 'false'.",
                            "Not a valid boolean",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (paramCls.getName().equals("byte"))
                {
                    try
                    {                        
                        Byte b = Byte.valueOf(paramVal);
                        parameters.add(b);
                        createdParamSuccessfully = true;
                    }
                    catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid byte. Please try again.",
                            "Not a valid byte",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (paramCls.getName().equals("char"))
                {
                    if (paramVal.length() != 1)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid char. Type exactly one character.",
                            "Not a valid char",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        Character ch = Character.valueOf(paramVal.charAt(0));
                        parameters.add(ch);
                        createdParamSuccessfully = true;                        
                    }
                }
                else if (paramCls.getName().equals("short"))
                {
                    try
                    {
                        Short s = Short.valueOf(paramVal);
                        parameters.add(s);
                        createdParamSuccessfully = true;
                    }
                    catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid short. Please try again.",
                            "Not a valid short",
                            JOptionPane.ERROR_MESSAGE);
                    }
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
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid integer. Please try again.",
                            "Not a valid integer",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (paramCls.getName().equals("long"))
                {
                    try
                    {
                        Long l = Long.valueOf(paramVal);
                        parameters.add(l);
                        createdParamSuccessfully = true;
                    }
                    catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid long. Please try again.",
                            "Not a valid long",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (paramCls.getName().equals("float"))
                {
                    try
                    {
                        Float f = Float.valueOf(paramVal);
                        parameters.add(f);
                        createdParamSuccessfully = true;
                    }
                    catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid float. Please try again.",
                            "Not a valid float",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if (paramCls.getName().equals("double"))
                {
                    try
                    {
                        Double d = Double.valueOf(paramVal);
                        parameters.add(d);
                        createdParamSuccessfully = true;
                    }
                    catch (NumberFormatException e)
                    {
                        JOptionPane.showMessageDialog(this,
                            "That wasn't a valid double. Please try again.",
                            "Not a valid double",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this,
                        "Unknown parameter type: " + paramCls.getName(),
                        "Unknown parameter type",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            if (createdParamSuccessfully)
            {
                numParam++;
            }
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
    
    private Vector<CreatureViewModel> createViewModelsForCreatures()
    {
        Vector<CreatureViewModel> vec = new Vector<CreatureViewModel>();
        List<Creature> creatures = aquarium.getCreatures();
        synchronized(creatures)
        {
            for (Creature c : aquarium.getCreatures())
            {
                vec.add(new CreatureViewModel(c));
            }
        }
        return vec;
    }
    
    private Vector<Constructor<?>> findConstructibleCreatureClasses()
    {
        Vector<Constructor<?>> constructors = new Vector<Constructor<?>>();
        for(String c : findClassesInCreaturesPackage())
        {
            try
            {
                Class<?> cl = Class.forName(c);
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
