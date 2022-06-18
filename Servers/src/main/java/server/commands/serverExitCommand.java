package server.commands;

import common.exceptions.WrongAmountOfElements;
import common.interaction.User;
import server.utility.responseOutputer;

public class serverExitCommand extends AbstractCommand {
	public serverExitCommand() {
        super("Server_Exit", "", ": Save collection and shut down the server");
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument, User user) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            //collectionManager.saveCollection();
            responseOutputer.appendln("Server completed successfully!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }

}
