package server.utility.database;

import common.data.*;
import common.exceptions.databaseHandling;
import common.interaction.humanRaw;
import common.interaction.User;
import common.utility.outPuter;
import server.app;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Stack;


public class databaseCollectionManager {
	// HUMAN_TABLE
    private final String SELECT_ALL_HUMAN = "SELECT * FROM " + databaseComunication.HUMAN_TABLE;
    private final String SELECT_HUMAN_BY_ID = SELECT_ALL_HUMAN + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_HUMAN_BY_ID_AND_USER_ID = SELECT_HUMAN_BY_ID + " AND " +
    		databaseComunication.HUMAN_TABLE_ACCOUNT_ID_COLUMN + " = ?";
    private final String INSERT_HUMAN = "INSERT INTO " +
    		databaseComunication.HUMAN_TABLE + " (" +
    		databaseComunication.HUMAN_TABLE_NAME_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_COORDINATES_ID_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_CREATION_DATE_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_TRUE_HERO_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_TOOTH_PICK_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_SPEED_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_MINUTES_WATING_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_WEAPON_ID_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_MOOD_ID_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_CAR_ID_COLUMN + ", " +
    		databaseComunication.HUMAN_TABLE_ACCOUNT_ID_COLUMN + ")" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_HUMAN_BY_ID = "DELETE FROM " + databaseComunication.HUMAN_TABLE +
            " WHERE " + databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_NAME_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_COORDINATES_ID_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_COORDINATES_ID_COLUMN + " = ?::coordinates" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_TRUE_HERO_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_TRUE_HERO_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_TOOTH_PICK_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_TOOTH_PICK_COLUMN  + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_SPEED_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_SPEED_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_MINUTES_WAITING_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_MINUTES_WATING_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_WEAPON_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_WEAPON_ID_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_MOOD_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_MOOD_ID_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_HUMAN_CAR_ID_BY_ID = "UPDATE " + databaseComunication.HUMAN_TABLE + " SET " +
    		databaseComunication.HUMAN_TABLE_CAR_ID_COLUMN + " = ?::car" + " WHERE " +
    		databaseComunication.HUMAN_TABLE_ID_COLUMN + " = ?";
    // COORDINATES_TABLE
    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + databaseComunication.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_COORDINATES_ID = SELECT_ALL_COORDINATES +
            " WHERE " + databaseComunication.COORDINATES_TABLE_ID_COLUMN+ " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
    		databaseComunication.COORDINATES_TABLE + " (" +
    		databaseComunication.COORDINATES_TABLE_X_COLUMN + ", " +
    		databaseComunication.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?)";
    private final String DELETE_COORDINATES_BY_ID = "DELETE FROM " + databaseComunication.COORDINATES_TABLE +
            " WHERE " + databaseComunication.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_COORDINATES_BY_HUMAN_ID = "UPDATE " +
    		databaseComunication.COORDINATES_TABLE + " SET " +
    		databaseComunication.COORDINATES_TABLE_X_COLUMN + " = ?, " +
    		databaseComunication.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.COORDINATES_TABLE_ID_COLUMN + " = ?";
    // car table
    private final String SELECT_ALL_CAR = "SELECT * FROM " + databaseComunication.CAR_TABLE;
    private final String SELECT_CAR_BY_CAR_ID = SELECT_ALL_CAR +
            " WHERE " + databaseComunication.CAR_TABLE_ID_COLUMN+ " = ?";
    private final String INSERT_CAR = "INSERT INTO " +
    		databaseComunication.CAR_TABLE + " (" +
            databaseComunication.CAR_TABLE_COOL_COLUMN +") VALUES (?)";
    private final String DELETE_CAR_BY_ID = "DELETE FROM " + databaseComunication.CAR_TABLE +
            " WHERE " + databaseComunication.CAR_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_CAR_BY_CAR_ID = "UPDATE " +
    		databaseComunication.CAR_TABLE + " SET " +
    		databaseComunication.CAR_TABLE_COOL_COLUMN + " = ?" + " WHERE " +
    		databaseComunication.CAR_TABLE_ID_COLUMN + " = ?";
    
    // Weapon table
    private final String SELECT_ALL_ID_WEAPON = "SELECT weaponID FROM " + databaseComunication.WEAPON_TABLE;
    private final String SELECT_ID_WEAPON_BY_TYPE = SELECT_ALL_ID_WEAPON + " WHERE type = ? ";
    private final String SELECT_TYPE_WEAPON_BY_ID = "SELECT * FROM " + databaseComunication.WEAPON_TABLE +
            " WHERE weaponId = ? ";
    // Mood table
    private final String SELECT_ALL_ID_MOOD = "SELECT moodID FROM " + databaseComunication.MOOD_TABLE;
    private final String SELECT_ID_MOOD_BY_TYPE = SELECT_ALL_ID_MOOD + " WHERE type = ? ";
    private final String SELECT_TYPE_MOOD_BY_ID = "SELECT * FROM " + databaseComunication.MOOD_TABLE +
            " WHERE moodId = ? ";
    // Reset Id
    private final String RESET_ALL_ID_HUMAN ="ALTER SEQUENCE " + databaseComunication.HUMAN_TABLE + "_" +     
    			  databaseComunication.HUMAN_TABLE_ID_COLUMN + "_seq RESTART WITH 1" ;
    		
	
    private databaseComunication DatabaseComunication;
    private databaseUserManager databaseUserManager;

    public databaseCollectionManager (databaseComunication DatabaseComunication, databaseUserManager databaseUserManager) {
        this.DatabaseComunication = DatabaseComunication;
        this.databaseUserManager = databaseUserManager;
    }
    /**
     * @return List of Human.
     * @throws databaseHandling When there's exception inside.
     */
    public Stack<humanbeing> getCollection() throws databaseHandling {
        Stack<humanbeing> humanList = new Stack<>();
        PreparedStatement preparedSelectAllStatement = null;
        try {
            preparedSelectAllStatement = DatabaseComunication.getPreparedStatement(SELECT_ALL_HUMAN, false);
            ResultSet resultSet = preparedSelectAllStatement.executeQuery();
            while (resultSet.next()) {
            	humanList.add(CreateHuman(resultSet));
            }
//            System.out.println(organizationList);
        } catch (SQLException exception) {
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectAllStatement);
        }
        return humanList;
    }
    /**
     * Create Human.
     *
     * @param resultSet Result set parameters of Human.
     * @return New Human.
     * @throws SQLException When there's exception inside.
     */
    private humanbeing CreateHuman(ResultSet resultSet) throws SQLException {
    	long id = resultSet.getLong(DatabaseComunication.HUMAN_TABLE_ID_COLUMN);
    	 String name = resultSet.getString(DatabaseComunication.HUMAN_TABLE_NAME_COLUMN);
         LocalDateTime creationDate = resultSet.getTimestamp(DatabaseComunication.HUMAN_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
         boolean Hero = Boolean.valueOf(resultSet.getString(DatabaseComunication.HUMAN_TABLE_TRUE_HERO_COLUMN));
         Boolean toothpick = Boolean.valueOf(resultSet.getString(DatabaseComunication.HUMAN_TABLE_TOOTH_PICK_COLUMN));
         double speed = resultSet.getDouble(DatabaseComunication.HUMAN_TABLE_SPEED_COLUMN);
         Integer minutes = resultSet.getInt(DatabaseComunication.HUMAN_TABLE_MINUTES_WATING_COLUMN);
         weapontype weapon = getWeaponTypeByTypeId(resultSet.getInt(DatabaseComunication.HUMAN_TABLE_WEAPON_ID_COLUMN));
         mood Mood = getMoodTypeByTypeId(resultSet.getInt(DatabaseComunication.HUMAN_TABLE_MOOD_ID_COLUMN));
         coordinates coordinates = getCoordinatesByCoordinatesId(resultSet.getInt(DatabaseComunication.HUMAN_TABLE_COORDINATES_ID_COLUMN));
         car car = getCarByCarId(resultSet.getLong(DatabaseComunication.HUMAN_TABLE_CAR_ID_COLUMN));
         User owner = databaseUserManager.getUserById(resultSet.getLong(DatabaseComunication.HUMAN_TABLE_ACCOUNT_ID_COLUMN));
         return new humanbeing(
                 id,
                 name,
                 coordinates,
                 creationDate,
                 Hero,
                 toothpick,
                 speed,
                 minutes,
                 weapon,
                 Mood,
                 car,
                 owner
         );
    }
    /**
     * @param humanId Id of human.
     * @return coordinates.
     * @throws SQLException When there's exception inside.
     */
    private coordinates getCoordinatesByCoordinatesId(long humanId) throws SQLException {
        coordinates coordinates;
        PreparedStatement preparedSelectCoordinatesByOrganizationIdStatement = null;
        try {
            preparedSelectCoordinatesByOrganizationIdStatement =
            		DatabaseComunication.getPreparedStatement(SELECT_COORDINATES_BY_COORDINATES_ID, false);
            preparedSelectCoordinatesByOrganizationIdStatement.setLong(1, humanId);
            ResultSet resultSet = preparedSelectCoordinatesByOrganizationIdStatement.executeQuery();
            app.logger.info("SELECT_COORDINATES_BY_COORDINATES_ID query completed.");
            if (resultSet.next()) {
                coordinates = new coordinates(
                        resultSet.getInt(DatabaseComunication.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getInt(DatabaseComunication.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_COORDINATES_BY_ORGANIZATION_ID query!");
            throw new SQLException(exception);
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectCoordinatesByOrganizationIdStatement);
        }
        return coordinates;
    }
    
    /**
     * @param carId ID of Car.
     * @return Address.
     * @throws SQLException When there's exception inside.
     */
    private car getCarByCarId(long carId) throws SQLException {
        car car;
        PreparedStatement preparedSelectAddressByAddressIdStatement = null;
        try {
            preparedSelectAddressByAddressIdStatement =
            		DatabaseComunication.getPreparedStatement(SELECT_CAR_BY_CAR_ID, false);
            preparedSelectAddressByAddressIdStatement.setLong(1, carId);
            ResultSet resultSet = preparedSelectAddressByAddressIdStatement.executeQuery();
            app.logger.info("SELECT_ADDRESS_BY_ADDRESS_ID query completed.");
            if (resultSet.next()) {
                car = new car(
                        resultSet.getBoolean(DatabaseComunication.CAR_TABLE_COOL_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_ADDRESS_BY_ADDRESS_ID query!");
            throw new SQLException(exception);
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectAddressByAddressIdStatement);
        }
        return car;
    }
    /**
     * get weapon
     * @param typeId
     * @return
     */
    private weapontype getWeaponTypeByTypeId(int typeId) {
    	weapontype weapon = null;
        PreparedStatement preparedSelectWeaponTypeByTypeIdStatement = null;
        try {
        	preparedSelectWeaponTypeByTypeIdStatement =
            		DatabaseComunication.getPreparedStatement(SELECT_TYPE_WEAPON_BY_ID, false);
        	preparedSelectWeaponTypeByTypeIdStatement.setInt(1, typeId);
            ResultSet resultSet = preparedSelectWeaponTypeByTypeIdStatement.executeQuery();
            app.logger.info("SELECT_WEAPON_TYPE_BY_ID query completed.");
            if (resultSet.next()) {
            	weapon = weapontype.valueOf(resultSet.getString(DatabaseComunication.WEAPON_TABLE_TYPE_COLUMN));
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_WEAPON_TYPE_BY_ID query!");
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectWeaponTypeByTypeIdStatement);
        }
        return weapon;
    }
    /**
     * get id weapon type
     * @param type
     * @return
     * @throws SQLException
     */
    private int getWeaponTypeIdByType(String type) throws SQLException {
        int typeId;
        PreparedStatement preparedSelectWeaponTypeIdByTypeStatement = null;
        try {
        	preparedSelectWeaponTypeIdByTypeStatement =
        			DatabaseComunication.getPreparedStatement(SELECT_ID_WEAPON_BY_TYPE, false);
        	preparedSelectWeaponTypeIdByTypeStatement.setString(1, type);
            ResultSet resultSet = preparedSelectWeaponTypeIdByTypeStatement.executeQuery();
            app.logger.info("SELECT_ID_WEAPON_BY_TYPE query completed.");
            if (resultSet.next()) {
                typeId = resultSet.getInt(DatabaseComunication.WEAPON_TABLE_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_ID_WEAPON_BY_TYPE query!");
            throw new SQLException(exception);
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectWeaponTypeIdByTypeStatement);
        }
        return typeId;
    }
    /**
     * get mood
     * @param typeId
     * @return
     */
    private mood getMoodTypeByTypeId(int typeId) {
    	mood Mood = null;
        PreparedStatement preparedSelectMoodTypeByTypeIdStatement = null;
        try {
        	preparedSelectMoodTypeByTypeIdStatement =
            		DatabaseComunication.getPreparedStatement(SELECT_TYPE_MOOD_BY_ID, false);
        	preparedSelectMoodTypeByTypeIdStatement.setInt(1, typeId);
            ResultSet resultSet = preparedSelectMoodTypeByTypeIdStatement.executeQuery();
            app.logger.info("SELECT_TYPE_MOOD_BY_ID query completed.");
            if (resultSet.next()) {
            	Mood = mood.valueOf(resultSet.getString(DatabaseComunication.MOOD_TABLE_TYPE_COLUMN));
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_TYPE_MOOD_BY_ID query!");
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectMoodTypeByTypeIdStatement);
        }
        return Mood;
    }
    /**
     * get mood id
     * @param type
     * @return
     * @throws SQLException
     */
    private int getMoodTypeIdByType(String type) throws SQLException {
        int typeId;
        PreparedStatement preparedSelectMoodTypeIdByTypeStatement = null;
        try {
        	preparedSelectMoodTypeIdByTypeStatement =
        			DatabaseComunication.getPreparedStatement(SELECT_ID_MOOD_BY_TYPE, false);
        	preparedSelectMoodTypeIdByTypeStatement.setString(1, type);
            ResultSet resultSet = preparedSelectMoodTypeIdByTypeStatement.executeQuery();
            app.logger.info("SELECT_ID_MOOD_BY_TYPE query completed.");
            if (resultSet.next()) {
                typeId = resultSet.getInt(DatabaseComunication.MOOD_TABLE_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_ID_MOOD_BY_TYPE query!");
            throw new SQLException(exception);
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectMoodTypeIdByTypeStatement);
        }
        return typeId;
    }
    
    // *** Insert data to database
    /**
     * @param organizationRaw Organization raw.
     * @param user User.
     * @return Organization.
     * @throws DatabaseHandlingException When there's exception inside.
     */
    public humanbeing insertHuman(humanRaw humanRaw, User user) throws databaseHandling {
        /*
          TODO: lấy data raw từ client request, xử lý và thêm vào table organization:
           - lấy ID từ hàm getGeneratedKeys() của PreparedStatement
           - phải thêm vào các bảng phụ trước sau đó lấy id để thêm vào bảng chính
           - Có hai cách thêm enum:
                +   Đồng bộ thứ tự enum trong class và bảng ghi
                    Lấy index của element trong enum điền vào bảng luôn
                    =))) có vẻ không hợp lý khi số lượng enum nhiều và khó khăn trong việc không thể thêm mới đc element
                    trong bảng. Hơi chuối nhưng khá nhanh với lượng enum ít
                +   Lấy String từ data raw sau và search trong bảng để lấy index, rồi thêm vào bảng chính
           - (optional) check tồn tại của Address và Coordinates:
                + Nếu tồn tại thì lấy id của cái cũ
                + Nếu không thì tạo mới
         */

    	humanbeing human;
        PreparedStatement preparedInsertHumanStatement = null;
        PreparedStatement preparedInsertCoordinatesStatement = null;
        PreparedStatement preparedInsertCarStatement = null;
        try {
        	DatabaseComunication.setCommitMode();
        	DatabaseComunication.setSavepoint();

            LocalDateTime creationTime = LocalDateTime.now();

            preparedInsertHumanStatement = DatabaseComunication.getPreparedStatement(INSERT_HUMAN, true);
            preparedInsertCoordinatesStatement = DatabaseComunication.getPreparedStatement(INSERT_COORDINATES, true);
            preparedInsertCarStatement = DatabaseComunication.getPreparedStatement(INSERT_CAR, true);
            // Step 1: Insert car
            preparedInsertCarStatement.setBoolean(1, humanRaw.getCar().getCool());

            if (preparedInsertCarStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedCarKeys = preparedInsertCarStatement.getGeneratedKeys();
            int carId; // here
            if (generatedCarKeys.next()) {
                carId = generatedCarKeys.getInt(1);
            } else throw new SQLException();
            app.logger.info("INSERT_CAR query completed.");

            // Step 2: Insert coordinates
            preparedInsertCoordinatesStatement.setInt(1, humanRaw.getCoordinates().getX());
            preparedInsertCoordinatesStatement.setDouble(2, humanRaw.getCoordinates().getY());

            if (preparedInsertCoordinatesStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedCoordinatesKeys = preparedInsertCoordinatesStatement.getGeneratedKeys();
            int coordinatesId; // here
            if (generatedCoordinatesKeys.next()) {
                coordinatesId = generatedCoordinatesKeys.getInt(1);
            } else throw new SQLException();
            app.logger.info("INSERT_COORDINATES query completed.");           
            // Step 3: Get ID of weapon type
            weapontype weaponRaw = humanRaw.getWeaponType();
            int weaponTypeId = getWeaponTypeIdByType(weaponRaw.toString());
            // Step 4: Get ID of mood
            mood moodRaw = humanRaw.getMood();
            int moodTypeId = getMoodTypeIdByType(moodRaw.toString());
            // Step: 5 Insert human to database
            preparedInsertHumanStatement.setString(1, humanRaw.getName());
            preparedInsertHumanStatement.setInt(2, coordinatesId);
            preparedInsertHumanStatement.setTimestamp(3, Timestamp.valueOf(creationTime));
            preparedInsertHumanStatement.setBoolean(4, humanRaw.getHero());
            preparedInsertHumanStatement.setBoolean(5, humanRaw.getToothpick());
            preparedInsertHumanStatement.setDouble(6, humanRaw.getSpeed());
            preparedInsertHumanStatement.setInt(7, humanRaw.getMinutesWaiting());
            preparedInsertHumanStatement.setInt(8, weaponTypeId);
            preparedInsertHumanStatement.setInt(9, moodTypeId);
            preparedInsertHumanStatement.setInt(10, carId);
            preparedInsertHumanStatement.setLong(11, databaseUserManager.getUserIdByUsername(user));

            if (preparedInsertHumanStatement.executeUpdate() == 0) throw new SQLException();
            ResultSet generatedHumanKeys = preparedInsertHumanStatement.getGeneratedKeys();
            long humanId;
            if (generatedHumanKeys.next()) {
            	humanId = generatedHumanKeys.getLong(1);
            } else throw new SQLException();
            app.logger.info("INSERT_HUMAN query completed.");

            // create new to add collection
            human = new humanbeing(
                    humanId,
                    humanRaw.getName(),
                    humanRaw.getCoordinates(),
                    creationTime,
                    humanRaw.getHero(),
                    humanRaw.getToothpick(),
                    humanRaw.getSpeed(),
                    humanRaw.getMinutesWaiting(),
                    humanRaw.getWeaponType(),
                    humanRaw.getMood(),
                    humanRaw.getCar(),
                    user
            );

            DatabaseComunication.commit();
            return human;
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing a group of requests to add a new object!");
            DatabaseComunication.rollback();
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedInsertHumanStatement);
        	DatabaseComunication.closePreparedStatement(preparedInsertCoordinatesStatement);
        	DatabaseComunication.closePreparedStatement(preparedInsertCarStatement);
        	DatabaseComunication.setNormalMode();
        }
    }
 // *** Update data ***
    /**
     * @param humanRaw human raw.
     * @param humanId  ID of Human.
     * @throws databaseHandling When there's exception inside.
     */
    public void updateHumanById(long humanId, humanRaw humanRaw) throws databaseHandling {
        /* TODO: - Chỉnh sửa thông tin bảng chính không phụ thuộc
                 - Thông qua bảng chính lấy id của bảng phụ
                 - Chỉnh sửa bảng phụ bằng id đã lấy
         */
        PreparedStatement prepareSelectOldHumanByIdStatement = null;

        // ***Bảng chính không phụ thuộc***
        PreparedStatement preparedUpdateHumanNameByIdStatement = null;
        PreparedStatement preparedUpdateHumanHeroByIdStatement = null;
        PreparedStatement preparedUpdateHumanToothpickByIdStatement = null;
        PreparedStatement preparedUpdateHumanSpeedByIdStatement = null;
        PreparedStatement preparedUpdateHumanMinutesByIdStatement = null;
        PreparedStatement preparedUpdateHumanWeaponIdByIdStatement = null;
        PreparedStatement preparedUpdateHumanMoodIdByIdStatement = null; // in main table update type id

        // ***Bảng phụ***
        PreparedStatement preparedUpdateHumanCoordinatesByCoordinatesIdStatement = null; // in sub table update coordinates
        PreparedStatement preparedUpdateHumanCarByCarIdStatement = null; // in sub table update address
        try {
        	DatabaseComunication.setCommitMode();
        	DatabaseComunication.setSavepoint();

            //  Attributes do not reference
        	preparedUpdateHumanNameByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_NAME_BY_ID, false);
        	preparedUpdateHumanHeroByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_TRUE_HERO_BY_ID, false);
        	preparedUpdateHumanToothpickByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_TOOTH_PICK_BY_ID, false);
        	preparedUpdateHumanSpeedByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_SPEED_BY_ID, false);
        	preparedUpdateHumanMinutesByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_MINUTES_WAITING_BY_ID, false);
        	preparedUpdateHumanWeaponIdByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_WEAPON_BY_ID, false);
        	preparedUpdateHumanMoodIdByIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_HUMAN_MOOD_BY_ID, false);
            //  Attributes reference
        	prepareSelectOldHumanByIdStatement = DatabaseComunication.getPreparedStatement(SELECT_HUMAN_BY_ID, false);
            int coordinatesId = 0;
            int carId = 0;
            
            preparedUpdateHumanCoordinatesByCoordinatesIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_COORDINATES_BY_HUMAN_ID, false);
            preparedUpdateHumanCarByCarIdStatement = DatabaseComunication.getPreparedStatement(UPDATE_CAR_BY_CAR_ID, false);


            if (humanRaw.getName() != null) {
            	preparedUpdateHumanNameByIdStatement.setString(1, humanRaw.getName());
            	preparedUpdateHumanNameByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanNameByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("UPDATE_HUMAN_NAME_BY_ID request was made.");
            }
            if (humanRaw.getHero() != null) {
            	preparedUpdateHumanHeroByIdStatement.setBoolean(1, humanRaw.getHero());
            	preparedUpdateHumanHeroByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanHeroByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_HERO_BY_ID request was made.");
            }
            if (humanRaw.getToothpick() != null) {
            	preparedUpdateHumanToothpickByIdStatement.setBoolean(1, humanRaw.getToothpick());
            	preparedUpdateHumanToothpickByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanToothpickByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_TOOTH_PICK_BY_ID request was made.");
            }
            if (humanRaw.getSpeed() != null) {
            	preparedUpdateHumanSpeedByIdStatement.setDouble(1, humanRaw.getSpeed());
            	preparedUpdateHumanSpeedByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanSpeedByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_SPEED_BY_ID request was made.");
            }
            if (humanRaw.getMinutesWaiting() != null) {
            	preparedUpdateHumanMinutesByIdStatement.setInt(1, humanRaw.getMinutesWaiting());
            	preparedUpdateHumanMinutesByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanMinutesByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_MINUTES_WATING_BY_ID request was made.");
            }
            
            if (humanId != -1) {
            	prepareSelectOldHumanByIdStatement.setLong(1, humanId);
                ResultSet oldHuman = prepareSelectOldHumanByIdStatement.executeQuery();
                while (oldHuman.next()) {
                    coordinatesId = oldHuman.getInt(DatabaseComunication.HUMAN_TABLE_COORDINATES_ID_COLUMN);
                    carId = oldHuman.getInt(DatabaseComunication.HUMAN_TABLE_CAR_ID_COLUMN);
                }
            }
            if (humanRaw.getWeaponType() != null) {
            	String newWeapon = humanRaw.getWeaponType().toString();
            	int newWeaponId = getWeaponTypeIdByType(newWeapon);
            	preparedUpdateHumanWeaponIdByIdStatement.setInt(1, newWeaponId);
            	preparedUpdateHumanWeaponIdByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanWeaponIdByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_WEAPON_BY_ID request was made.");
            }
            if (humanRaw.getMood() != null) {
            	String newMood = humanRaw.getMood().toString();
            	int newMoodType = getMoodTypeIdByType(newMood);
            	preparedUpdateHumanMoodIdByIdStatement.setInt(1, newMoodType);
            	preparedUpdateHumanMoodIdByIdStatement.setLong(2, humanId);
                if (preparedUpdateHumanMoodIdByIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_MOOD_BY_ID request was made.");
            }

            if (humanRaw.getCoordinates() != null) {
            	preparedUpdateHumanCoordinatesByCoordinatesIdStatement.setInt(1, humanRaw.getCoordinates().getX());
            	preparedUpdateHumanCoordinatesByCoordinatesIdStatement.setInt(2, humanRaw.getCoordinates().getY());
            	preparedUpdateHumanCoordinatesByCoordinatesIdStatement.setInt(3, coordinatesId);
                if (preparedUpdateHumanCoordinatesByCoordinatesIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_COORDINATES_BY_HUMAN_ID request was made.");
            }
            if (humanRaw.getCar() != null) {
            	preparedUpdateHumanCarByCarIdStatement.setBoolean(1, humanRaw.getCar().getCool());
            	preparedUpdateHumanCarByCarIdStatement.setInt(2, carId);
                if (preparedUpdateHumanCarByCarIdStatement.executeUpdate() == 0) throw new SQLException();
                app.logger.info("An UPDATE_HUMAN_CAR_BY_ID request was made.");
            }
            

            DatabaseComunication.commit();
        } catch (SQLException exception) {
            app.logger.error(exception + "An error occurred while executing a group of requests to update an object!");
            DatabaseComunication.rollback();
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanNameByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanCoordinatesByCoordinatesIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanHeroByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanToothpickByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanSpeedByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanMinutesByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanWeaponIdByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanMoodIdByIdStatement);
        	DatabaseComunication.closePreparedStatement(preparedUpdateHumanCarByCarIdStatement);
        	DatabaseComunication.setNormalMode();
        }
    }
    /**
     * Delete Human by id.
     *
     * @param humanId ID of Human.
     * @throws databaseHandling When there's exception inside.
     */
    public void deleteHumanById(long humanId) throws databaseHandling {
        // TODO: Если делаем орден уникальным, тут че-то много всего менять
        PreparedStatement preparedDeleteHumanByIdStatement = null;
        try {
        	preparedDeleteHumanByIdStatement = DatabaseComunication.getPreparedStatement(DELETE_HUMAN_BY_ID, false);
        	preparedDeleteHumanByIdStatement.setLong(1, humanId);
            if (preparedDeleteHumanByIdStatement.executeUpdate() == 0) outPuter.println(3);
            app.logger.info("DELETE_HUMAN_BY_ID request was made.");
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the DELETE_HUMAN_BY_ID request!");
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedDeleteHumanByIdStatement);
        }
    }
    /**
     * Reset all human Id
     * @throws databaseHandling
     */
    public void resetAllHumanId() throws databaseHandling {
    	PreparedStatement preparedResetAllHumanIdStatement = null;
    	try {
    		preparedResetAllHumanIdStatement = DatabaseComunication.getPreparedStatement(RESET_ALL_ID_HUMAN, false);
    		if (preparedResetAllHumanIdStatement.executeUpdate() == 0) outPuter.println(3);
            app.logger.info("RESET_ALL_ID_HUMAN request was made.");
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the RESET_ALL_ID_HUMAN request!");
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedResetAllHumanIdStatement);
        }
    }
    	
    
    /**
     * Checks Human user id.
     *
     * @param humanId Id of Human.
     * @param user Owner of human.
     * @throws databaseHandling When there's exception inside.
     * @return Is everything ok.
     */
    public boolean checkHumanUserId(long humanId, User user) throws databaseHandling {
        PreparedStatement preparedSelectHumanByIdAndUserIdStatement = null;
        try {
        	preparedSelectHumanByIdAndUserIdStatement = DatabaseComunication.getPreparedStatement(SELECT_HUMAN_BY_ID_AND_USER_ID, false);
        	preparedSelectHumanByIdAndUserIdStatement.setLong(1, humanId);
        	preparedSelectHumanByIdAndUserIdStatement.setLong(2, databaseUserManager.getUserIdByUsername(user));
            ResultSet resultSet = preparedSelectHumanByIdAndUserIdStatement.executeQuery();
            app.logger.info("SELECT_HUMAN_BY_ID_AND_USER_ID query completed.");
            return resultSet.next();
        } catch (SQLException exception) {
            app.logger.error("An error occurred while executing the SELECT_HUMAN_BY_ID_AND_USER_ID query!");
            throw new databaseHandling();
        } finally {
        	DatabaseComunication.closePreparedStatement(preparedSelectHumanByIdAndUserIdStatement);
        }
    }
    /**
     * Clear the collection.
     *
     * @throws databaseHandling When there's exception inside.
     */
    public void clearCollection() throws databaseHandling {
        Stack<humanbeing> humanList = getCollection();
        for (humanbeing human : humanList) {
        	deleteHumanById(human.getId());
        }
    }
}
