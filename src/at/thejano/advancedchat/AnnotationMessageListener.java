package at.thejano.advancedchat;

import at.dalex.playtime.PlayTimeAPI;
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

                String hoverText = "§3Online seit§7: " + PlayTimeAPI.getSessionPlayTime(player.getUniqueId())
                        + "\n§9Gesamte Spielzeit§7: " + PlayTimeAPI.getTotalPlayTime(player.getUniqueId());
                textComponent.setText("§c@" + userNameWithColor + lastColorCode + " ");
                textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
            }

            finalComponent.addExtra(textComponent);
            lastColorCode = ColorUtils.getLastColorCode(word);
        }

        sendMessageToAllPlayers(finalComponent);
        e.setCancelled(true);
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
