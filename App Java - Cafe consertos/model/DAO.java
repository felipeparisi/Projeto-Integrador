package model;

import java.sql.Connection;
import java.sql.DriverManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {

	/** The driver. */
	//Variáveis para setar o banco de dados
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	/** The url. */
	private String url = "jdbc:mysql://10.26.46.211:3306/dbcafeconsertos";
	
	/** The user. */
	private String user = "root";
	
	/** The password. */
	private String password = "123@senac";
	
	/** The con. */
	//Objeto (JDBC) usado para conectar o banco
	private Connection con;
	
	/**
	 * Conexão.
	 *
	 * @return con
	 */
	public Connection conectar() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
