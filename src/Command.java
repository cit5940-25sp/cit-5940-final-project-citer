/**
 * The command interface helps implement the command design pattern.
 */
public interface Command {
    /**
     * Each core feature class implements the execute method
     * which is called by the chatbot during runtime.
     */
    public void execute();
}
