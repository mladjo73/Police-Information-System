package listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
import view.Table;

/**
 * Klasa koja implementira ActionListener za dugmad u alatnoj traci(toolbar).
 * @author grupa4
 * 
 *
 */
public class ToolbarButtonsActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		AppView appView = (AppView) SwingUtilities.getWindowAncestor((JButton)e.getSource());
		switch(e.getActionCommand()) {
		case "new":
			appView.getAppState().handleCreate();
			break;
		case "edit":
			appView.setAppState(new ModifyState(appView));
			appView.getAppState().handleChange();
			break;
		case "next":
			appView.getAppState().handleNext();
			break;
		case "prev":
			appView.getAppState().handlePrev();
			break;
		case "last":
			appView.getAppState().handleLast();
			break;
		case "first":
			appView.getAppState().handleFirst();
			break;
		case "accept":
			appView.getAppState().handleSubmit();
			break;
		case "cancel":
			appView.getAppState().handleCancel();
			break;
		case "delete":
			appView.getAppState().handleDelete();
			break;
		case "report":
		    Table table = appView.getMainTable();
		    GeneralTableModel tableModel = (GeneralTableModel) appView.getMainTable().getModel();

		    if (tableModel instanceof CustomTableModel) {
		        CustomTableModel customTableModel = (CustomTableModel) tableModel;

		        FastReportBuilder drb = new FastReportBuilder();
		        try {
		            // Initialize procUtil and retrieve metadata
		            StoredProceduresUtil procUtil = new StoredProceduresUtil(customTableModel.getTable());
		            
		            ResultSetMetaData rsmd = procUtil.getResultSetMetaData(customTableModel.getTable().getRetrieveSProc());  // Assuming procUtil has a method to get ResultSetMetaData
		            
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
		            StoredProceduresUtil procUtil1 = new StoredProceduresUtil(customTableModel.getTable());
		            JRDataSource jrDataSource = new JRResultSetDataSource(procUtil.read());

		            JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dynamicReport, new ClassicLayoutManager(), jrDataSource);
		            JasperViewer viewer = new JasperViewer(jasperPrint, false);
		            viewer.setVisible(true);
		        } catch (ColumnBuilderException | ClassNotFoundException | JRException e1) {
		            e1.printStackTrace();
		        }
		        break;
		    }
		}

		    
		

		
	}

}
