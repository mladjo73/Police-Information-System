package view;

import java.awt.Component;
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.CustomTableModel;
import model.GeneralTableModel;

public class Table extends JTable {

	private static final long serialVersionUID = 1L;
	
	private CustomTableModel tableModel;
	
	public Table(CustomTableModel tableModel)
	{
		this.tableModel=tableModel;
		
		setRowHeight(30);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setReorderingAllowed(false);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	
	public CustomTableModel getTableModel() {
		return tableModel;
	}
	public void setSelectionListener(ListSelectionListener listener) {
		getSelectionModel().addListSelectionListener(listener);
	}
	public void setTableModel(GeneralTableModel tableModel) {
	    setModel(tableModel);
	    if (tableModel.getColumns() != null) {
	        TableColumnModel columnModel = getColumnModel();
	        for (int i = 0; i < columnModel.getColumnCount(); i++) {
	            TableColumn tableColumn = columnModel.getColumn(i);
	            int preferredWidth = tableColumn.getMinWidth();
	            int maxWidth = tableColumn.getMaxWidth(); 

	            for (int row = 0; row < getRowCount(); row++) {
	                TableCellRenderer cellRenderer = getCellRenderer(row, i);
	                Component comp = prepareRenderer(cellRenderer, row, i);

	                String cellText = ((JLabel) comp).getText().trim();
	                comp = new JLabel(cellText);
	                int width = comp.getPreferredSize().width + getIntercellSpacing().width;
	                preferredWidth = Math.max(preferredWidth, width);

	                if (preferredWidth >= maxWidth) {
	                    preferredWidth = maxWidth;
	                    break;
	                }
	            }
	            tableColumn.setPreferredWidth(preferredWidth);
	        }
	    }
	}


	public void setTableModel() {
		setModel(tableModel);
	}
	

}
