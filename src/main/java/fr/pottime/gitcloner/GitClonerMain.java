package fr.pottime.gitcloner;

import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.enums.ExitStatus;
import fr.pottime.gitcloner.manager.AccountManager;
import fr.pottime.gitcloner.manager.RepositoriesManager;
import fr.pottime.gitcloner.repository.Repository;

/**
 * Clone all repositories from an Github user or an Github organisation!
 *
 * @author Antoine
 * @version 1.3
 */
public class GitClonerMain {

    /**
     * The version of GitCloner
     */
    public static final String VERSION = "1.1.5";

    /**
     * The type of account choose by the user.
     */
    private static AccountType accountType;

    /**
     * Entry point
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1.2");
        if (args.length < 2) {
            // The user must put more arguments
            GitCloner.logger.warning("** DO NOT OPEN AN ISSUES ON GITHUB **\n" +
                    "You must put more arguments!\n" +
                    "Read USAGE.MD if you need help.");
            Runtime.getRuntime().exit(ExitStatus.ARGS_REQUIRED.getStatus());
            return;
        }

        // Set the account type
        switch (args[0]) {
            case "user":
                GitClonerMain.accountType = AccountType.USER;
                break;
            case "org":
                GitClonerMain.accountType = AccountType.ORG;
                break;
            default:
                GitClonerMain.accountType = AccountType.OTHER;
                break;
        }
        if (GitClonerMain.accountType == AccountType.OTHER) GitClonerMain.badType(args[0]);

        Account account = AccountManager.createAccount(args[1], GitClonerMain.accountType);
        for (Repository repo : RepositoriesManager.getRepositories(account)) {
            if (!repo.getOwner().getUsername().equals(account.getUsername())) continue;
            account.addRepository(repo);
        }

        GitCloner cloner = new GitCloner(account);
        boolean success = cloner.start();
        if (!success) Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
        Runtime.getRuntime().exit(ExitStatus.SUCCESS.getStatus());
    }

    /**
     * Print to the console an message for
     * the user and exit GitClonerMain.
     *
     * @param typeEntered The type entered by the user.
     */
    private static void badType(String typeEntered) {
        GitCloner.logger.severe("** DO NOT OPEN AN ISSUES ON GITHUB **\n" +
                "The type " + typeEntered + " is not correct\n" +
                "You can use only 2 type." +
                "User for clone all repos of an user\n" +
                "And org for clone all repos of an organisation.\n");
        Runtime.getRuntime().exit(ExitStatus.BAD_ARGUMENT.getStatus());
    }
}
