package games.indigo.safarinets.api;

import org.bukkit.ChatColor;

public enum NetType {

    SINGLE_USE(ChatColor.GRAY + "Single Use"), MULTI_USE(ChatColor.GRAY + "Multi Use");

    private final String type;

    NetType(String type) {
        this.type = type;
    }

    public String getNetName() {
        return type;
    }

    public static NetType getNetType(String from) {
        for(NetType type : values()) {
            if(type.name().equalsIgnoreCase(from)) {
                return type;
            }
        }
        return null;
    }

}
