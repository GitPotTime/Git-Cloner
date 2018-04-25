package fr.pottime.gitcloner.repository;

import fr.pottime.gitcloner.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * An public repository
 *
 * @author Antoine
 * @version 1.0
 */
@AllArgsConstructor
@Getter
@Setter
public class PublicRepository implements Repository {

    private String name;
    private String httpsUrl;
    private String sshUrl;
    private Account owner;

    @Override
    public boolean isPublicRepository() {
        return true;
    }

}
