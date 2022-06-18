package server.commands;

import server.utility.CollectionManager;
import server.utility.responseOutputer;
import common.exceptions.WrongAmountOfElements;
import common.interaction.User;

public class SaveCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
	
	public SaveCommand (CollectionManager collectionManager) {
		super ("Save","", ": Save collection to the file") ;
		this.collectionManager = collectionManager ;
	}
	/**
     * Save the collection to the file
     * 
     * @param argument The argument passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();

            //collectionManager.saveCollection();
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Type '" + getName() + "' to use this command");
        }
        return false;
    }

}
