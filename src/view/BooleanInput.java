package view;

import java.awt.Component;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import model.TreeElement.Column;
/**
 * Klasa BooleanInput predstavlja grafički komponentu koja omogućava unos
 *  i prikazivanje boolean vrijednosti pomoću radio dugmadi za opcije "Da" i "Ne", vezana za određenu kolonu modela podataka.
 *  @author grupa4
 */
public class BooleanInput extends JPanel implements InputField {

    private static final long serialVersionUID = 1L;

    private final JRadioButton btnTrue = new JRadioButton("Da");
    private final JRadioButton btnFalse = new JRadioButton("Ne");
    private final ButtonGroup btnGroup = new ButtonGroup();

    private final Column column;
    private Boolean value;

    public BooleanInput(Column column) {
       
        this.column = column;

        TitledBorder border = new TitledBorder(column.getName());
        setBorder(border);

        btnGroup.add(btnTrue);
        btnGroup.add(btnFalse);
        
        btnTrue.addActionListener((e) -> setValue(true));
		btnFalse.addActionListener((e) -> setValue(false));

        add(btnTrue);
        add(btnFalse);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object object) {
        value = (Boolean) object;
        SwingUtilities.invokeLater(() -> handleValueChanged());
    }

    private void handleValueChanged() {
        if (value != null) {
            if (value) {
                btnTrue.setSelected(true);
            } else {
                btnFalse.setSelected(true);
            }
        } else {
            btnGroup.clearSelection();
        }
    }

    @Override
    public void setReferenceBtn(Component btn) {
        // Implement as needed
    }

    @Override
    public void setEnabled(boolean enabled) {
        btnTrue.setEnabled(enabled);
        btnFalse.setEnabled(enabled);
    }

    @Override
    public boolean isPrimary() {
        return false;
    }

    @Override
    public boolean isNullable() {
        return column.isNullable();
    }

    @Override
    public String getName() {
        return column.getName();
    }
}

