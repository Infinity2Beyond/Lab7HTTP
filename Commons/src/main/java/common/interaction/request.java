package common.interaction;

public class request  {
	private String commandName;
    private String commandStringArgument;
    private humanRaw commandObjectArgument;
    private User user;
    private processingCode code;

    public request(String commandName, String commandStringArgument, humanRaw commandObjectArgument, User user, processingCode code) {
        this.commandName = commandName;
        this.commandStringArgument = commandStringArgument;
        this.commandObjectArgument = commandObjectArgument;
        this.user = user;
        this.code = code;
    }

    public void setCommandObjectArgument(humanRaw commandObjectArgument) {
		this.commandObjectArgument = commandObjectArgument;
	}

	public processingCode getCode() {
		return code;
	}

	public request(String commandName, String commandStringArgument, User user, processingCode code) {
        this(commandName, commandStringArgument, null, user,code);
    }

    public request(User user) {
        this("", "", user,null);
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public humanRaw getCommandObjectArgument() {
        return commandObjectArgument;
    }
    /**
     * @return User of command.
     */
    public User getUser() {
        return user;
    }

    /**
     * @return Is this request empty.
     */
    public boolean isEmpty() {
        return commandName.isEmpty() && commandStringArgument.isEmpty() && commandObjectArgument == null;
    }

    @Override
    public String toString() {
        return "Request[" + commandName + ", " + commandStringArgument + ", " + commandObjectArgument + "]";
    }
}
