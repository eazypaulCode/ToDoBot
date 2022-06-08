package de.eazypaulcode.todobot.command.commands;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.command.CommandObject;
import de.eazypaulcode.todobot.todo.ToDoEntry;
import de.eazypaulcode.todobot.todo.ToDoList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.time.Instant;
import java.util.Optional;

public class AddEntryCommand extends CommandObject {


    public AddEntryCommand(ToDoBot todoBot) {
        super(todoBot);
    }

    @Override
    public CommandData getCommand() {
        return Commands.slash("addentry", "Adds a new entry to a To-Do-list")
                .addOption(OptionType.STRING, "uuid", "The uuid of the To-Do-list", true)
                .addOption(OptionType.STRING, "criteria", "The criteria of the entry to add", true)
                .addOption(OptionType.STRING, "emoji", "The emoji of the entry to add");
    }

    @Override
    public void respond(SlashCommandInteractionEvent event) {
        event.replyEmbeds(new EmbedBuilder().setTitle("<a:loadingDownload:806936664521703435>").appendDescription("Loading...").build()).setEphemeral(true).queue(interactionHook -> {
            OptionMapping uuid = event.getOption("uuid");
            OptionMapping criteria = event.getOption("criteria");
            OptionMapping emoji = event.getOption("emoji");
            if (uuid == null || criteria == null) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Please provide the uuid, criteria and optionally a emoji of the entry to add")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            Optional<ToDoList> list = todoBot.getToDoManager().getByUUID(uuid.getAsString());
            if (list.isEmpty()) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error while adding entry**")
                        .appendDescription("Could not find the To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            if (list.get().getOwner() != event.getUser().getIdLong()) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error while adding entry**")
                        .appendDescription("You are not the owner of this To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            ToDoEntry entry = new ToDoEntry(criteria.getAsString(), emoji == null ? "" : emoji.getAsString());
            if (list.get().getEntries().size() >= 25) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error while adding entry**")
                        .appendDescription("This To-Do-list is full *(max. 25 entries)*")
                        .setColor(Color.RED)
                        .setTimestamp(Instant.now())
                        .build()
                ).queue();
                return;
            }
            list.get().addEntry(entry);
            interactionHook.editOriginalEmbeds(new EmbedBuilder()
                    .setTitle(":white_check_mark: | **Entry added**")
                    .appendDescription("Entry added to To-Do-list")
                    .appendDescription("\nEntry: " + entry.getCriteria())
                    .appendDescription("\nEmoji: " + (entry.hasEmoji() ? entry.getEmoji() : "*No emoji*"))
                    .setFooter("New total of " + list.get().getEntries().size() + " entries")
                    .setTimestamp(Instant.now())
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
        });
    }
}
