package fr.pottime.gitcloner;

import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.enums.ExitStatus;
import fr.pottime.gitcloner.manager.AccountManager;
import fr.pottime.gitcloner.manager.RepositoriesManager;
import fr.pottime.gitcloner.repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * @author Antoine
 * @version 1.0
 */
public class GitCloner {

    /**
     * The version of GitCloner
     */
    public static final String VERSION = "1.0.0";

    /**
     * The logger
     */
    public static final Logger logger;

    /**
     * The type of account choose by the user.
     */
    public static AccountType accountType;

    static {
        logger = Logger.getLogger(GitCloner.class.getName());
    }

    /**
     * Entry point
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1.2");
        if (args.length < 2) {
            // The user must put more arguments
            logger.warning("** DO NOT OPEN AN ISSUES ON GITHUB **");
            logger.warning("You must put more arguments!");
            logger.warning("Read USAGE.MD if you need help.");
            Runtime.getRuntime().exit(ExitStatus.ARGS_REQUIRED.getStatus());
            return;
        }

        // Set the account type
        switch (args[0]) {
            case "user":
                GitCloner.accountType = AccountType.USER;
                break;
            case "org":
                GitCloner.accountType = AccountType.ORG;
                break;
            default:
                GitCloner.accountType = AccountType.OTHER;
                break;
        }
        if (GitCloner.accountType == AccountType.OTHER) GitCloner.badType(args[0]);

        Account account = AccountManager.createAccount(args[1], GitCloner.accountType);
        for (Repository repo : RepositoriesManager.getRepositories(account)) {
            if (!repo.getOwner().getUsername().equals(account.getUsername())) continue;
            account.addRepository(repo);
        }

        GitCloner.logger.info("Cloning " + account.getUsername() + " repositories..");
        for (Repository repo : account.getRepositories()) {
            String command = "git clone " + repo.getHttpsUrl();
            try {
                GitCloner.logger.info("Cloning the repo " + repo.getName() + " with the command " + command);
                Process process = GitUtils.runCommand(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) System.out.println(line);
                GitCloner.logger.info("The repo " + repo.getName() + " are been cloned!");
            } catch (IOException e) {
                GitCloner.logger.severe("** OPEN AN ISSUES ON GITHUB **");
                GitCloner.logger.severe("Can't run the command " + command);
                GitCloner.logger.severe("Error are here:");
                e.printStackTrace();
                Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
                return;
            }
        }
        GitCloner.logger.info("All user repositories are been cloned!");
        Runtime.getRuntime().exit(ExitStatus.SUCCESS.getStatus());
    }

    /**
     * Print to the console an message for
     * the user and exit GitCloner.
     *
     * @param typeEntered The type entered by the user.
     */
    private static void badType(String typeEntered) {
        logger.severe("** DO NOT OPEN AN ISSUES ON GITHUB **");
        logger.severe("The type " + typeEntered + " is not correct");
        logger.severe("You can use only 2 type.");
        logger.severe("User for clone all repos of an user");
        logger.severe("And org for clone all repos of an organisation.");
        Runtime.getRuntime().exit(ExitStatus.BAD_ARGUMENT.getStatus());
    }
}
