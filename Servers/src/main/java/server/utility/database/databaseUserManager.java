package server.utility.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.exceptions.databaseHandling;
import common.interaction.User;
import server.app;

public class databaseUserManager {
	// USER_TABLE
    private final String SELECT_USER_BY_ID = "SELECT * FROM " + databaseComunication.USER_TABLE +
            " WHERE " + databaseComunication.USER_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + databaseComunication.USER_TABLE +
            " WHERE " + databaseComunication.USER_TABLE_USERNAME_COLUMN + " = ?";
    private final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
    		databaseComunication.USER_TABLE_PASSWORD_COLUMN + " = ?";
    private final String INSERT_USER = "INSERT INTO " +
    		databaseComunication.USER_TABLE + " (" +
    		databaseComunication.USER_TABLE_USERNAME_COLUMN + ", " +
    		databaseComunication.USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?)";

    private databaseComunication DatabaseCommunication;

    public databaseUserManager(databaseComunication DatabaseCommunication) {
        this.DatabaseCommunication = DatabaseCommunication;
    }

    /**
     * @param userId User id.
     * @return User by id.
     * @throws SQLException When there's exception inside.
     */
    public User getUserById(long userId) throws SQLException {
        User user;
        PreparedStatement preparedSelectUserByIdStatement = null;
        try {
            preparedSelectUserByIdStatement =
            		DatabaseCommunication.getPreparedStatement(SELECT_USER_BY_ID, false);
            preparedSelectUserByIdStatement.setLong(1, userId);
            ResultSet resultSet = preparedSelectUserByIdStatement.executeQuery();
            app.logger.info("SELECT_USER_BY_ID query completed.");
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString(DatabaseCommunication.USER_TABLE_USERNAME_COLUMN),
                        resultSet.getString(DatabaseCommunication.USER_TABLE_PASSWORD_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
        	app.logger.error("An error occurred while executing the SELECT_USER_BY_ID query!");
            throw new SQLException(exception);
        } finally {
        	DatabaseCommunication.closePreparedStatement(preparedSelectUserByIdStatement);
        }
        return user;
    }

    /**
     * Check user by username and password.
     *
     * @param user User.
     * @return Result set.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean checkUserByUsernameAndPassword(User user) throws databaseHandling {
        PreparedStatement preparedSelectUserByUsernameAndPasswordStatement = null;
        try {
            preparedSelectUserByUsernameAndPasswordStatement =
            		DatabaseCommunication.getPreparedStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD, false);
            preparedSelectUserByUsernameAndPasswordStatement.setString(1, user.getUsername());
            preparedSelectUserByUsernameAndPasswordStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedSelectUserByUsernameAndPasswordStatement.executeQuery();
            app.logger.info("SELECT_USER_BY_USERNAME_AND_PASSWORD query completed.");
            return resultSet.next();
        } catch (SQLException exception) {
        	System.out.println(exception);
        	app.logger.error("An error occurred while executing the SELECT_USER_BY_USERNAME_AND_PASSWORD query!");
            throw new databaseHandling();
        } finally {
        	DatabaseCommunication.closePreparedStatement(preparedSelectUserByUsernameAndPasswordStatement);
        }
    }

    /**
     * Get user id by username.
     *
     * @param user User.
     * @return User id.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public long getUserIdByUsername(User user) throws databaseHandling {
        long userId;
        PreparedStatement preparedSelectUserByUsernameStatement = null;
        try {
            preparedSelectUserByUsernameStatement =
            		DatabaseCommunication.getPreparedStatement(SELECT_USER_BY_USERNAME, false);
            preparedSelectUserByUsernameStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedSelectUserByUsernameStatement.executeQuery();
            app.logger.info("SELECT_USER_BY_USERNAME query completed.");
            if (resultSet.next()) {
                userId = resultSet.getLong(DatabaseCommunication.USER_TABLE_ID_COLUMN);
            } else userId = -1;
            return userId;
        } catch (SQLException exception) {
        	app.logger.error("An error occurred while executing the SELECT_USER_BY_USERNAME query!");
            throw new databaseHandling();
        } finally {
        	DatabaseCommunication.closePreparedStatement(preparedSelectUserByUsernameStatement);
        }
    }

    /**
     * Insert user.
     *
     * @param user User.
     * @return Status of insert.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public boolean insertUser(User user) throws databaseHandling {
        PreparedStatement preparedInsertUserStatement = null;
        try {
            if (getUserIdByUsername(user) != -1) return false;
            preparedInsertUserStatement =
            		DatabaseCommunication.getPreparedStatement(INSERT_USER, false);
            preparedInsertUserStatement.setString(1, user.getUsername());
            preparedInsertUserStatement.setString(2, user.getPassword());
            if (preparedInsertUserStatement.executeUpdate() == 0) throw new SQLException();
            app.logger.info("INSERT_USER query completed.");
            return true;
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the INSERT_USER query!");
            throw new databaseHandling();
        } finally {
        	DatabaseCommunication.closePreparedStatement(preparedInsertUserStatement);
        }
    }

}
