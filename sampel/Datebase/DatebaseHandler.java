package Datebase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Model.Configs;
import Model.Const;
import Model.User;

import java.sql.ResultSet;

public class DatebaseHandler extends Configs {
	Connection dbConnection;

	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";

		Class.forName("com.mysql.jdbc.Driver");

		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
		return dbConnection;
	}

	public void signUpUser(User user) {

		String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_NAME + "," + Const.USER_SURNAME + ","
				+ Const.USER_PATRONYMIC + "," + Const.USER_PASSWORD + "," + Const.USER_LOGIN + ")"
				+ "VALUES(?,?,?,?,?)";

		try {
			PreparedStatement prSt;
			prSt = getDBConnection().prepareStatement(insert);
			prSt.setString(1, user.getName());
			prSt.setString(2, user.getSurname());
			prSt.setString(3, user.getPatronymic());
			prSt.setString(4, user.getPassword());
			prSt.setString(5, user.getLogin());
			prSt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getUser(User user) {
		ResultSet resSet = null;

		String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_LOGIN + " =? AND "
				+ Const.USER_PASSWORD + "=?";

		try {
			PreparedStatement prSt;
			prSt = getDBConnection().prepareStatement(select);
			prSt.setString(1, user.getLogin());
			prSt.setString(2, user.getPassword());
			resSet = prSt.executeQuery();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resSet;
	}
}
