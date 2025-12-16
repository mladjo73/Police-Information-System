package view;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import listeners.CustomTreeSelectionListener;
import model.ApplicationModel;
/**
 * Klasa BrowserPanel predstavlja panel sa bočno postavljenim stablom (JTree) 
 * koje omogućava pregled i odabir elemenata hijerarhijske strukture aplikacije, prilagođeno renderovanjem i upravljanjem događajima.
 * @author grupa4
 */
public class BrowserPanel extends JPanel {
 
	private static final long serialVersionUID = 1L;
	private JTree tree = null;
	private AppView appView = null;

    public BrowserPanel(ApplicationModel model) {
        // TODO Auto-generated constructor stub
       setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));  //poravnanje u box-u
    	
        tree = new JTree(model.getTreeModel());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
       
       
        tree.setCellRenderer(new CustomTreeCellRenderer());
        CustomTreeSelectionListener customTreeSelectionListener = new CustomTreeSelectionListener(appView);
        tree.addTreeSelectionListener(customTreeSelectionListener);
        tree.addMouseListener(customTreeSelectionListener); 

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane); 
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        
      /*  Border blueBorder = BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLUE);
        setBorder(BorderFactory.createCompoundBorder(blueBorder, getBorder()));

       
        setBackground(Color.decode("#FFFFFF"));
        tree.setBackground(Color.decode("#FFFFFF")); */
    }
}
