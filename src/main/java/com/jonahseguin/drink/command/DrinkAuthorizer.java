package com.jonahseguin.drink.command;

import com.jonahseguin.drink.provider.ProviderMessage;
import com.jonahseguin.drink.util.ComponentHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

@Getter
@Setter
public class DrinkAuthorizer {

    public boolean isAuthorized(@Nonnull CommandSender sender, @Nonnull DrinkCommand command, @Nonnull String label) {
        if (command.getPermission() == null || command.getPermission().isEmpty()) {
            return true;
        }
        if (sender.hasPermission(command.getPermission())) {
            return true;
        }

        final String message = (providerMessages.containsKey(ProviderMessage.UNAUTHORIZED))
                ? providerMessages.get(ProviderMessage.UNAUTHORIZED)
                : ProviderMessage.UNAUTHORIZED.msg();
        sender.sendMessage(ComponentHelper.format(message));
        return false;
    }

}
