package to2.dice.messaging;

/**
 * @author Fan
 * @version 0.1
 */
public class Response {
    public enum Type {
        SUCCESS, FAILURE
    }

    //region Fields
    /** Type of the response  */
    public Type type;
    /** Message associated with response, i.e. error message when failure */
    public String message;
    //endregion

    //region Constructors
    /**
     * Constructs response with type and message set to null
     */
    public Response() {}

    /**
     * Constructs new response with specified type
     * @param type Type of the constructed response
     */
    public Response(Type type) {
        this();
        this.type = type;
    }

    /**
     * Constructs response with specified type and message
     * @param type Type of the constructed response
     * @param message Message of the constructed response
     */
    public Response(Type type, String message) {
        this(type);
        this.message = message;
    }
    //endregion

    //region Public methods
    /**
     * Checks if response has type SUCCESS
     * @return Returns true if response has type SUCCESS, false otherwise
     */
    public boolean isSuccess() {
        return type == Type.SUCCESS;
    }
    //endregion
}