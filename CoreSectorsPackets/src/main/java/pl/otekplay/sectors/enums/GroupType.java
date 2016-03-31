package pl.otekplay.sectors.enums;

public enum GroupType {
    PLAYER(0, ""),
    VIP(1, "&8[&3{LEVEL}&8] [&6VIP&8] &e{NICK} &2» &7{MSG}"),
    YOUTUBER(2, "&8[&3{LEVEL}&8] [&fY&4T&8] &6{NICK} &8» &7{MSG}"),
    HELPER(3, "&8[&9H&8] &9{NICK} &2» &3{MSG}"),
    MODERATOR(4, "&8[&2M&8] &2{NICK} &8» &a{MSG}"),
    ADMIN(5, "&8[&4A&8] &4{NICK} &8» &c{MSG}"),
    OWNER(6, "[Owner]"),
    ROOT(10, "&8[&4R&8] &4{NICK} &8» &6&l{MSG}"),
    CONSOLE(999, "[CONSOLE]");

    private final int level;
    private final String prefix;

    GroupType(int level, String prefix) {
        this.level = level;
        this.prefix = prefix;
    }

    public int getLevel() {
        return level;
    }

    public boolean can(GroupType type) {
        return level >= type.getLevel();
    }

    public String getPrefix() {
        return prefix;
    }

}