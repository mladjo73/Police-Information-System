package databaseAccess;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * DbConnection je klasa koja predstavlja povezanost sa bazom podataka,
 * pruža osnovne metode za uspostavljanje, upravljanje i zatvaranje konekcije sa bazom podataka.
 * @author grupa4
 */
public class DbConnection {
	
	private static DbConnection instance;
	protected Connection conn = null;
	String address = null;
	String database = null;
	String port = null;
	String databaseType = null;
	String username = null;
	String password = null;
	
	// Privatni konstruktor da se spreči direktno instanciranje
	private DbConnection() {
		// Konstruktor ostaje prazan kako bismo izbegli automatsko otvaranje konekcije
	}
	
	// Metoda za dobijanje instance DbConnection klase (Singleton obrazac)
	public static DbConnection GetInstance() {
		if (instance == null) {
			instance = new DbConnection();
		}
		return instance;
	}
	
	// Metoda za otvaranje konekcije sa bazom podataka
	public void openConnection() {
	    if (conn != null) {
	        closeConnection();  // Zatvori prethodnu konekciju ako postoji
	    }

	    try {
	        File inputXML = new File("xml/sema.xml");
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputXML);

	        doc.getDocumentElement().normalize();
	        NodeList nodeList = doc.getElementsByTagName("connection");
	        for (int i = 0; i < nodeList.getLength(); i++) {
	            Node nodeConnection = nodeList.item(i);
	            if (nodeConnection.getNodeType() == Node.ELEMENT_NODE) {
	                Element xmlElement = (Element) nodeConnection;
	                address = xmlElement.getAttribute("address");
	                database = xmlElement.getAttribute("database");
	                port = xmlElement.getAttribute("port");
	                databaseType = xmlElement.getAttribute("database_type");
	                username = xmlElement.getAttribute("username");
	                password = xmlElement.getAttribute("password");
	                String connectionString = "jdbc:sqlserver://" + address + ":" + port + ";user=" + username
	                        + ";password=" + password + "; trustServerCertificate=true";

	                try {
	                    DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
	                    conn = DriverManager.getConnection(connectionString);
	                    System.out.println("Uspješna konekcija");
	                } catch (SQLException e) {
	                    System.out.println("Neuspješna konekcija");
	                    JOptionPane.showMessageDialog(null, "Došlo je do greške!\nOpis: " + e.getMessage(), "Greška!",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }
	}

	// Metoda za dobijanje Connection objekta
	public Connection getConn() {
		if (conn == null) {
            openConnection();  // Otvori novu konekciju ako nije već otvorena
        }
        return conn;
	}

	// Metoda za postavljanje Connection objekta
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	// Proverava da li je konekcija otvorena
	public boolean isConnOpen() {
		try {
			return conn != null && !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Zatvara trenutnu konekciju sa bazom podataka
	public void closeConnection() {
	    if (isConnOpen()) {
	        try {
	            conn.close();
	        } catch (SQLException e) {
	            ErrorHandlerMethod(null, e.getMessage());
	        } finally {
	            conn = null;  // Resetuj konekciju
	        }
	    }
	}

	// Proverava da li je konekcija validna u određenom timeout periodu
	public boolean isConnectionValid(int timeout) {
		if (isConnOpen()) {
			try {
				return conn.isValid(timeout);
			} catch (SQLException e) {
				ErrorHandlerMethod(null, e.getMessage());
			}
		}	
		return false;		
	}

	// Getter i setter metode za ostale atribute
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Metoda za prikazivanje grešaka
	protected void ErrorHandlerMethod(Component component, String errorMessage) {
		JOptionPane.showMessageDialog(component, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
}

