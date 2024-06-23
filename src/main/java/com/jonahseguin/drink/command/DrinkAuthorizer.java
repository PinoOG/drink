package com.jonahseguin.drink.command;

import com.jonahseguin.drink.util.ComponentHelper;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

import static com.jonahseguin.drink.command.DrinkCommandService.notAuthorized;

@Getter
@Setter
public class DrinkAuthorizer {

    private static final Component noPermissionMessage = ComponentHelper.format(notAuthorized);

    public boolean isAuthorized(@Nonnull CommandSender sender, @Nonnull DrinkCommand command, @Nonnull String label) {
        if (command.getPermission() == null || command.getPermission().isEmpty()) {
            return true;
        }
        if (sender.hasPermission(command.getPermission())) {
            return true;
        }

        final String message = command.getPermissionMessage();
        Component component = message != null && !message.isEmpty() ? ComponentHelper.format(message) : noPermissionMessage;
        sender.sendMessage(component);
        return false;
    }

}
