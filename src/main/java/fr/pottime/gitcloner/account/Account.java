package fr.pottime.gitcloner.account;

import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.repository.Repository;

import java.util.Collection;

/**
 * Github account
 *
 * @author Antoine
 * @version 1.0
 */
public interface Account {

    String getUsername();

    void setUsername(String username);

    Collection<Repository> getRepositories();

    void removeRepository(Repository repository);

    void addRepository(Repository repository);

    boolean isOwnedRepository(Repository repository);

    AccountType getAccountType();

    boolean isExists();

}
