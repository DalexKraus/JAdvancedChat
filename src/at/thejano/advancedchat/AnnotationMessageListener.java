package at.thejano.advancedchat;

import at.dalex.playtime.PlayTimeAPI;
import at.thejano.absentapi.JAbsentAPI;
import at.thejano.advancedchat.util.ColorUtils;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AnnotationMessageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String startMessage = e.getPlayer().getDisplayName() + " " + e.getMessage();
        String[] splitMessage = startMessage.split(" ");
        String lastColorCode = "§f";

        TextComponent finalComponent = new TextComponent();

        for (String word : splitMessage) {
            TextComponent textComponent = new TextComponent();
            textComponent.setText(word + " ");

            if (word.contains("@")) {
                String userName = word.replaceAll("@", "");
                String userNameWithColor;
                Player player = Bukkit.getServer().getPlayer(userName);
                if (player != null && player.isOnline()) {
                    userNameWithColor = "§e" + userName;
                }
                else {
                    finalComponent.addExtra(textComponent);
                    continue;
                }

                int playtimeSession = PlayTimeAPI.getSessionPlayTime(player.getUniqueId());
                int playtimeTotal = PlayTimeAPI.getTotalPlayTime(player.getUniqueId());

                String[] sessionSegments = createTimeString(playtimeSession).split(":");
                String[] totalTimeSegments = createTimeString(playtimeTotal).split(":");

                String sessionString    = sessionSegments[0]    + " Stunden " + sessionSegments[1]   + " Minuten " + sessionSegments[2]     + " Sekunden";
                String totalTimeString  = totalTimeSegments[0]  + " Stunden " + totalTimeSegments[1] + " Minuten " + totalTimeSegments[2]   + " Sekunden";

                int afkTime = JAbsentAPI.getAFKTime(player.getUniqueId());
                String afkString = afkTime > 20 ? "§8AFK seit §e" + afkTime + " §8Sekunden" : "";

                String hoverText = "§3Online seit§7: " + sessionString
                        + "\n§9Gesamte Spielzeit§7: " + totalTimeString;

                if (afkString != "") {
                    hoverText += "\n\n" + afkString;
                }

                textComponent.setText("§c@" + userNameWithColor + lastColorCode + " ");
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
            }

            finalComponent.addExtra(textComponent);
            lastColorCode = ColorUtils.getLastColorCode(word);
        }

        sendMessageToAllPlayers(finalComponent);
        e.setCancelled(true);
    }

    private String createTimeString(int passedSeconds) {
        int hours = passedSeconds / 3600;
        int minutes = (passedSeconds % 3600) / 60;
        int seconds = passedSeconds % 60;

        return String.format("%d:%d:%d", hours, minutes, seconds);
    }

    /**
     * Sends a {@link TextComponent} to all Players
     * who are online on the server.
     * @param component The {@link TextComponent} which should be sent
     */
    private void sendMessageToAllPlayers(TextComponent component) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.spigot().sendMessage(component);
        }
    }
}
