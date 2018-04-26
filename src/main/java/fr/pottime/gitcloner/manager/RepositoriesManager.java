package fr.pottime.gitcloner.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.pottime.gitcloner.GitCloner;
import fr.pottime.gitcloner.GitClonerMain;
import fr.pottime.gitcloner.GitUtils;
import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.enums.ExitStatus;
import fr.pottime.gitcloner.repository.PrivateRepository;
import fr.pottime.gitcloner.repository.PublicRepository;
import fr.pottime.gitcloner.repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Manage your repositories!
 *
 * @author Antoine
 * @version 1.0
 */
public class RepositoriesManager {

    /**
     * Create an repository
     *
     * @param name     The name of the repository.
     * @param owner    The owner of the repository.
     * @param isPublic Make {@code true} if you want
     *                 the repository public or
     *                 make {@code false} if you
     *                 want the repository private.
     * @return The repository created.
     */
    public static Repository createRepository(String name, Account owner, boolean isPublic) {
        String httpsUrl = "https://github.com/" + owner.getUsername() +
                "/" + name + ".git";
        String sshUrl = "git@github.com:" + owner.getUsername() +
                "/" + name + ".git";

        return isPublic ?
                new PublicRepository(name, httpsUrl, sshUrl, owner) :
                new PrivateRepository(name, httpsUrl, sshUrl, owner);
    }

    /**
     * Get all repositories created by {@code owner}
     *
     * @param owner Get all repositories
     *              created by this owner
     * @return All repositories created
     * by the account {@code owner}
     */
    public static Collection<Repository> getRepositories(Account owner) {
        Collection<Repository> repos = new ArrayList<>();
        URL url = GitUtils.toURL("https://api.github.com/" +
                owner.getAccountType().getType() + "/" + owner.getUsername() + "/repos");

        URLConnection conn;
        try {
            conn = url.openConnection();
            conn.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(20));
            conn.setReadTimeout((int) TimeUnit.SECONDS.toMillis(30));
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", "GitCloner/" + GitClonerMain.VERSION);
        } catch (IOException e) {
            GitCloner.logger.severe("** OPEN AN ISSUES ON GTIHUB **");
            GitCloner.logger.severe("Can't open the connection for the url " + url);
            GitCloner.logger.severe("Error are here:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
            return repos;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            GitCloner.logger.severe("** OPEN AN ISSUES ON GITHUB **");
            GitCloner.logger.severe("Can't open the stream for the url " + url);
            GitCloner.logger.severe("Error are here:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
            return repos;
        }

        String content = GitUtils.readContent(reader);
        JsonArray array = GitUtils.parseJsonString(content);
        int length = GitUtils.getRepositoriesSize(array);
        // Add repos to the list
        for (int i = 0; i < length; ++i)
            repos.add(createRepository(((JsonObject) array.get(i)).get("name").getAsString(), owner, true));
        return repos;
    }
}
