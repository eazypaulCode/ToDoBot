package de.eazypaulcode.todobot.command.commands;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.command.CommandObject;
import de.eazypaulcode.todobot.todo.ToDoEntry;
import de.eazypaulcode.todobot.todo.ToDoList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.time.Instant;
import java.util.Optional;

public class RemoveEntryCommand extends CommandObject {


    public RemoveEntryCommand(ToDoBot todoBot) {
        super(todoBot);
    }

    @Override
    public CommandData getCommand() {
        return Commands.slash("removeentry", "Removes an entry from a To-Do-list")
                .addOption(OptionType.STRING, "uuid", "The uuid of the To-Do-list", true)
                .addOption(OptionType.INTEGER, "index", "The index (position) from the entry to remove", true);
    }

    @Override
    public void respond(SlashCommandInteractionEvent event) {
        event.replyEmbeds(new EmbedBuilder().setTitle("<a:loadingDownload:806936664521703435>").appendDescription("Loading...").build()).setEphemeral(true).queue(interactionHook -> {
            OptionMapping uuid = event.getOption("uuid");
            OptionMapping index = event.getOption("index");
            if (uuid == null || index == null) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Please provide a uuid and index for the To-Do-entry")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            Optional<ToDoList> list = todoBot.getToDoManager().getByUUID(uuid.getAsString());
            if (list.isEmpty()) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Could not find the To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            if (list.get().getOwner() != event.getUser().getIdLong()) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("You are not the owner of this To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            if (index.getAsInt() > list.get().getEntries().size() || index.getAsInt() < 1) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("The index is out of bounds")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            ToDoEntry entry = list.get().getEntries().get(index.getAsInt() - 1);
            list.get().removeEntry(entry);
            interactionHook.editOriginalEmbeds(new EmbedBuilder()
                    .setTitle(":white_check_mark: | **Success**")
                    .appendDescription("Removed entry from To-Do-list")
                    .appendDescription("\n**Criteria:** " + "*" + entry.getCriteria() + "*")
                    .appendDescription("\n**Emoji**: " + (entry.getEmoji().isEmpty() ? "None" : entry.getEmoji()))
                    .setTimestamp(Instant.now())
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
    });
}
}
