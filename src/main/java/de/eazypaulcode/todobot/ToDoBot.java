package de.eazypaulcode.todobot;

import de.eazypaulcode.todobot.command.CommandManager;
import de.eazypaulcode.todobot.listener.DirectMessageListener;
import de.eazypaulcode.todobot.logging.Logger;
import de.eazypaulcode.todobot.message.Key;
import de.eazypaulcode.todobot.todo.ToDoManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class ToDoBot {

    private final Logger logger;

    private final String token;

    private ToDoManager toDoManager;
    private CommandManager commandManager;

    private JDA bot;

    public ToDoBot(String token) {
        this.token = token;
        this.logger = new Logger("ToDoBot");
        init();
    }

    private void init() {
        logger.info(Key.from("bot.starting"));
        commandManager = new CommandManager();
        try {
            JDABuilder builder = JDABuilder.createDefault(token);
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.addEventListeners(commandManager);
            builder.addEventListeners(new DirectMessageListener(this));
            builder.setBulkDeleteSplittingEnabled(false);
            bot = builder.build().awaitReady();
        } catch (Exception e) {
            logger.critical(Key.from("bot.invalid-token"));
            logger.debug("Exception: " + e.getMessage());
            System.exit(1);
        }
        bot.getPresence().setPresence(OnlineStatus.ONLINE, false);
        bot.getPresence().setPresence(Activity.listening("for your TODO-lists"), false);
        toDoManager = new ToDoManager();
        commandManager.load(this);
    }

    public JDA getBot() {
        return bot;
    }

    public ToDoManager getToDoManager() {
        return toDoManager;
    }
}
