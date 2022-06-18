package server.commands;

import common.exceptions.WrongAmountOfElements;
import common.interaction.User;
import server.utility.responseOutputer;

public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("Exit","", ": Turn off the cilent and save the file");
    }

    /**
     * Executes to exit
     * 
     * @param argument The argument passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument, User user) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            return true;            
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        }
        return false;
    }
}
