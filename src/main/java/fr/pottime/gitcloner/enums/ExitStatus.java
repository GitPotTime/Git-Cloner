package fr.pottime.gitcloner.enums;

/**
 * All possible exit are listed here.
 */
public enum ExitStatus {

    /**
     * The application is an success.
     */
    SUCCESS(0),

    /**
     * The user must put more arguments.
     */
    ARGS_REQUIRED(1),

    /**
     * The user entered an bad argument
     */
    BAD_ARGUMENT(2),

    /**
     * An url is malformed.
     */
    MALFORMED_URL(3),

    /**
     * An error occurred..
     */
    ERROR(4);


    /**
     * The status
     */
    int status;

    ExitStatus(int status) {
        this.status = status;
    }

    /**
     * Exit GitCloner with the status {@link #status}
     * using {@link Runtime#exit(int)}
     */
    public void exit() {
        Runtime.getRuntime().exit(this.getStatus());
    }

    /**
     * Get the exit status
     *
     * @return The exit status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Check if GitCloner exit with an error
     *
     * @return {@code true} if GitCloner
     */
    public boolean isError() {
        return status != 0;
    }

}
