package fr.pottime.gitcloner.account;

import fr.pottime.gitcloner.GitUtils;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * An user account
 *
 * @author Antoine
 * @version 1.0
 */
@AllArgsConstructor
@Getter
public class UserAccount implements Account {

    @Setter
    private String username;
    private Collection<Repository> repositories;
    private AccountType accountType;

    @Override
    public void removeRepository(Repository repository) {
        for (Repository repo : repositories) {
            if (repo.getName().equals(repository.getName())) {
                if (repo.getOwner().getUsername().equals(repository.getOwner().getUsername())) {
                    repositories.remove(repository);
                    break;
                }
            }
        }
    }

    @Override
    public void addRepository(Repository repository) {
        for (Repository repo : repositories)
            if (repo.getName().equals(repository.getName()))
                if (repo.getOwner().getUsername().equals(repository.getOwner().getUsername())) return;
        repositories.add(repository);
    }

    @Override
    public boolean isOwnedRepository(Repository repository) {
        return repository.getOwner().getUsername().equals(this.getUsername());
    }

    /**
     * Check if the account exists
     *
     * @return {@code true} if the account exists or {@code false}
     * if the account doesn't exists or an error occurred.
     */
    @Override
    public boolean isExists() {
        return GitUtils.isAccountExists(this);
    }
}
