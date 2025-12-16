package view;

import javax.swing.*;

import listeners.ToolbarButtonsActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnFirst, btnNext, btnPrevious, btnLast;
    private JButton btnNew, btnEdit, btnDelete;
    private JButton btnAccept, btnCancel;
    private JButton btnReport;
    
	public Toolbar() {
        setFloatable(false);
        
        // Navigacioni dugmići
        btnFirst = createButton("/first.png", "First","first");
        btnNext = createButton("/next.png", "Next","next");
        btnPrevious = createButton("/previous.png", "Previous","prev");
        btnLast = createButton("/last.png", "Last","last");

        // Komande za promenu stanja
        btnNew = createButton("/new.png", "New","new");
        btnEdit = createButton("/edit.png", "Edit","edit");
        btnDelete = createButton("/delete.png", "Delete","delete");

        // Potvrda unosa/promene
        btnAccept = createButton("/accept.png", "Accept","accept");
        btnCancel = createButton("/cancel.png", "Cancel","cancel");

        // Generisanje izveštaja
        btnReport = createButton("/report.png", "Report","report");

        
        
     // Dodavanje dugmića u toolbar
        add(btnFirst);
        add(btnNext);
        add(btnPrevious);
        add(btnLast);
        addSeparator();
        add(btnNew);
        add(btnEdit);
        add(btnDelete);
        addSeparator();
        add(btnAccept);
        add(btnCancel);
        addSeparator();
        add(btnReport);
  
        
        
     // Dodavanje listenera dugmićima
        ToolbarButtonsActionListener actionListener = new ToolbarButtonsActionListener();
        btnFirst.addActionListener(actionListener);
        btnNext.addActionListener(actionListener);
        btnPrevious.addActionListener(actionListener);
        btnLast.addActionListener(actionListener);
        btnNew.addActionListener(actionListener);
        btnEdit.addActionListener(actionListener);
        btnDelete.addActionListener(actionListener);
        btnAccept.addActionListener(actionListener);
        btnCancel.addActionListener(actionListener);
        btnReport.addActionListener(actionListener);
        
        setButtonsEnabled(true);
        
        
    }

    private JButton createButton(String imagePath, String tooltip,String actionCommand) {
        JButton button = new JButton();
        button.setToolTipText(tooltip);

        // Učitaj sliku
        ImageIcon icon = new ImageIcon(getClass().getResource("" + imagePath));
        Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(image));
        
        button.setActionCommand(actionCommand);

        // Postavi dimenzije dugmeta
        Dimension size = new Dimension(40, 40);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setMinimumSize(size);

        return button;
    }
    
    public void setButtonsEnabled(boolean enabled) {
        btnFirst.setEnabled(enabled);
        btnNext.setEnabled(enabled);
        btnPrevious.setEnabled(enabled);
        btnLast.setEnabled(enabled);
        btnNew.setEnabled(enabled);
        btnEdit.setEnabled(enabled);
        btnDelete.setEnabled(enabled);
        btnAccept.setEnabled(enabled);
        btnCancel.setEnabled(enabled);
        btnReport.setEnabled(enabled);
    }
    
    public void setButtonsInModifyState() { 
   	 btnFirst.setEnabled(true);
        btnLast.setEnabled(true);
        btnNext.setEnabled(true);
        btnPrevious.setEnabled(true);
        btnNew.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(true);
        btnAccept.setEnabled(true);
        btnCancel.setEnabled(true);
        btnReport.setEnabled(true);
   }
    public void setButtonsInActiveState() {
        btnFirst.setEnabled(false);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
        btnLast.setEnabled(false);
        btnNew.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnAccept.setEnabled(false);
        btnCancel.setEnabled(false);
        btnReport.setEnabled(true);
    }
    public void setButtonsInCreationState() {
        btnFirst.setEnabled(false);
        btnLast.setEnabled(false);
        btnNext.setEnabled(false);
        btnPrevious.setEnabled(false);
        btnNew.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnAccept.setEnabled(true);
        btnCancel.setEnabled(true);
        btnReport.setEnabled(false);
    }
    
  /*  @Override
    public void update(Subject subject) {
        // TODO Auto-generated method stub
        
    } */
}
