package fr.pottime.gitcloner.repository;

import fr.pottime.gitcloner.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PrivateRepository implements Repository {

    private String name;
    private String httpsUrl;
    private String sshUrl;
    private Account owner;

    @Override
    public boolean isPublicRepository() {
        return true;
    }

}
