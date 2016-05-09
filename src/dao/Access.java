package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Player;

public class Access {

	public ArrayList<Player> getPlayers(Connection con) throws SQLException {
		ArrayList<Player> playerList = new ArrayList<Player>();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM players");
		ResultSet rs = stmt.executeQuery();
		try {
			while (rs.next()) {
				Player newPlayer = new Player();
				newPlayer.setID(rs.getInt("idplayers"));
				newPlayer.setName(rs.getString("name"));
				newPlayer.setPassword(rs.getString("password"));
				newPlayer.setChips(rs.getInt("chips"));
				playerList.add(newPlayer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return playerList;
	}

	public boolean NewPlayer(Connection con, String name, String password, int chips) {
		String query = "INSERT INTO players (name, chips, password)" + "VALUES (?, ?, ?)";

		// create the mysql insert preparedstatement
		PreparedStatement Stmt;
		try {
			Stmt = con.prepareStatement(query);
			Stmt.setString(1, name);
			Stmt.setInt(2, chips);
			Stmt.setString(3, password);

			Stmt.executeUpdate();
			con.close();

			System.out.println("Insert query(REGISTER) Successful!");
			return true;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Insert query(REGISTER) Failed!");
			return false;
		}

	}

	public boolean LoginPlayer(Connection con, String name, String password) throws SQLException {

		String query = "select * from players where name = ? and password = ?";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1, name);
		stmt.setString(2, password);
		ResultSet rs = stmt.executeQuery();
		boolean val = rs.next(); // next() returns false if there are no-rows
									// retrieved

		if (val == false) {
			System.out.println("Login Errado"); // prints this message if your
												// resultset is empty
		}

		con.close();
		return val;
	}

	public void updatePlayer(Connection con, String Playername, int ChipsNewValue) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "update players set chips = ? where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setInt(1, ChipsNewValue);
		preparedStmt.setString(2, Playername);

		// execute the java preparedstatement
		preparedStmt.executeUpdate();

		con.close();
	}

	public int getPlayerMoney(Connection con, String Playername) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}

		con.close();
		return result;
	}

	public void addChips(Connection con, String Playername, int addChips) throws SQLException {

		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}

		// create the java mysql update preparedstatement
		String query2 = "update players set chips = ? where name = ?";
		PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		preparedStmt2.setInt(1, result + addChips);
		preparedStmt2.setString(2, Playername);

		// execute the java preparedstatement
		preparedStmt2.executeUpdate();

		con.close();
	}

	public boolean removeChips(Connection con, String Playername, int removeChips) throws SQLException {

		boolean remove=false;
		
		// create the java mysql update preparedstatement
		String query = "SELECT chips FROM players where name = ?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, Playername);

		// execute the java preparedstatement
		ResultSet rs = preparedStmt.executeQuery();
		int result = 0;
		while (rs.next()) {
			result = rs.getInt(1);
		}
		
		if(result >= removeChips) {
			// create the java mysql update preparedstatement
			String query2 = "update players set chips = ? where name = ?";
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			preparedStmt2.setInt(1, result - removeChips);
			preparedStmt2.setString(2, Playername);

			// execute the java preparedstatement
			preparedStmt2.executeUpdate();
			
			remove = true;
		}
			
		con.close();
		
		return remove;
	}
}
