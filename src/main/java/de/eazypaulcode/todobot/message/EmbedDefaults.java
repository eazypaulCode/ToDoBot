package de.eazypaulcode.todobot.message;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class EmbedDefaults {

    public static MessageEmbed help() {
        return new EmbedBuilder()
                .setTitle(":bulb: | **Help**")
                .appendDescription("*Below is a list of all available commands*")
                .addField("*/createtodo*", "Creates a To-Do-list", true)
                .addField("*/todolist*", "Shows a To-Do-list", true)
                .addField("*/addentry*", "Adds an entry to a To-Do-list", true)
                .addField("*/removeentry*", "Removes an entry from a To-Do-list", true)
                .addField("*/deletetodo*", "Deletes a To-Do-list", true)
                .addField("*/help*", "Shows this help message", true)
                .addField("*/listtodos*", "Shows a list of all your To-Do-lists", true)
                .setColor(Color.YELLOW)
                .setTimestamp(Instant.now()).build();
    }

    public static MessageEmbed messageCommand() {
        return new EmbedBuilder()
                .setTitle(":warning: | **Message Command detected**")
                .appendDescription("It seems like you tried to use a message command.")
                .appendDescription("\nAs they are outdated, please use a slash command instead.")
                .setColor(Color.YELLOW)
                .setTimestamp(Instant.now()).build();
    }

}
