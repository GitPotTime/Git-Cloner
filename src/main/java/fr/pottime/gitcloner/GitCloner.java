package fr.pottime.gitcloner;

import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.enums.ExitStatus;
import fr.pottime.gitcloner.manager.AccountManager;
import fr.pottime.gitcloner.repository.Repository;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Clone all repositories created by an Github user or an Github organisation!
 *
 * @author Antoine
 * @version 1.0
 */
@Getter
public class GitCloner {

    /**
     * The logger used to print information and errors.
     */
    public static final Logger logger;
    private Account account;

    static {
        logger = Logger.getLogger(GitCloner.class.getName());
    }

    /**
     * Don't use this constructor.
     *
     * @see GitCloner#GitCloner(Account) Use this constructor!
     * @see GitCloner#GitCloner(String, AccountType) Or this one!
     * @deprecated Some values can't be used.
     */
    @Deprecated
    private GitCloner() {
        throw new IllegalStateException("You can't use this constructor!");
    }

    /**
     * Create an instance of GitCloner with the
     * account {@code account}
     *
     * @param account The account
     */
    public GitCloner(Account account) {
        this.account = account;
    }

    /**
     * Create an instance of GitCloner and
     * Create an account with the name {@code name} and
     * the type of the account will be {@code accountType}
     *
     * @param name        The name of the account
     * @param accountType The type of the account
     */
    public GitCloner(String name, AccountType accountType) {
        this(AccountManager.createAccount(name, accountType));
    }

    /**
     * Clone all repositories for the account {@link #account}.
     * GitCloner use the https-url to clone the repositories.
     *
     * @return {@code true} if the no error occurred and all repositories
     * are been cloned! Else, if an error occurred or an repositories aren't
     * cloned. Return {@code false}
     * @see Account#getRepositories() Clone all repositories from here.
     */
    public boolean start() {
        if (!this.isAccountExists()) {
            logger.severe("** DO NOT OPEN AN ISSUES ON GITHUB **");
            logger.severe("The account " + account.getUsername() + " doesn't exists on Github.");
            return false;
        }
        logger.info("Cloning " + account.getUsername() + " repositories..");
        File accountFile = new File(account.getUsername());
        if (!accountFile.mkdir()) {
            logger.warning("** DO NOT OPEN AN ISSUES ON GITHUB **\n" +
                    "Please, delete the folder " + this.account.getUsername() + " after, retry.");
            Runtime.getRuntime().exit(ExitStatus.FOLDER_MUST_BE_DELETED.getStatus());
            return false;
        }
        for (Repository repo : account.getRepositories()) {
            String goToOwnerFileCommand = "cd " + accountFile.getPath();
            String cloneCommand = "git clone " + repo.getHttpsUrl();
            StringBuilder cmdBuilder = new StringBuilder();
            cmdBuilder.append(goToOwnerFileCommand).append(" && ").append(cloneCommand);
            try {
                logger.info("Cloning the repo " + repo.getName() + " with the command " + cloneCommand);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                Process process = GitUtils.runCommand(cmdBuilder.toString());
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) System.out.println(line);
                logger.info("The repo " + repo.getName() + " are been cloned!");
            } catch (IOException e) {
                logger.severe("** OPEN AN ISSUES ON GITHUB **\n" +
                        "Can't run the command: " + cmdBuilder.toString() + "\n" +
                        "Error are here:");
                e.printStackTrace();
                Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
                return false;
            }
        }
        logger.info("All user repositories are been cloned!");
        return true;
    }

    /**
     * Get who many the account {@link #account} created repositories.
     *
     * @return The number of repositories created
     */
    public int getRepositoriesSize() {
        return account.getRepositories().size();
    }

    /**
     * Get the repositories created by
     * the account {@link #account}
     *
     * @return All repositories created by
     * the account {@link #account}
     */
    public Collection<Repository> getRepositories() {
        return account.getRepositories();
    }

    /**
     * Get the type of account for the
     * account {@link #account}
     *
     * @return The type of account for the
     * account {@link #account}
     */
    public AccountType getAccountType() {
        return account.getAccountType();
    }

    /**
     * Get if the account {@link #account} exists
     * in Github
     *
     * @return {@code true} if the account
     * {@link #account} exists in github
     * else, return {@code false}
     */
    public boolean isAccountExists() {
        return account.isExists();
    }

}
