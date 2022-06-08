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

public class CreateToDoCommand extends CommandObject {

    public CreateToDoCommand(ToDoBot todoBot) {
        super(todoBot);
    }

    @Override
    public CommandData getCommand() {
        return Commands.slash("createtodo", "Creates a new To-Do-list")
                .addOption(OptionType.STRING, "name", "The name of the To-Do-list", true);
    }

    @Override
    public void respond(SlashCommandInteractionEvent event) {
        event.replyEmbeds(new EmbedBuilder().setTitle("<a:loadingDownload:806936664521703435>").appendDescription("Loading...").build()).setEphemeral(true).queue(interactionHook -> {
            OptionMapping name = event.getOption("name");
            if (name == null) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Please provide a name for the To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            ToDoList list = todoBot.getToDoManager().registerNewList(name.getAsString(), event.getUser());
            if (list == null) {
                interactionHook.editOriginalEmbeds(new EmbedBuilder()
                        .setTitle(":no_entry: | **Error**")
                        .appendDescription("Could not create the To-Do-list")
                        .setTimestamp(Instant.now())
                        .setColor(Color.RED)
                        .build()
                ).queue();
                return;
            }
            interactionHook.editOriginalEmbeds(list.getCreatedEmbed()).queue();
        });
    }
}
