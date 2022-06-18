package server.commands;

import common.interaction.User;

public interface Command {
	String getDescription();
    String getName();
    String getUsage();
    boolean execute(String commandStringArgument, Object commandObjectArgument, User user);
}
