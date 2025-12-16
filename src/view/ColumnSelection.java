package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Klasa predstavlja dijalog za odabir kolona koje će se prikazati, 
 * koristeći ResultSetMetaData za dohvaćanje naziva kolona i omogućavajući korisniku da označi koje kolone želi prikazati.
 * @author grupa4
 */
public class ColumnSelection extends JDialog {

    private static final long serialVersionUID = 1L;
    private List<JCheckBox> list = null;
    private JButton btnOk;
    private JButton btnCancel;

    public ColumnSelection(ResultSetMetaData rsmd, List<Boolean> boolList) {

        setTitle("Izbor kolona za prikaz");
        setSize(new Dimension(400, 600)); // Increased height to fit more checkboxes
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        list = new ArrayList<>();
        try {
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                list.add(new JCheckBox(rsmd.getColumnName(i), false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Checkbox panel with GridBagLayout for left alignment
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; // Align to the left
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.insets = new java.awt.Insets(5, 5, 5, 5); // Padding around checkboxes

        for (JCheckBox jCheckBox : list) {
            checkBoxPanel.add(jCheckBox, gbc);
            gbc.gridy++; // Move to the next row
        }

        add(checkBoxPanel);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.weightx = 1.0;

        btnOk = new JButton("OK");
        btnCancel = new JButton("Odustani");

        btnOk.addActionListener(e -> {
            for (int i = 0; i < list.size(); i++) {
                boolList.add(list.get(i).isSelected());
            }
            dispose();
        });

        btnCancel.addActionListener(e -> {
            dispose();
        });

        gbcButtons.insets = new java.awt.Insets(5, 5, 5, 5); 
        buttonPanel.add(btnOk, gbcButtons);

        gbcButtons.gridx = 1;
        buttonPanel.add(btnCancel, gbcButtons);

        add(buttonPanel);

        setModal(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
