package server.commands;
import common.exceptions.databaseHandling;
import common.exceptions.UserIsNotFound;
import common.exceptions.WrongAmountOfElements;
import common.interaction.User;
import server.utility.database.databaseUserManager;
import server.utility.responseOutputer;

/**
 * Command 'login'. Allows the user to login.
 */
public class LoginCommand extends AbstractCommand {
    private databaseUserManager DatabaseUserManager;

    public LoginCommand(databaseUserManager DatabaseUserManager) {
        super("login", "", "internal command");
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
            if (DatabaseUserManager.checkUserByUsernameAndPassword(user)) responseOutputer.appendln("User " +
                    user.getUsername() + " authorized.");
            else throw new UserIsNotFound();
            return true;
        } catch (WrongAmountOfElements exception) {
            responseOutputer.appendln("This is an internal command!");
        } catch (ClassCastException exception) {
            responseOutputer.appenderror("The object passed by the client is invalid!");
        } catch (databaseHandling exception) {
            responseOutputer.appenderror("An error occurred while accessing the database!");
        } catch (UserIsNotFound exception) {
            responseOutputer.appenderror("Wrong username or password!");
        }
        return false;
    }
}
