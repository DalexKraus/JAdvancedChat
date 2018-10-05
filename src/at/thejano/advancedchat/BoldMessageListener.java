package at.thejano.advancedchat;

import at.thejano.advancedchat.util.ColorUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BoldMessageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        do {
            int startIndex = message.indexOf("*");
            message = message.replaceFirst("\\*", "");
            if (startIndex != -1) {
                int stopIndex = message.indexOf("*");
                if (stopIndex != -1) {
                    message = message.replaceFirst("\\*", "");

                    String boldText = message.substring(startIndex, stopIndex);
                    String textBehindBoldText = message.substring(0, startIndex);

                    String lastSegmentColorCode = ColorUtils.getLastColorCode(textBehindBoldText);

                    String finalBoldText = "§l" + boldText + lastSegmentColorCode;
                    String lastSegment = message.substring(stopIndex, message.length());
                    message = textBehindBoldText + finalBoldText + lastSegment;
                }
            }
        } while (message.contains("*"));


        e.setMessage(message);
    }

}
