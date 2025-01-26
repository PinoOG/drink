package com.jonahseguin.drink.exception;

import com.jonahseguin.drink.util.ComponentHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CommandExitMessage extends Exception {

    private Component component;

    public CommandExitMessage(@Nullable String message) {
        super(message);
    }

    public CommandExitMessage(@Nonnull Component component) {
        super("");
        this.component = component;
    }

    public void print(CommandSender sender) {
        if (getMessage() == null || getMessage().isEmpty()) {
            if (component != null) {
                sender.sendMessage(component);
            }
            return;
        }
        sender.sendMessage(ComponentHelper.format(getMessage()));
    }
}
