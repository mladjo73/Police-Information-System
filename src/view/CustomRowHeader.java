package view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;

/**
 * Klasa za prikaz rednog broja reda u tabeli
 * 
 * @author grupa4
 */
public class CustomRowHeader extends JList<String> {
    
    private static final long serialVersionUID = 1L;

    public CustomRowHeader(JTable table) {
        super(createListModel(table));

        setFixedCellWidth(50);
        setFixedCellHeight(table.getRowHeight());
        setCellRenderer(new RowHeaderRenderer(table));

        // Add a table model listener to handle updates
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // Update row header when table data changes
                updateRowHeader(table);
            }
        });
    }

    private static AbstractListModel<String> createListModel(JTable table) {
        return new AbstractListModel<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public int getSize() {
                return table.getRowCount();
            }

            @Override
            public String getElementAt(int index) {
                return String.valueOf(index + 1);
            }
        };
    }

    public void updateRowHeader(JTable table) {
        // Update the model for the row header
        setModel(createListModel(table));
        revalidate(); // Ensure the row header layout is updated
        repaint();    // Ensure the row header is repainted
    }

    private static class RowHeaderRenderer extends JLabel implements ListCellRenderer<String> {
        private static final long serialVersionUID = 1L;

        RowHeaderRenderer(JTable table) {
            JTableHeader header = table.getTableHeader();
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(CENTER);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
}