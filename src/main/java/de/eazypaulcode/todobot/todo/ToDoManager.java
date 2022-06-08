package de.eazypaulcode.todobot.todo;

import de.eazypaulcode.todobot.logging.Logger;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ToDoManager {

    private final List<ToDoList> toDoLists;
    private final Logger logger;

    public ToDoManager() {
        this.toDoLists = new ArrayList<>();
        this.logger = new Logger("ToDoManager");
    }

    public ToDoList registerNewList(String name, User owner) {
        return registerNewList(new ToDoList(name, owner));
    }

    public ToDoList registerNewList(ToDoList toDoList) {
        if (getByUUID(toDoList.getUuid()).isPresent()) {
            logger.error("To-Do-list with UUID " + toDoList.getUuid() + " already exists and can not be registered.");
            return null;
        }
        toDoLists.add(toDoList);
        logger.fine("To-do-list " + replaceGermanLetters(toDoList.getName()) + " with UUID " + toDoList.getUuid() + " registered.");
        return toDoList;
    }

    public void unregisterList(ToDoList toDoList) {
        if (getByUUID(toDoList.getUuid()).isEmpty()) {
            logger.error("To-Do-list with UUID " + toDoList.getUuid() + " does not exist and can not be unregistered.");
            return;
        }
        toDoLists.remove(toDoList);
        logger.info("To-do-list " + replaceGermanLetters(toDoList.getName()) + " with UUID " + toDoList.getUuid() + " unregistered.");
    }

    public Optional<ToDoList> getByUUID(String uuid) {
        return toDoLists.stream().filter(toDoList -> toDoList.getUuid().equals(uuid)).findFirst();
    }

    public List<ToDoList> getToDoLists() {
        return Collections.unmodifiableList(toDoLists);
    }

    public List<ToDoList> getToDoLists(long userId) {
        return toDoLists.stream().filter(toDoList -> toDoList.getOwner() == userId).toList();
    }

    public List<ToDoList> getToDoLists(User user) {
        return getToDoLists(user.getIdLong());
    }

    public String replaceGermanLetters(String text) {
        text = text.replace("ä", "ae");
        text = text.replace("ö", "oe");
        text = text.replace("ü", "ue");
        text = text.replace("ß", "ss");
        return text;
    }

}
