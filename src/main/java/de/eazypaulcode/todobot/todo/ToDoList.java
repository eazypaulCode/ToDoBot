package de.eazypaulcode.todobot.todo;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoList {

    private String name;
    private final long owner;
    private final String uuid;

    private final List<ToDoEntry> entries;

    public ToDoList(String name, long owner, String uuid, List<ToDoEntry> entries) {
        this.name = name;
        this.owner = owner;
        this.uuid = uuid;
        this.entries = entries;
    }

    public ToDoList(String name, User user) {
        this.name = name;
        this.owner = user.getIdLong();
        this.uuid = randomUUID();
        this.entries = new ArrayList<>();
    }

    public ToDoList(String name, long owner) {
        this.name = name;
        this.owner = owner;
        this.uuid = randomUUID();
        this.entries = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public long getOwner() {
        return owner;
    }

    public String getUuid() {
        return uuid;
    }

    public List<ToDoEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public boolean addEntry(ToDoEntry entry) {
        if (entries.stream().anyMatch(todoEntry -> todoEntry.getCriteria().equals(entry.getCriteria()))) {
            return false;
        }
        entries.add(entry);
        return true;
    }

    public void removeEntry(ToDoEntry entry) {
        entries.remove(entry);
    }

    public void removeEntry(int index) {
        entries.remove(index - 1);
    }

    public MessageEmbed getEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(":bookmark: | " + "**" + name + "**");
        builder.appendDescription("**UUID:** " + "*" + uuid + "*");
        builder.appendDescription("\n\n");
        if (entries.isEmpty()) {
            builder.appendDescription("*No entries added yet. Go ahead and add some!*");
        }
        if (!entries.isEmpty()) {
            for (ToDoEntry entry : entries) {
                if (entry.hasEmoji()) {
                    builder.appendDescription("**-** " + entry.getEmoji() + " " + entry.getCriteria() + "\n");
                    continue;
                }
                builder.appendDescription("**-** " +entry.getCriteria() + "\n");
            }
        }
        builder.setTimestamp(Instant.now());
        builder.setFooter("Total of " + entries.size() + " entries");
        return builder.build();
    }

    public MessageEmbed getCreatedEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(":bookmark: | " + "**To-Do-list created**");
        builder.appendDescription("**Name:** " + "*" + name + "*");
        builder.appendDescription("\n**UUID:** " + "*" + uuid + "*");
        builder.setColor(Color.GREEN);
        builder.setTimestamp(Instant.now());
        return builder.build();
    }

    public String randomUUID() {
        String uuid = "";
        for (int i = 0; i < 8; i++) {
            uuid += (char) (Math.random() * 26 + 'a');
        }
        return uuid;
    }
}
