package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.LoginController;
import model.ApplicationModel;
import view.LoginView;

public class MainClass {
	 public static void main(String[] args) {
		 
		 
		 //LookAndFeel
		 /* try {
	         // Set System L&F
	     UIManager.setLookAndFeel(
	         UIManager.getSystemLookAndFeelClassName());
	 } 
	 catch (UnsupportedLookAndFeelException e) {
	    // handle exception
	 }
	 catch (ClassNotFoundException e) {
	    // handle exception
	 }
	 catch (InstantiationException e) {
	    // handle exception
	 }
	 catch (IllegalAccessException e) {
	    // handle exception
	 }
		 */
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                LoginView loginView = new LoginView("xml/sema.xml");
	                ApplicationModel appmodel = new ApplicationModel("xml/sema.xml");
	                new LoginController(loginView, appmodel);
	                loginView.setVisible(true);
	            }
	        });
	    }

}
