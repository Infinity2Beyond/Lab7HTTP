package server.utility;

import common.interaction.User;
import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;

import java.util.concurrent.Callable;

public class requestHandle  implements Callable<response> {
	private final CommandManager commandManager;
	private final request request;
	
	public requestHandle(request request, CommandManager commandManager) {
		this.commandManager = commandManager;
		this.request = request;
	}	
	/**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
	@Override
    public response call() {
    	 User hashedUser = new User(
                 request.getUser().getUsername(),
                 PasswordHasher.hashPassword(request.getUser().getPassword())
         );
        commandManager.addToHistory(request.getCommandName(), request.getUser());
        responseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument(), hashedUser);
        
        return new response(responseCode, responseOutputer.getAndClear());
    }
	


    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private responseCode executeCommand(String command, String commandStringArgument,
            Object commandObjectArgument, User user) {
    	switch (command) {
    	case "":
            break;
        case "HELP":
            if (!commandManager.Help(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "INFO":
            if (!commandManager.Info(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "SHOW":
            if (!commandManager.Show(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "ADD":
            if (!commandManager.Add(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            return responseCode.ACCEPTED_201_ACCEPTED;
        case "UPDATE_BY_ID":
            if (!commandManager.Update(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            return responseCode.ACCEPTED_201_ACCEPTED;
        case "REMOVE_BY_ID":
            if (!commandManager.RemoveById(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "CLEAR":
            if (!commandManager.Clear(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        /*case "SAVE":
            if (!commandManager.Save(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;*/
        case "EXECUTE_SCRIPT":
            if (!commandManager.ExecuteScript(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            return responseCode.ACCEPTED_201_ACCEPTED;
        case "FILTER_LESS_THAN_IMPACT_SPEED":
            if (!commandManager.FilterLessThanImpactSpeed(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_404_NOT_FOUND;
            break;
        case "PRINT_DESCENDING":
            if (!commandManager.PrintDescending(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "HISTORY":
            if (!commandManager.History(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "SHUFFLE":
            if (!commandManager.Shuffle(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "REORDER":
            if (!commandManager.Reorder(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "MIN_BY_IMPACT_SPEED":
            if (!commandManager.MinByImpactSpeed(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_404_NOT_FOUND;
            break;          
        case "EXIT":
            if (!commandManager.Exit(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_501_NOT_IMPLEMENTED;
            break;
        case "SERVER_EXIT":
            if (!commandManager.serverExit(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_404_NOT_FOUND;
            return responseCode.SERVER_EXIT;
        case "login":
            if (!commandManager.login(commandStringArgument, commandObjectArgument, user))
                return responseCode.CLIENT_ERROR_401_METHOD_NOT_AUTHORIZED;
            break;
        case "register":
            if (!commandManager.register(commandStringArgument, commandObjectArgument, user))
                return responseCode.SERVER_ERROR_404_NOT_FOUND;
            return responseCode.ACCEPTED_201_ACCEPTED;
        default:
        	responseOutputer.appendln("Command  '" + command + "' not found. Type 'Help' for help.");
            return responseCode.SERVER_ERROR_404_NOT_FOUND;
    	
    }
    	return responseCode.OK_200_OK;
    }

}
