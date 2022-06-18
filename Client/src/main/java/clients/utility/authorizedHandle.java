package clients.utility;

import common.interaction.request;
import common.utility.outPuter;
import common.exceptions.CommandUsage;
import common.interaction.User;
import common.interaction.processingCode;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class authorizedHandle {
	private final String loginCommand = "login";
    private final String registerCommand = "register";

    private final Scanner userScanner;
    public authorizedHandle(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * Handle user authentication.
     *
     * @return Request of user.
     */
    public request handle() {
    	authorizedAsker authAsker = new authorizedAsker(userScanner);
        String command = authAsker.askQuestion("Do you already have an account?") ? loginCommand : registerCommand;
        User user = new User(authAsker.askLogin(), authAsker.askPassword());
        processingCode code = processCommand(command.replace("Command", ""), "");
        return new request(command, "", user, code);
    }
    private processingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return processingCode.ERROR;
                case "login":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "register":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    return processingCode.POST;
                default:
                    outPuter.println("Command '" + command + "' not found. Type 'help' for help.");
                    return processingCode.ERROR;
            }
        } catch (CommandUsage exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            outPuter.println("Usage: '" + command + "'");
            return processingCode.ERROR;
        }
        return processingCode.GET;
    }
}
