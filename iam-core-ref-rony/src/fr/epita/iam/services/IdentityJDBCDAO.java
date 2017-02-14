/**
 * 
 */
package fr.epita.iam.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.DaoInitializationException;

/**
 * @author tbrou
 *
 */
public class IdentityJDBCDAO {

	private Connection currentConnection;
	private String sDate1 = "1994-03-03";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 
	 */
	public IdentityJDBCDAO() throws DaoInitializationException {	//create the connection with the database
			
		try {
			getConnection();
		} catch (SQLException e) {
			DaoInitializationException die = new DaoInitializationException();
			die.initCause(e);
			throw die;
		}
	}

	/**
	 * gets a connection from the database
	 * @throws SQLException
	 */
	private Connection getConnection() throws SQLException {	//method to get a connection
		try {
			this.currentConnection.getSchema();
		} catch (Exception e) {
			String user = "user";
			String password = "password";
			String connectionString = "jdbc:derby://localhost:1527/sample;create=true";	//connection string
			this.currentConnection = DriverManager.getConnection(connectionString, user, password);
		}
		return this.currentConnection;
	}

	/**
	 * close connection with database
	 */
	private void releaseResources() {
		try {
			this.currentConnection.close();
		} catch (Exception e) {
			System.out.println("There has been an error with the connection");
		}
	}

	/**
	 * Read all the identities from the database
	 * @return
	 * @throws SQLException
	 */
	public List<Identity> readAllIdentities() throws SQLException {
		List<Identity> identities = new ArrayList<Identity>();
		Connection connection = getConnection();

		PreparedStatement statement = connection.prepareStatement("select * from IDENTITIES");
		ResultSet rs = statement.executeQuery();

		while (rs.next()) {
			int uid = rs.getInt("IDENTITY_ID");
			String displayName = rs.getString("IDENTITY_DISPLAYNAME");
			String email = rs.getString("IDENTITY_EMAIL");
			Date date = rs.getDate("IDENTITY_BIRTHDATE");
			Identity identity = new Identity(String.valueOf(uid), displayName, email, date.toString());
			identities.add(identity);
		}
		
		this.releaseResources();
		return identities;
	}

	/**
	 * write an identity in the database
	 * @param identity
	 * @throws SQLException
	 */
	public void write(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "INSERT INTO IDENTITIES(IDENTITY_DISPLAYNAME, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) VALUES(?,?,?)";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getDisplayName());
		pstmt.setString(2, identity.getEmail());
		pstmt.setString(3, identity.getDate());

		pstmt.execute();
		this.releaseResources();
	}

	/**
	 * update the user
	 * @param identity
	 * @throws SQLException
	 */
	public void update(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME=?, IDENTITY_EMAIL=?, IDENTITY_BIRTHDATE=? WHERE IDENTITY_ID=?";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getDisplayName());
		pstmt.setString(2, identity.getEmail());
		pstmt.setString(3, identity.getDate());
		pstmt.setString(4, identity.getUid());

		pstmt.execute();
		this.releaseResources();
	}

	/**
	 * delete the user
	 * @param identity
	 * @throws SQLException
	 */
	public void delete(Identity identity) throws SQLException {
		Connection connection = getConnection();

		String sqlInstruction = "DELETE FROM IDENTITIES WHERE IDENTITY_ID=?";
		PreparedStatement pstmt = connection.prepareStatement(sqlInstruction);
		pstmt.setString(1, identity.getUid());

		pstmt.execute();
		this.releaseResources();
	}
}
