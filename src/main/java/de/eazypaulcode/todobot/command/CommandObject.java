package de.eazypaulcode.todobot.command;

import de.eazypaulcode.todobot.ToDoBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class CommandObject {

    protected final ToDoBot todoBot;

    public CommandObject(ToDoBot todoBot) {
        this.todoBot = todoBot;
    }

    public abstract CommandData getCommand();

    public abstract void respond(SlashCommandInteractionEvent event);

}
