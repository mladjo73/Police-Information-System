package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import databaseAccess.DbConnection;
import model.ApplicationModel;
import view.AppView;
import view.LoginView;

/**
 * Klasa koja implementira prijavu korisnika na sistem.
 * @author grupa4
 * 
 *
 */

public class LoginListener implements ActionListener
{
	ApplicationModel appModel = null;
	AppView appView = null;
	LoginView login = null;
	
	public LoginListener(AppView appView, LoginView login, ApplicationModel appModel)
	{
		this.appView = null;
		this.login = login;
		this.appModel = appModel;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String username = new String(login.getUsername());
		String password = new String(login.getPassword());
      
        DbConnection instance = DbConnection.GetInstance();
        Connection conn = instance.getConn();
        
        try {
			CallableStatement udspLogin = conn.prepareCall("{call Login(?,?)}");
			udspLogin.setString(1,  username);
			udspLogin.setString(2, password);
			
			ResultSet resultSet = udspLogin.executeQuery();
			if(resultSet.next())
			{
				login.setVisible(false);
				AppView appView = new AppView(appModel);
			}
			else
			{
				JOptionPane.showMessageDialog(null,  "Neispravno korisnicko ime ili lozinka!\nMolimo Vas da unesete ponovo.", "Error", JOptionPane.ERROR_MESSAGE);
			
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
