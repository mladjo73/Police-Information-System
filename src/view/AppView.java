package view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import listeners.CustomListSelectionListener;
import model.ApplicationModel;
import model.ApplicationState;
import model.CustomTableModel;
import model.GeneralTableModel;
import model.IdleState;
import model.TreeElement;
/**
 * Klasa ApplicationView predstavlja prikaz aplikacije, povezujući model aplikacije sa glavnim prozorom i inicijalizujući login prozor.
 * @author grupa4
 */
public class AppView extends JFrame
{
	 private CustomRowHeader rowHeader;
	
	private static final long serialVersionUID = 1L;
	private ApplicationModel appModel;
	private Table mainTable;
	
	private FormPanel formPanel = null;
    private Toolbar toolbar = null;
    private Statusbar statusBar = null;
    private Menubar menuBar = null;
    private ApplicationState appState;
	
	public AppView(ApplicationModel appModel)
	{
		this.appModel=appModel;
	
		    setLayout(new BorderLayout());
		    
	        setTitle("Policija");
	         ImageIcon icon = new ImageIcon("resources/policija.jpg");
	        setIconImage(icon.getImage()); 
	        setSize(800, 600);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        
	        this.mainTable = new Table(new CustomTableModel());
	        this.mainTable.getSelectionModel().addListSelectionListener(new CustomListSelectionListener(this));
	       
	        this.mainTable.getSelectionModel().addListSelectionListener(event -> {
	            if (!event.getValueIsAdjusting()) {
	                updateStatusbarTableInfo();
	            }
	        });
	        
	    	rowHeader = new CustomRowHeader(mainTable);
	        JScrollPane scrollPane = new JScrollPane(mainTable);
	        scrollPane.setRowHeaderView(rowHeader);
	        JPanel tablePanel = new JPanel();
	        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
	        tablePanel.add(scrollPane);
	        add(tablePanel, BorderLayout.CENTER);
	        
	        formPanel= new FormPanel(new CustomTableModel(),this);
	        tablePanel.add(formPanel);
	        
	        menuBar = new Menubar();
	        setJMenuBar(menuBar);

	        toolbar = new Toolbar();
	        add(toolbar, BorderLayout.NORTH);


	        statusBar = new Statusbar();
	        add(statusBar, BorderLayout.SOUTH);
	        
	        BrowserPanel browserPanel = new BrowserPanel(appModel);
	        add(browserPanel, BorderLayout.WEST);
	        
	        setAppState(new IdleState(this));
	        setVisible(true);
	}
	
	public void setTableModel(GeneralTableModel tableModel) {
        mainTable.setModel(tableModel);
       formPanel.setTableModel(tableModel);
       // System.out.println(mainTable.getModel());
       rowHeader.updateRowHeader(mainTable); // Osveži redni brojevi
       rowHeader.revalidate();
       rowHeader.repaint();
       
    }

    
    
    public ApplicationState getAppState()
	{
		return appState;
	}

	public void setAppState(ApplicationState appState)
	{
		this.appState = appState;
	}
	
	public Table getMainTable()
	{
		return mainTable;
	}

	public void setMainTable(Table mainTable)
	{
		this.mainTable = mainTable;
	}
	public FormPanel getFormPanel()
	{
		return formPanel;
	}

	public Toolbar getToolbar()
	{
		return toolbar;
	}

	public Statusbar getStatusBar()
	{
		return statusBar;
	}
	
	public Menubar getMenuBarComponent()
	{
		return menuBar;
	}

	public void setFormPanel(FormPanel formPanel)
	{
		this.formPanel = formPanel;
	}

	public void setToolbar(Toolbar toolbar)
	{
		this.toolbar = toolbar;
	}

	public ApplicationModel getModel() {
		return appModel;
	}

	public void setStatusBar(Statusbar statusBar)
	{
		this.statusBar = statusBar;
	}
	
	public void updateStatusbarTableInfo() {
	    String tableName = "N/A";  // Podrazumevana vrednost ako tabela nije selektovana
	    if (mainTable.getModel() instanceof CustomTableModel) {
	        CustomTableModel model = (CustomTableModel) mainTable.getModel();
	        TreeElement.TableHelper tableHelper = model.getTable();
	        tableName = tableHelper != null ? tableHelper.getName() : "N/A";
	    }
	    int numberOfRows=mainTable.getRowCount();
	    int selectedRow = mainTable.getSelectedRow();
	    statusBar.updateTableInfo(tableName, selectedRow, numberOfRows);
	}
	
	 public CustomRowHeader getRowHeader() {
	        return rowHeader;
	    }
}

