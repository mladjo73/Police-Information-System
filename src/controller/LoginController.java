package controller;

import databaseAccess.DbConnection;
import model.ApplicationModel;
import view.AppView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
* Klasa za upravljanje procesom prijave korisnika,osnovna funkcija je da primi podatke za prijavu (korisničko ime i lozinka), verifikuje ih i odredi da li su ispravni ili ne. 
* LoginController odgovoran za upravljanje prijavom i interakciju između modela (baze podataka) i prikaza (user interface-a).
* @author grupa4
*/

public class LoginController {
    private LoginView loginView;
    private ApplicationModel appModel;

    public LoginController(LoginView loginView, ApplicationModel appModel) {
        this.loginView = loginView;
        this.appModel = appModel;
        
        this.loginView.getRootPane().setDefaultButton(loginView.getLoginButton());

        this.loginView.addLoginListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        this.loginView.addCancelListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Zatvori aplikaciju
            }
        });
    }

    private void handleLogin() {
        String username = loginView.getUsername();
        String password = loginView.getPassword();

        DbConnection instance = DbConnection.GetInstance();
        Connection conn = instance.getConn();
        
        try {
            CallableStatement udspLogin = conn.prepareCall("{call Login(?,?)}");
            udspLogin.setString(1, username);
            udspLogin.setString(2, password);

            ResultSet resultSet = udspLogin.executeQuery();
            if (resultSet.next()) {
                loginView.dispose();  // Zatvori login prozor
                showAppView();  // Prikaži glavni prozor aplikacije
            } else {
                JOptionPane.showMessageDialog(loginView, "Neispravno korisničko ime ili lozinka!\nMolimo Vas da unesete ponovo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Očisti resurse i zatvori konekciju ako je potrebno
            instance.closeConnection();
        }
    }

    private void showAppView() {
        SwingUtilities.invokeLater(() -> {
            new AppView(appModel).setVisible(true);  // Otvori glavni prozor aplikacije
        });
    }

    public static void startLoginProcess(String xmlPath) {
        SwingUtilities.invokeLater(() -> {
            ApplicationModel appModel = new ApplicationModel(xmlPath);
            LoginView loginView = new LoginView(xmlPath);
            new LoginController(loginView, appModel);
            loginView.setVisible(true);
        });
    }

    public static void main(String[] args) {
        startLoginProcess("xml/sema.xml");
    }
}