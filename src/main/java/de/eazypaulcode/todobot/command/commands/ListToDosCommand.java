package de.eazypaulcode.todobot.command.commands;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.command.CommandObject;
import de.eazypaulcode.todobot.todo.ToDoList;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class ListToDosCommand extends CommandObject {

    public ListToDosCommand(ToDoBot todoBot) {
        super(todoBot);
    }

    @Override
    public CommandData getCommand() {
        return Commands.slash("listtodos", "Lists all your To-Do-lists");
    }

    @Override
    public void respond(SlashCommandInteractionEvent event) {
        event.replyEmbeds(new EmbedBuilder().setTitle("<a:loadingDownload:806936664521703435>").appendDescription("Loading...").build()).setEphemeral(true).queue(interactionHook -> {
            List<ToDoList> toDoLists = todoBot.getToDoManager().getToDoLists(event.getUser());
            if (toDoLists.isEmpty()) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error while listing To-Do-lists**")
                        .appendDescription("You have no To-Do-lists")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle(":bookmark: | **To-Do-lists**");
            builder.appendDescription("\n");
            for (ToDoList list : toDoLists) {
                builder.appendDescription("**-** " + list.getName() + " | **UUID**: " + list.getUuid() + "\n");
            }
            builder.setTimestamp(Instant.now());
            builder.setColor(Color.GREEN);
            interactionHook.editOriginalEmbeds(builder.build()).queue();
        });
    }
}
