package at.thejano.advancedchat.util;

public class ColorUtils {

    /**
     * Returns the last color code from the
     * given String. {@link org.bukkit.ChatColor}
     * @param message The String containing color codes
     * @return The last color code.
     */
    public static String getLastColorCode(String message) {
        String colorCode = "§f";
        for (int i = 0; i < message.length() - 1; i++) {
            if (message.charAt(i) == '§' && message.charAt(i + 1) != ' ') {
                colorCode = message.substring(i, i + 1);
            }
        }
        return colorCode;
    }
}
