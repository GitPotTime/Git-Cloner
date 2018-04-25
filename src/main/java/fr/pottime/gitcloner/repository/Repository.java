package fr.pottime.gitcloner.repository;

import fr.pottime.gitcloner.account.Account;

/**
 * An repository
 *
 * @author Antoine
 * @version 1.1
 */
public interface Repository {

    /**
     * Get the name of the repository
     *
     * @return The name of the repository
     */
    String getName();

    /**
     * Set the name of the repository to {@code name}
     *
     * @param name The new name of the repository
     */
    void setName(String name);

    /**
     * Get the httpsUrl of the repository
     *
     * @return The httpsUrl of the repository
     */
    String getHttpsUrl();

    /**
     * Set the name of the repository to {@code name}
     *
     * @param httpsUrl The new name of the repository
     */
    void setHttpsUrl(String httpsUrl);

    /**
     * Get the ssh url of the repository
     *
     * @return The ssh url of the repository
     */
    String getSshUrl();

    /**
     * Set the ssh url of the repository
     *
     * @param sshUrl The new ssh url of the repository
     */
    void setSshUrl(String sshUrl);

    /**
     * Get if the repository is public
     *
     * @return {@code true} if the repository is public, else return {@code false}
     */
    boolean isPublicRepository();

    /**
     * Get the owner of the repository
     *
     * @return The owner of the repository
     */
    Account getOwner();

    /**
     * Set the owner of the repository
     *
     * @param account The new owner of the repository
     */
    void setOwner(Account account);

}
