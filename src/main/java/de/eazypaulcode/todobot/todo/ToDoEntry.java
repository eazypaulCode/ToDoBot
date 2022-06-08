package de.eazypaulcode.todobot.todo;

public class ToDoEntry {

    private String emoji;
    private String criteria;

    public ToDoEntry(String criteria, String emoji) {
        this.emoji = emoji;
        this.criteria = criteria;
    }

    public ToDoEntry(String criteria) {
        this("", criteria);
    }

    public String getCriteria() {
        return criteria;
    }

    public String getEmoji() {
        return emoji;
    }

    public void editEmoji(String emoji) {
        this.emoji = emoji;
    }

    public boolean hasEmoji() {
        return !emoji.isEmpty();
    }
}
