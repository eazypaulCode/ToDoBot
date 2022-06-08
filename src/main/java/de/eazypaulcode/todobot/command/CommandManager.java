package de.eazypaulcode.todobot.command;

import de.eazypaulcode.todobot.ToDoBot;
import de.eazypaulcode.todobot.command.commands.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    private final List<CommandObject> commands;

    public CommandManager() {
        this.commands = new ArrayList<>();
    }

    public void load(ToDoBot todoBot) {
        commands.add(new CreateToDoCommand(todoBot));
        commands.add(new ToDoListCommand(todoBot));
        commands.add(new DeleteToDoCommand(todoBot));
        commands.add(new AddEntryCommand(todoBot));
        commands.add(new ListToDosCommand(todoBot));
        commands.add(new RemoveEntryCommand(todoBot));
        commands.add(new HelpCommand(todoBot));

        CommandListUpdateAction updateAction = todoBot.getBot().updateCommands();

        for (CommandObject commandObject : commands) {
            updateAction = updateAction.addCommands(commandObject.getCommand());
        }

        updateAction.queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commands.stream()
                .filter(commandObject -> commandObject.getCommand().getName().equalsIgnoreCase(event.getInteraction().getName()))
                .forEach(commandObject -> commandObject.respond(event));
    }
}
