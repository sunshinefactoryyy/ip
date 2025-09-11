package bobbot.command;

import bobbot.exception.BobException;

/**
 * Interface for handling different types of commands in BobBot.
 * Each command type should have its own implementation of this interface.
 */
@FunctionalInterface
public interface CommandHandler {
    /**
     * Handles the execution of a specific command.
     *
     * @param arguments the arguments passed to the command
     * @return the response message from executing the command
     * @throws BobException if there's an error specific to BobBot operations
     * @throws Exception if there's any other error during command execution
     */
    String handle(String[] arguments) throws Exception;
}