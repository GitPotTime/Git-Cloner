package fr.pottime.gitcloner.enums;

/**
 * They are all account type
 *
 * @author Antoine
 * @version 1.0
 */
public enum AccountType {

    /**
     * An simple user account
     */
    USER("users"),

    /**
     * An organisation account
     */
    ORG("orgs"),

    /**
     * An special account
     */
    OTHER("other");

    private String type;

    AccountType(String type) {
        this.type = type;
    }

    /**
     * Get the type of account
     *
     * @return The type of account
     */
    public String getType() {
        return type;
    }

}
