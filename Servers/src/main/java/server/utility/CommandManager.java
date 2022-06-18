package server.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import server.commands.*;
import common.exceptions.HistoryIsEmpty;
import common.interaction.User;


public class CommandManager {
	private final int COMMAND_HISTORY_SIZE = 16;

    private String[] commandHistory = new String[COMMAND_HISTORY_SIZE];
    private List<Command> commands = Collections.synchronizedList(new ArrayList<>());
    private Command HelpCommand;
    private Command InfoCommand;
    private Command ShowCommand;
    private Command AddCommand;
    private Command UpdateIdCommand;
    private Command RemoveByIdCommand;
    private Command ClearCommand;
    private Command ExitCommand;
    private Command ExecuteScriptCommand;
    private Command FilterLessThanImpactSpeedCommand;
    private Command PrintDescendingCommand;
    private Command HistoryCommand;
    private Command ShuffleCommand;
    private Command ReorderCommand;
    private Command MinByImpactSpeedCommand;
    private Command serverExitCommand;
    private Command loginCommand;
    private Command registerCommand;

    
    public CommandManager(Command HelpCommand, Command InfoCommand, Command ShowCommand, Command AddCommand, Command UpdateIdCommand,
            Command RemoveByIdCommand, Command ClearCommand,    Command ExitCommand, Command ExecuteScriptCommand,
            Command FilterLessThanImpactSpeedCommand, Command PrintDescendingCommand, Command HistoryCommand, Command ShuffleCommand,
            Command ReorderCommand, Command MinByImpactSpeedCommand, Command serverExitCommand, Command loginCommand,
            Command registerCommand) {
    	this.HelpCommand = HelpCommand;
        this.InfoCommand = InfoCommand;
        this.ShowCommand = ShowCommand;
        this.AddCommand = AddCommand;
        this.UpdateIdCommand = UpdateIdCommand;
        this.RemoveByIdCommand = RemoveByIdCommand;
        this.ClearCommand = ClearCommand;
        this.ExitCommand = ExitCommand;
        this.ExecuteScriptCommand = ExecuteScriptCommand;
        this.FilterLessThanImpactSpeedCommand = FilterLessThanImpactSpeedCommand;
        this.PrintDescendingCommand = PrintDescendingCommand;
        this.HistoryCommand = HistoryCommand;
        this.ShuffleCommand = ShuffleCommand;
        this.ReorderCommand = ReorderCommand;
        this.MinByImpactSpeedCommand = MinByImpactSpeedCommand;
        this.serverExitCommand = serverExitCommand;
        this.loginCommand = loginCommand;
        this.registerCommand = registerCommand;
        
        commands.add(InfoCommand);
        commands.add(ShowCommand);
        commands.add(AddCommand);
        commands.add(UpdateIdCommand);
        commands.add(RemoveByIdCommand);
        commands.add(ClearCommand);
        commands.add(ExitCommand);
        commands.add(ExecuteScriptCommand);
        commands.add(FilterLessThanImpactSpeedCommand);
        commands.add(PrintDescendingCommand);
        commands.add(HistoryCommand);
        commands.add(ShuffleCommand);
        commands.add(ReorderCommand);
        commands.add(MinByImpactSpeedCommand);
        commands.add(serverExitCommand);
        
    }
    /**
     * @return The command history.
     */
    public String[] getCommandHistory() {
        return commandHistory;
    }

    /**
     * @return List of manager's commands.
     */
    public List<Command> getCommands() {
    	
        return commands;
    }
    /**
     * Adds command to command history.
     * @param commandToStore Command to add.
     */
    public void addToHistory(String commandToStore, User user) { 
    	

        for (Command command : commands) {
            if (command.getName().toUpperCase().split(" ")[0].equals(commandToStore) ) {
                for (int i = COMMAND_HISTORY_SIZE-1; i>0; i--) {
                    commandHistory[i] = commandHistory[i-1];
                }
                commandHistory[0] = commandToStore.toLowerCase() + " (" + user.getUsername() +")";
            }
        }
    }
    
    /**
     * Prints info about the all commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Help(String argument, Object objectArgument, User user) {
        if (HelpCommand.execute(argument, objectArgument, user)) {
            for (Command command : commands) {
            	responseOutputer.appendtable(command.getName(), command.getDescription());
            }
            return true;
        } else 
        	return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Info(String argument, Object objectArgument, User user) {
        return InfoCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Show(String argument, Object objectArgument, User user) {
        return ShowCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Add(String argument, Object objectArgument, User user) {
        return AddCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Update(String argument, Object objectArgument, User user) {
        return UpdateIdCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean RemoveById(String argument, Object objectArgument, User user) {
        return RemoveByIdCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Clear(String argument, Object objectArgument, User user) {
        return ClearCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    /*public boolean Save(String argument, Object objectArgument) {
        return SaveCommand.execute(argument, objectArgument);
    }*/

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Exit(String argument, Object objectArgument, User user) {
        return ExitCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean ExecuteScript(String argument, Object objectArgument, User user) {
        return ExecuteScriptCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean FilterLessThanImpactSpeed(String argument, Object objectArgument, User user) {
        return FilterLessThanImpactSpeedCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean PrintDescending(String argument, Object objectArgument, User user) {
        return PrintDescendingCommand.execute(argument, objectArgument, user);
    }

    /**
     * Prints the history of used commands.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public synchronized boolean History(String argument, Object objectArgument, User user) {
        if (HistoryCommand.execute(argument, objectArgument, user)) {
            try {
            	int S = 0;
            	responseOutputer.appendln("Last used commands:");
                for (int i=1; i<commandHistory.length; i++) {
                		if (commandHistory[i] != null) {
                			responseOutputer.appendln(" " + i + ":"  + commandHistory[i]);
                			S += 1; 
                		}
                		if (S == 0) throw new HistoryIsEmpty();
                }      
                return true;
            } catch (HistoryIsEmpty exception) {
            	responseOutputer.appendln("Not a single command has been used yet!");
            }
        }
        return false;
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Shuffle(String argument, Object objectArgument, User user) {
        return ShuffleCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean Reorder(String argument, Object objectArgument, User user) {
        return ReorderCommand.execute(argument, objectArgument, user);
    }

    /**
     * Executes needed command.
     * @param argument Its argument.
     * @return Command exit status.
     */
    public boolean MinByImpactSpeed(String argument, Object objectArgument, User user) {
        return MinByImpactSpeedCommand.execute(argument, objectArgument, user);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @return Command exit status.
     */
    public boolean serverExit(String stringArgument, Object objectArgument, User user) {
        return serverExitCommand.execute(stringArgument, objectArgument, user);
    }
    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean login(String stringArgument, Object objectArgument, User user) {
        return loginCommand.execute(stringArgument, objectArgument, user);
    }

    /**
     * Executes needed command.
     *
     * @param stringArgument Its string argument.
     * @param objectArgument Its object argument.
     * @param user           User object.
     * @return Command exit status.
     */
    public boolean register(String stringArgument, Object objectArgument, User user) {
        return registerCommand.execute(stringArgument, objectArgument, user);
    }

    @Override
    public String toString() {
        return "CommandManager ";
    }
   

}
