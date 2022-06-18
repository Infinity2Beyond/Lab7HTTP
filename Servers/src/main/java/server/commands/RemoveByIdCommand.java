package server.commands;

import common.exceptions.WrongAmountOfElements;
import common.exceptions.databaseHandling;
import common.exceptions.manualDatabaseEdit;
import common.exceptions.permissionDenied;
import common.interaction.User;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.HumanNotFound;
import common.data.humanbeing;
import server.utility.CollectionManager;
import server.utility.responseOutputer;
import server.utility.database.databaseCollectionManager;

public class RemoveByIdCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
    private databaseCollectionManager DatabaseCollectionManager;


	public RemoveByIdCommand (CollectionManager collectionManager, databaseCollectionManager DatabaseCollectionManager) {
		super("Remove_By_ID","", ": Remove items from collection by id");
		this.collectionManager = collectionManager ;
		this.DatabaseCollectionManager = DatabaseCollectionManager;
	}
	/**
     * Remove an organization from the collection
     * 
     * @param argument The argument that is passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();

            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmpty();
            long id = Long.parseLong(argument);
            humanbeing HumanBeingToRemove = collectionManager.getById(id);
            if (HumanBeingToRemove == null)
                throw new HumanNotFound();
            if (!HumanBeingToRemove.getOwner().equals(user)) throw new permissionDenied();
            if (!DatabaseCollectionManager.checkHumanUserId(HumanBeingToRemove.getId(), user)) throw new manualDatabaseEdit();
            DatabaseCollectionManager.deleteHumanById(id);
            collectionManager.removeFromCollection(HumanBeingToRemove);
            //collectionManager.regenerateID();
            responseOutputer.appendln("Humanbeing successfully removed!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("The collection is empty!");
        } catch (HumanNotFound exception) {
        	responseOutputer.appenderror("There is no humanbeing with this ID in the collection!");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database!");
        } catch (permissionDenied exception) {
            responseOutputer.appenderror("You don't have the right to execute this command!");
            responseOutputer.appendln("Objects owned by other users are read-only.");
        } catch (manualDatabaseEdit exception) {
            responseOutputer.appenderror("A direct database change has occurred!");
            responseOutputer.appendln("Restart the client to avoid possible errors.");
        }
        return false;
    }

}
