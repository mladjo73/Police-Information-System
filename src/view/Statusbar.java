package view;

import javax.swing.*;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Statusbar extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel tableLabel;
    private JLabel rowLabel;
    private JLabel timeLabel;
    private JLabel lblState;

    public Statusbar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(getWidth(), 30));  // Visina status bara

        tableLabel = new JLabel("Table");
        rowLabel = new JLabel("Row");
        timeLabel = new JLabel();
        lblState=new JLabel();

        updateTime();

        add(tableLabel);
        add(Box.createHorizontalStrut(20));  // Razmak između labela
        add(rowLabel);
        add(Box.createHorizontalStrut(20));
        add(lblState);
        add(Box.createHorizontalGlue());
        add(timeLabel);

        // Pokreni tajmer za ažuriranje vremena
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        String time = sdf.format(new Date());
        timeLabel.setText("Date and time: " + time);
    }

    public void updateTableInfo(String tableName, int rowIndex,int numberOfRows) {
        tableLabel.setText(tableName);
        rowLabel.setText("Row selected: " + (rowIndex >= 0 ? rowIndex + 1 : "0") +"/"+ numberOfRows);
    }

    public void updateTableName(String tableName) {
        tableLabel.setText("Table: " + tableName);
    }

    public void updateRowNumber(int rowIndex) {
        rowLabel.setText("Row selected: " + (rowIndex >= 0 ? rowIndex + 1 : "N/A"));
    }
    
    public void setStatusMessage(String message) {
        lblState.setText(message);
    }
    
    
    public void setRowMessage(String s) {
        rowLabel.setText(s);
    }

}
