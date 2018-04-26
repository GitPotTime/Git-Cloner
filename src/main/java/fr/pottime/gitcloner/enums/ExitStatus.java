package fr.pottime.gitcloner.enums;

/**
 * All possible exit are listed here.
 *
 * @author Antoine
 * @version 1.0.2
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
     * The account doesn't exists
     */
    ACCOUNT_DONT_EXISTS(4),

    /**
     * GitCloner need to be updated.
     */
    GITCLONER_NOT_UP_TO_DATE(5),

    /**
     * An folder must be deleted
     */
    FOLDER_MUST_BE_DELETED(6),

    /**
     * An error occurred..
     */
    ERROR(7);


    /**
     * The status
     */
    int status;

    ExitStatus(int status) {
        this.status = status;
    }

    /**
     * Exit GitClonerMain with the status {@link #status}
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
     * @return {@code true} if GitCloner exit with
     * an error or return {@code false} if GitCloner
     * without errors.
     */
    public boolean isError() {
        return status != 0;
    }

}
