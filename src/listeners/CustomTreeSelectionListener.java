package listeners;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import model.ActiveState;
import model.CustomTableModel;
import model.TreeElement;
import view.AppView;

/**
 * Klasa koja implementira izbor opcije iz application browsera koji je realizovan kao JTree.
 * @author grupa4
 * 
 *
 */
public class CustomTreeSelectionListener extends MouseAdapter implements TreeSelectionListener {
    private AppView appView;  // Dodajte referencu na AppView
    private JTree tree = null;

    public CustomTreeSelectionListener(AppView appView) {
        this.appView = appView;  // Inicijalizujte referencu
    }
    public CustomTreeSelectionListener(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        tree = (JTree) e.getSource();
        updateStatusBar();  // Ažurirajte status bar
    }

    private void updateStatusBar() {
        if (tree != null) {
            TreePath selectedPath = tree.getSelectionPath();
            if (selectedPath != null && selectedPath.getLastPathComponent() instanceof TreeElement.TableHelper) {
                TreeElement.TableHelper table = (TreeElement.TableHelper) selectedPath.getLastPathComponent();
                try {
                appView.updateStatusbarTableInfo();
                }catch (Exception e) {
					// TODO: handle exception
				}
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof JTree) {
            if (e.getClickCount() == 2) {
                AppView appView = (AppView)SwingUtilities.getWindowAncestor((Component)e.getSource());
                if (tree != null) {
                    int row = tree.getRowForLocation(e.getX(), e.getY());
                    
                    if (row == -1) {
                        tree.clearSelection();
                    } else {
                        TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                        tree.setSelectionPath(selPath);
                        if (selPath.getLastPathComponent() instanceof TreeElement.TableHelper) {
                            TreeElement.TableHelper table = (TreeElement.TableHelper) selPath.getLastPathComponent();
                            CustomTableModel tableModel = new CustomTableModel(table);
                            appView.setTableModel(tableModel);
                            appView.setAppState(new ActiveState(appView, table.getName()));
                            
                            // Ovdje ažuriramo status bar sa novom tabelom
                            appView.updateStatusbarTableInfo();
                        }
                    }
                }
            }
        }
    }
}