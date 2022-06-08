package de.eazypaulcode.todobot.listener;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.message.EmbedDefaults;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DirectMessageListener extends ListenerAdapter {

    private final ToDoBot todoBot;

    public DirectMessageListener(ToDoBot todoBot) {
        this.todoBot = todoBot;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()) {
            return;
        }
        if (!(event.getChannel() instanceof PrivateChannel channel)) {
            return;
        }
        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getMessage().getContentRaw().startsWith("!")) {
            channel.sendMessageEmbeds(EmbedDefaults.messageCommand()).queue();
            return;
        }

        channel.sendMessageEmbeds(EmbedDefaults.help()).queue();
    }
}
