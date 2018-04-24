package fr.pottime.gitcloner.repository;

import fr.pottime.gitcloner.account.Account;

/**
 * An repository
 */
public interface Repository {

    String getName();

    void setName(String name);

    String getHttpsUrl();

    void setHttpsUrl(String httpsUrl);

    String getSshUrl();

    void setSshUrl(String sshUrl);

    boolean isPublicRepository();

    Account getOwner();

    void setOwner(Account account);

}
