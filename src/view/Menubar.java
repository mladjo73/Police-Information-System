package view;

import javax.swing.*;

import listeners.LogoutListener;
import listeners.MenuEditActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Menubar extends JMenuBar {
   
	public JMenu editMenu;
    public JMenuItem firstItem;
    public JMenuItem nextItem;
    public JMenuItem previousItem;
    public JMenuItem lastItem;

    public JMenuItem newItem;
    public JMenuItem editItem;
    public JMenuItem deleteItem;

    public JMenuItem acceptItem;
    public JMenuItem cancelItem;

    public JMenuItem reportItem;
    
	private static final long serialVersionUID = 1L;

	public Menubar() {
        // File menu
		JMenu fileMenu = new JMenu("File");

        // Load and scale icons for File menu
        ImageIcon switchXMLIcon = scaleIcon(new ImageIcon(getClass().getResource("/sxml.png")), 16, 16);
        ImageIcon logoutIcon = scaleIcon(new ImageIcon(getClass().getResource("/logout.png")), 16, 16);
        ImageIcon exitIcon = scaleIcon(new ImageIcon(getClass().getResource("/exit.png")), 16, 16);
       
        
         JMenuItem switchXMLItem = new JMenuItem("Switch XML", switchXMLIcon);
         JMenuItem logOutItem = new JMenuItem("Log Out", logoutIcon);
         JMenuItem exitItem = new JMenuItem("Exit XML", exitIcon);
         
        logOutItem.addActionListener(new LogoutListener());
         
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(switchXMLItem);
        fileMenu.addSeparator();
        fileMenu.add(logOutItem);
        fileMenu.add(exitItem);

        // Edit menu
        editMenu = new JMenu("Edit");

        // Load and scale icons for navigation commands
        ImageIcon firstIcon = scaleIcon(new ImageIcon(getClass().getResource("/first.png")), 16, 16);
        ImageIcon nextIcon = scaleIcon(new ImageIcon(getClass().getResource("/next.png")), 16, 16);
        ImageIcon previousIcon = scaleIcon(new ImageIcon(getClass().getResource("/previous.png")), 16, 16);
        ImageIcon lastIcon = scaleIcon(new ImageIcon(getClass().getResource("/last.png")), 16, 16);

        // Navigation commands
         firstItem = new JMenuItem("First", firstIcon);
         nextItem = new JMenuItem("Next", nextIcon);
         previousItem = new JMenuItem("Previous", previousIcon);
         lastItem = new JMenuItem("Last", lastIcon);

        // Load and scale icons for state change commands
        ImageIcon newIcon = scaleIcon(new ImageIcon(getClass().getResource("/new.png")), 16, 16);
        ImageIcon editIcon = scaleIcon(new ImageIcon(getClass().getResource("/edit.png")), 16, 16);
        ImageIcon deleteIcon = scaleIcon(new ImageIcon(getClass().getResource("/delete.png")), 16, 16);

        // State change commands
         newItem = new JMenuItem("New", newIcon);
         editItem = new JMenuItem("Edit", editIcon);
         deleteItem = new JMenuItem("Delete", deleteIcon);

        // Load and scale icons for accept/cancel commands
        ImageIcon acceptIcon = scaleIcon(new ImageIcon(getClass().getResource("/accept.png")), 16, 16);
        ImageIcon cancelIcon = scaleIcon(new ImageIcon(getClass().getResource("/cancel.png")), 16, 16);

        // Accept/Cancel commands
        acceptItem = new JMenuItem("Accept", acceptIcon);
        cancelItem = new JMenuItem("Cancel", cancelIcon);

        // Load and scale icon for Report command
        ImageIcon reportIcon = scaleIcon(new ImageIcon(getClass().getResource("/report.png")), 16, 16);

        // Report command
        reportItem = new JMenuItem("Report", reportIcon);

        // Add items to Edit menu
        editMenu.add(firstItem);
        editMenu.add(nextItem);
        editMenu.add(previousItem);
        editMenu.add(lastItem);
        editMenu.addSeparator();
        editMenu.add(newItem);
        editMenu.add(editItem);
        editMenu.add(deleteItem);
        editMenu.addSeparator();
        editMenu.add(acceptItem);
        editMenu.add(cancelItem);
        editMenu.addSeparator();
        editMenu.add(reportItem);

       
        // Help menu
        JMenu helpMenu = new JMenu("Help");

        ImageIcon aboutItemIcon = scaleIcon(new ImageIcon(getClass().getResource("/info.png")), 16, 16);
        JMenuItem aboutItem = new JMenuItem("About",aboutItemIcon);
        helpMenu.add(aboutItem);
        // Add menus to the menu bar
        this.add(fileMenu);
        this.add(editMenu);
        this.add(helpMenu);
        
        
        MenuEditActionListener menuEditActionListener = new MenuEditActionListener();
        
        firstItem.setActionCommand("first");
        nextItem.setActionCommand("next");
        previousItem.setActionCommand("prev");
        lastItem.setActionCommand("last");
        newItem.setActionCommand("new");
        editItem.setActionCommand("edit");
        deleteItem.setActionCommand("delete");
        acceptItem.setActionCommand("accept");
        cancelItem.setActionCommand("cancel");
        reportItem.setActionCommand("report");
        aboutItem.setActionCommand("about");
        switchXMLItem.setActionCommand("switch");

        firstItem.addActionListener(menuEditActionListener);
        nextItem.addActionListener(menuEditActionListener);
        previousItem.addActionListener(menuEditActionListener);
        lastItem.addActionListener(menuEditActionListener);
        newItem.addActionListener(menuEditActionListener);
        editItem.addActionListener(menuEditActionListener);
        deleteItem.addActionListener(menuEditActionListener);
        acceptItem.addActionListener(menuEditActionListener);
        cancelItem.addActionListener(menuEditActionListener);
        reportItem.addActionListener(menuEditActionListener);
        aboutItem.addActionListener(menuEditActionListener);
        switchXMLItem.addActionListener(menuEditActionListener);
        
        switchXMLItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        logOutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        
        firstItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        nextItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        previousItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        lastItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        acceptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        cancelItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        reportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        
    }

    // Helper method to scale icons
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }
}
