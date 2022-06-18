package server.commands;

import server.utility.CollectionManager;
import common.exceptions.WrongAmountOfElements;
import common.exceptions.databaseHandling;
import common.interaction.User;
import common.interaction.humanRaw;
import server.utility.responseOutputer;
import server.utility.database.databaseCollectionManager;

public class AddCommand extends AbstractCommand {
	private final CollectionManager collectionManager;
    private final databaseCollectionManager DatabaseCollectionManager;

	
	public AddCommand(CollectionManager collectionManager, databaseCollectionManager DatabaseCollectionManager) {
		super ("Add","",": Add new element to the collection");
		this.collectionManager = collectionManager ;
		this.DatabaseCollectionManager = DatabaseCollectionManager;
	}
	/**
     * Add an organization to the collection
     * 
     * @param argument The argument is the user input.
     * @return statement of the command.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
    	try {
            System.out.println(objectArgument);
            if (!argument.isEmpty() || objectArgument == null) throw new WrongAmountOfElements();
            humanRaw humanRaw = (humanRaw) objectArgument;
            collectionManager.addToCollection(DatabaseCollectionManager.insertHuman(humanRaw, user));
            responseOutputer.appendln("Human Being added successfully!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (ClassCastException exception) {
            responseOutputer.appenderror(exception + "The object passed by the client is invalid!");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database! leu leu");
        }
        return false;
    }

}
