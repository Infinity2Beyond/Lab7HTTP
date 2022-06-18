package server.commands;

import server.utility.CollectionManager;
import common.data.humanbeing;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.WrongAmountOfElements;
import common.exceptions.databaseHandling;
import common.exceptions.manualDatabaseEdit;
import common.exceptions.permissionDenied;
import common.interaction.User;
import server.utility.responseOutputer;
import server.utility.database.databaseCollectionManager;

public class ClearCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
    private databaseCollectionManager DatabaseCollectionManager;
	public ClearCommand(CollectionManager collectionManager, databaseCollectionManager DatabaseCollectionManager) {
		super ("Clear","", ": Clear the collection") ;
		this.collectionManager = collectionManager ;
		this.DatabaseCollectionManager = DatabaseCollectionManager;
	}
	/**
     * Clear the collection
     * 
     * @param argument The argument is the string that is passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmpty();
            for (humanbeing human: collectionManager.getCollection()) {
            	if(!human.getOwner().equals(user)) throw new permissionDenied();
            	if(!DatabaseCollectionManager.checkHumanUserId(human.getId(), user)) throw new manualDatabaseEdit();
            }
            DatabaseCollectionManager.clearCollection();
            collectionManager.clearCollection(); 
            DatabaseCollectionManager.resetAllHumanId();
            responseOutputer.appendln("The collection is clear!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appendln("The collection is already empty!");
		} catch (permissionDenied exception) {
            responseOutputer.appenderror("You don't have the right to execute this command!");
            responseOutputer.appendln("Objects owned by other users are read-only.");
        } catch (manualDatabaseEdit exception) {
            responseOutputer.appenderror("A direct database change has occurred!");
            responseOutputer.appendln("Restart the client to avoid possible errors.");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database!");
        } return false;
    }
}
