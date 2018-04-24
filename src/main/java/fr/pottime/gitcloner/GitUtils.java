package fr.pottime.gitcloner;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import fr.pottime.gitcloner.account.Account;
import fr.pottime.gitcloner.enums.AccountType;
import fr.pottime.gitcloner.enums.ExitStatus;
import fr.pottime.gitcloner.repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * They are utils method.
 *
 * @author Antoine
 * @version 1.0.3
 */
public class GitUtils {

    /**
     * Read the content of an file or an website
     *
     * @param reader The reader used
     * @return The content of an file or an website.
     */
    public static String readContent(Reader reader) {
        BufferedReader r = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = r.readLine()) != null) sb.append(line);
            return sb.toString();
        } catch (IOException e) {
            if (line == null) return sb.toString();
            GitCloner.logger.severe("** OPEN AN ISSUES ON GITHUB **");
            GitCloner.logger.severe("An error occurred while reading the content of an file or an website.");
            GitCloner.logger.severe("Errors are here:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
            return sb.toString();
        }
    }

    /**
     * Parse the string {@code str}
     *
     * @param str The string to parse
     * @return The string {@code str} parsed or an
     * empty JSONObject if an error occurred.
     */
    public static JsonArray parseJsonString(String str) {
        JsonParser parser = new JsonParser();
        try {
            return (JsonArray) parser.parse(str);
        } catch (JsonSyntaxException e) {
            GitCloner.logger.severe("** OPEN AN ISSUES ON GITHUB **");
            GitCloner.logger.severe("Can't parse the string " + str);
            GitCloner.logger.severe("Errors are here:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
            return new JsonArray();
        } catch (ClassCastException ignored) {
            return new JsonArray();
        }
    }

    /**
     * Convert an string to an {@link URL#URL(String)}
     *
     * @param url The URL to convert
     * @return The URL
     */
    public static URL toURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            GitCloner.logger.severe("** OPEN AN ISSUES ON GITHUB **");
            GitCloner.logger.severe("The url " + url + " is malformed.");
            GitCloner.logger.severe("Please open an issues with these errors:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.MALFORMED_URL.getStatus());
            return null;
        }
    }

    /**
     * Check if the account {@code account} exists.
     *
     * @param account The account
     * @return {@code true} if the account exists else return {@code false}
     * if the account doesn't exists or if an error occurred.
     */
    public static boolean isAccountExists(Account account) {
        return GitUtils.isAccountExists(account.getUsername(), account.getAccountType());
    }

    /**
     * Check if the account with the username {@code username} exists.
     *
     * @param username    The name of the account
     * @param accountType The type of account
     * @return {@code true} if the account exists else return {@code false}
     */
    public static boolean isAccountExists(String username, AccountType accountType) {
        URL url = GitUtils.toURL("https://api.github.com/" +
                accountType.getType() + "/" + username);
        try {
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout((int) TimeUnit.MILLISECONDS.toSeconds(20000));
            conn.setReadTimeout((int) TimeUnit.MILLISECONDS.toSeconds(30000));
            conn.setDoOutput(true);
            conn.addRequestProperty("User-Agent", "GitCloner-" + GitCloner.VERSION);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String content = GitUtils.readContent(reader);
            return GitUtils.parseJsonString(content).size() > 0;
        } catch (IOException e) {
            GitCloner.logger.severe("** READ THE MESSAGE FOR SEE IF YOU NEED TO OPEN AN ISSUES **");
            GitCloner.logger.severe("Try to connect to the url " + url.getHost() + " and if you can't. It's because the website is closed else");
            GitCloner.logger.severe("open an issues on github with they errors:");
            e.printStackTrace();
            Runtime.getRuntime().exit(ExitStatus.ERROR.getStatus());
            return false;
        }
    }

    /**
     * Get the number of repositories created
     *
     * @param array The JSON Array
     * @return The number of repositories created
     */
    public static int getRepositoriesSize(JsonArray array) {
        return array.size();
    }

    /**
     * List all repositories by {@code account} and
     * print him with {@code printer}
     *
     * @param account The account
     * @param printer The printer
     */
    public static void listRepositories(Account account, Logger printer) {
        GitCloner.logger.info("The user " + account.getUsername() + " have " + account.getRepositories().size() + " repositories");
        GitCloner.logger.info("They repositories are here:");
        for (Repository repo : account.getRepositories()) {
            printer.fine(repo.getName() + ":");
            printer.fine("httpsUrl= " + repo.getHttpsUrl());
            printer.fine("sshUrl= " + repo.getSshUrl());
            printer.fine("public: " + (repo.isPublicRepository() ? "Yes" : "No"));
        }
        GitCloner.logger.info("All repositories by " + account.getUsername() + " are listed!");
    }

    /**
     * Execute the command {@code command} in the cmd.
     *
     * @param command The command to execute
     * @return The process
     * @throws IOException If an I / O exception occur.
     */
    public static Process runCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        return builder.start();
    }
}
