package fr.pottime.gitcloner.manager;

import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.account.OrganisationAccount;
import fr.pottime.gitcloner.account.UserAccount;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.repository.Repository;

import java.util.ArrayList;

/**
 * Manage your accounts!
 *
 * @author Antoine
 * @version 1.0
 */
public class AccountManager {

    /**
     * Create the account {@code name} with the type {@code accountType}
     *
     * @param name        The name of the account
     * @param accountType The type of the account
     * @return The account created.
     */
    public static Account createAccount(String name, AccountType accountType) {
        return accountType == AccountType.USER ?
                new UserAccount(name, new ArrayList<Repository>(), accountType) :
                new OrganisationAccount(name, new ArrayList<Repository>(), accountType);
    }

}
