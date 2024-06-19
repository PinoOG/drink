package com.jonahseguin.drink.provider.spigot;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import com.jonahseguin.drink.provider.ProviderMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

public class PlayerSenderProvider extends DrinkProvider<Player> {

    public static final PlayerSenderProvider INSTANCE = new PlayerSenderProvider();

    @Override
    public boolean doesConsumeArgument() {
        return false;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    @Nullable
    public Player provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        if (arg.isSenderPlayer()) {
            return arg.getSenderAsPlayer();
        }
        final String message = (providerMessages.containsKey(ProviderMessage.PLAYER_SENDER))
                ? providerMessages.get(ProviderMessage.PLAYER_SENDER)
                : ProviderMessage.PLAYER_SENDER.msg();
        throw new CommandExitMessage(message);
    }

    @Override
    public String argumentDescription() {
        return "player sender";
    }

}
