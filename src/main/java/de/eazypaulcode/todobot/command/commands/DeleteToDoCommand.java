package de.eazypaulcode.todobot.command.commands;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.command.CommandObject;
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

public class DeleteToDoCommand extends CommandObject {

    public DeleteToDoCommand(ToDoBot todoBot) {
        super(todoBot);
    }

    @Override
    public CommandData getCommand() {
        return Commands.slash("deletetodo", "Deletes a To-Do-list")
                .addOption(OptionType.STRING, "uuid", "The uuid of the To-Do-list", true);
    }

    @Override
    public void respond(SlashCommandInteractionEvent event) {
        event.replyEmbeds(new EmbedBuilder().setTitle("<a:loadingDownload:806936664521703435>").appendDescription("Loading...").build()).setEphemeral(true).queue(interactionHook -> {
        OptionMapping uuid = event.getOption("uuid");
            if (uuid == null) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Please provide a uuid for the To-Do-list to delete it")
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
                        .setTitle(":no_entry: | **Error while deleting**")
                        .appendDescription("You are not the owner of this To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            todoBot.getToDoManager().unregisterList(list.get());
            interactionHook.editOriginalEmbeds(new EmbedBuilder()
                    .setTitle(":wastebasket: | **To-Do-list deleted**")
                    .appendDescription("The To-Do-list has been deleted")
                    .appendDescription("\n**UUID:** " + "*" + list.get().getUuid() + "*")
                    .setTimestamp(Instant.now())
                    .setColor(Color.GREEN)
                    .build()
            ).queue();
        });
        }
}
