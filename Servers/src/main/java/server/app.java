package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.exceptions.NotInDeclaredLimit;
import common.exceptions.WrongAmountOfElements;
import common.utility.outPuter;
import server.commands.*;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.database.databaseCollectionManager;
import server.utility.database.databaseComunication;
import server.utility.database.databaseUserManager;

public class app {
    private static final int MAX_CLIENTS = 1000;
    public static final Logger logger = LoggerFactory.getLogger("ServerLogger");
	private static int port;
    private static String databaseHost;
    private static String databasePassword;
    private static String databaseAddress;
    private static final String databaseUsername = "postgres";


    public static void main(String[] args) {
        if (!initialize(args)) return;
        databaseComunication DatabaseCommunication = new databaseComunication(databaseAddress, databaseUsername, databasePassword);
        databaseUserManager DatabaseUserManager = new databaseUserManager(DatabaseCommunication);
        databaseCollectionManager DatabaseCollectionManager = new databaseCollectionManager(DatabaseCommunication, DatabaseUserManager);    	
        CollectionManager collectionManager = new CollectionManager(DatabaseCollectionManager);
        CommandManager commandManager = new CommandManager(
        		 new HelpCommand(),
        		 new InfoCommand(collectionManager),
                 new ShowCommand(collectionManager),
                 new AddCommand(collectionManager, DatabaseCollectionManager),
                 new UpdateIdCommand(collectionManager,DatabaseCollectionManager),
                 new RemoveByIdCommand(collectionManager, DatabaseCollectionManager),
                 new ClearCommand(collectionManager,DatabaseCollectionManager),
                 /*new SaveCommand(collectionManager),*/
                 new ExitCommand(),
                 new ExecuteScriptFileCommand(),
                 new FilterLessThanImpactSpeedCommand(collectionManager),
                 new PrintDescending(collectionManager),
                 new HistoryCommand(),
                 new ShuffleCommand(collectionManager),
                 new ReorderCommand(collectionManager),
                 new MinByImpactSpeedCommand(collectionManager),
                 new serverExitCommand(),
                 new LoginCommand(DatabaseUserManager),
                 new RegisterCommand(DatabaseUserManager)
                 );
        theServer server = new theServer(port, MAX_CLIENTS, commandManager);
        server.run();
        DatabaseCommunication.closeConnection();
    }
    /**
     * It initializes the port, database host and database password
     *
     * @return The method returns a boolean value of initialization status.
     */
    private static boolean initialize(String[] args) {
        try {
            if (args.length != 3) throw new WrongAmountOfElements();
            port = Integer.parseInt(args[0]);
            if (port < 0) throw new NotInDeclaredLimit();
            databaseHost = args[1];
            databasePassword = args[2];
            databaseAddress = "jdbc:postgresql://" + databaseHost + ":5433/HumanDB";
            return true;
        } catch (WrongAmountOfElements exception) {
            String jarName = new java.io.File(app.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            outPuter.println("To use: 'java -jar " + jarName + " <port> <db_host> <db_password>'");
        } catch (NumberFormatException exception) {
            outPuter.printerror("The port must be represented by a number!");
            app.logger.error("The port must be represented by a number!");
        } catch (NotInDeclaredLimit exception) {
        	outPuter.printerror("The port cannot be negative!");
        	app.logger.error("The port cannot be negative!");
        }
        app.logger.error("Launch port initialization error!");
        return false;
    }

}
