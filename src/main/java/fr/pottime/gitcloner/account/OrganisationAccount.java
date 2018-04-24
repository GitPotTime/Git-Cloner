package fr.pottime.gitcloner.account;

import fr.pottime.gitcloner.GitUtils;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.repository.Repository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * An organisation account
 *
 * @author Antoine
 * @version 1.1
 */

@AllArgsConstructor
@Getter
public class OrganisationAccount implements Account {

    @Setter
    private String username;
    private Collection<Repository> repositories;
    private AccountType accountType;

    /**
     * Don't use this constructor.
     * Some values aren't set.
     *
     * @deprecated Some value aren't set
     */
    @Deprecated
    private OrganisationAccount() {
        throw new IllegalStateException("You can't use this constructor!");
    }

    /**
     * Remove the repository {@code repository} from the list {@link #repositories}
     *
     * @param repository The repository to remove
     */
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

    /**
     * Add the repository {@code repository} to the list {@link #repositories}
     *
     * @param repository The repository to add
     */
    @Override
    public void addRepository(Repository repository) {
        for (Repository repo : repositories) {
            if (repo.getName().equals(repository.getName())) {
                if (repo.getOwner().getUsername().equals(repository.getOwner().getUsername())) {
                    repositories.add(repository);
                    break;
                }
            }
        }
    }

    /**
     * Check if the repository {@code repository}
     *
     * @param repository The repository to check
     * @return {@code true} if the repository is owned
     */
    @Override
    public boolean isOwnedRepository(Repository repository) {
        return repository.getOwner().getUsername().equals(this.getUsername());
    }

    /**
     * Get if the user with the username {@code username} exists.
     *
     * @return {@code true} if the account exists or {@code false}
     * if the user doesn't exists or an error occurred.
     */
    @Override
    public boolean isExists() {
        return GitUtils.isAccountExists(this);
    }
}
