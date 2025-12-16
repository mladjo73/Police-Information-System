package listeners;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner.Alignment;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import controller.LoginController;
import databaseAccess.DbConnection;
import model.CustomTableModel;
import model.GeneralTableModel;
import model.ModifyState;
import model.StoredProceduresUtil;
import model.TreeElement.Column;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import view.AppView;
import view.ColumnSelection;
import view.LoginView;
import view.Table;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/**
 * MenuEditActionListener je klasa koja služi kao slušatelj(listener) za akcije koje se događaju u Edit meniju.
 * @author grupa4
 * 
 *
 */

public class MenuEditActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        JPopupMenu menuItem = (JPopupMenu) ((JMenuItem) e.getSource()).getParent();
        AppView window = (AppView) SwingUtilities.getWindowAncestor((JMenuBar) ((JMenuItem) menuItem.getInvoker()).getParent());

        switch (e.getActionCommand()) {
            case "new":
                window.getAppState().handleCreate();
                break;
            case "edit":
                window.setAppState(new ModifyState(window));
                window.getAppState().handleChange();
                break;
            case "next":
                window.getAppState().handleNext();
                break;
            case "prev":
                window.getAppState().handlePrev();
                break;
            case "last":
                window.getAppState().handleLast();
                break;
            case "delete":
    			window.getAppState().handleDelete();
    			break;
            case "first":
                window.getAppState().handleFirst();
                break;
            case "accept":
                window.getAppState().handleSubmit();
                break;
            case "cancel":
                window.getAppState().handleCancel();
                break;
            case "report":
                try {
                    GeneralTableModel tableModel = (GeneralTableModel) window.getMainTable().getModel();
                    
                    if (tableModel instanceof CustomTableModel) {
                        CustomTableModel customTableModel = (CustomTableModel) tableModel;
                        FastReportBuilder drb = new FastReportBuilder();

                        try {
                            // Initialize procUtil and retrieve metadata
                            StoredProceduresUtil procUtil = new StoredProceduresUtil(customTableModel.getTable());
                            ResultSetMetaData rsmd = procUtil.getResultSetMetaData(customTableModel.getTable().getRetrieveSProc());
                            System.out.println("XXXXXXX---------->" + rsmd.toString());

                            // Get the column selection based on user input or some criteria
                            List<Boolean> showCols = new ArrayList<>();
                            new view.ColumnSelection(rsmd, showCols);

                            // Build the report using only the selected columns
                            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                                Column col = (Column) tableModel.getColumn(i);
                                if (showCols.get(i)) {
                                    drb.addColumn(col.getName(), col.getName(), String.class.getName(), 30);
                                }
                            }

                            DynamicReportBuilder drpb = drb
                                .setTitle(customTableModel.getTable().getName())
                                .setOddRowBackgroundStyle(new StyleBuilder(false).setBackgroundColor(new Color(167, 199, 231)).build())
                                .setTitleStyle(new StyleBuilder(false).setFont(new ar.com.fdvs.dj.domain.constants.Font(21, "Georgia", true)).build())
                                .setPrintBackgroundOnOddRows(true)
                                .setUseFullPageWidth(true)
                                .addFirstPageImageBanner("resources/policija.jpg", 200, 120, Alignment.Center)
                                .setHeaderVariablesHeight(30)
                                .addAutoText("Izvjestaj kreiran: " + new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date()), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 200, new StyleBuilder(true).build())
                                .setFooterVariablesHeight(20)
                                .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT, 30, 20);

                            DynamicReport dynamicReport = drpb.build();
                            JRDataSource jrDataSource = new JRResultSetDataSource(procUtil.read());

                            JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), jrDataSource);
                            JasperViewer viewer = new JasperViewer(jasperPrint, false);
                            viewer.setVisible(true);
                        } catch (ColumnBuilderException | ClassNotFoundException | JRException e1) {
                            e1.printStackTrace();
                        }
                    }
                } catch (ClassCastException e9) {
                    // Handle the outer exception and call ComplexReport
                	try {
                	    // Pozivanje ComplexReport procedure
                	    DbConnection instance = DbConnection.GetInstance();
                	    Connection conn = instance.getConn();
                	    CallableStatement callableStatement = conn.prepareCall("{call ComplexReport()}");

                	    ResultSet resultSet = callableStatement.executeQuery();
                	    ResultSetMetaData rsmd = resultSet.getMetaData();

                	    // Prikaz dijaloga za izbor kolona
                	    List<Boolean> showCols = new ArrayList<>();
                	    new view.ColumnSelection(rsmd, showCols);

                	    FastReportBuilder drb = new FastReportBuilder();

                	    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                	        if (showCols.get(i - 1)) {
                	            drb.addColumn(rsmd.getColumnName(i), rsmd.getColumnName(i), String.class.getName(), 30);
                	        }
                	    }

                	    DynamicReportBuilder drpb = drb
                	        .setTitle("Kompleksni izvještaj")
                	        .setOddRowBackgroundStyle(new StyleBuilder(false).setBackgroundColor(new Color(252, 191, 80)).build())
                	        .setTitleStyle(new StyleBuilder(false).setFont(new ar.com.fdvs.dj.domain.constants.Font(23, "Georgia", true)).build())
                	        .setPrintBackgroundOnOddRows(true)
                	        .setUseFullPageWidth(true)
                	        .addFirstPageImageBanner("resources/policija.jpg", 200, 150, Alignment.Left)
                	        .setHeaderVariablesHeight(20)
                	        .addAutoText("Kreirano dana: " + new java.text.SimpleDateFormat("dd.MM.yyyy. HH:mm")
                	                .format(new java.util.Date()), AutoText.POSITION_HEADER, AutoText.ALIGNMENT_RIGHT, 250, new StyleBuilder(true).build())
                	        .setFooterVariablesHeight(20)
                	        .addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER, 30, 20);

                	    DynamicReport dynamicReport = drpb.build();
                	    JRDataSource jrDataSource = new JRResultSetDataSource(resultSet);

                	    JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), jrDataSource);
                	    JasperViewer viewer = new JasperViewer(jasperPrint, false);
                	    viewer.setVisible(true);
                	} catch (Exception e2) {
                	    e2.printStackTrace();
                	}

                }
                break;

            case "about":
            	if (Desktop.isDesktopSupported()) {
    			    Desktop desktop = Desktop.getDesktop();
    			    File htmlFile = new File("doc/about.html");
    			    try {
    			        desktop.browse(htmlFile.toURI());
    			    } catch (IOException er) {
    			        er.printStackTrace();
    			    }
    			}
            	break;
            case "switch":
			  	JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("XML Fajlovi", "xml"));
		        int returnValue = fileChooser.showOpenDialog(null);

		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		            File selectedFile = fileChooser.getSelectedFile();
		            System.out.println("fajl: " +  selectedFile.toString());
		            LoginView loginViewInstance = new LoginView(selectedFile.toString());
		            new LoginController(loginViewInstance, null);

		            LoginController.startLoginProcess(selectedFile.toString());
		            window.dispose();
		        }
		        break;
               
    		
    		}
    	}
   }
