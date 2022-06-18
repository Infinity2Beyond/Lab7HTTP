package server.commands;

import common.exceptions.databaseHandling;
import common.exceptions.UserIsAlreadyExisted;
import common.exceptions.WrongAmountOfElements;
import common.interaction.User;
import server.utility.database.databaseUserManager;
import server.utility.responseOutputer;

/**
 * Command 'register'. Allows the user to register.
 */
public class RegisterCommand extends AbstractCommand {
    private databaseUserManager DatabaseUserManager;

    public RegisterCommand(databaseUserManager DatabaseUserManager) {
        super("Register", "", ": internal command");
        this.DatabaseUserManager = DatabaseUserManager;
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
            if (DatabaseUserManager.insertUser(user)) responseOutputer.appendln("User " +
                    user.getUsername() + " registered.");
            else throw new UserIsAlreadyExisted();
            return true;
        } catch (WrongAmountOfElements exception) {
            responseOutputer.appendln("This is an internal command!");
        } catch (ClassCastException exception) {
            responseOutputer.appenderror("The object passed by the client is invalid!");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database!");
        } catch (UserIsAlreadyExisted exception) {
            responseOutputer.appenderror("User " + user.getUsername() + " already exists!");
        }
        return false;
    }
}