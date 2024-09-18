package com.jonahseguin.drink.provider.spigot;

import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import com.jonahseguin.drink.provider.ProviderMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

public class PlayerProvider extends DrinkProvider<Player> {

    private final Plugin plugin;

    public PlayerProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean doesConsumeArgument() {
        return true;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean allowNullArgument() {
        return false;
    }

    @Nullable
    @Override
    public Player defaultNullValue() {
        return null;
    }

    @Nullable
    @Override
    public Player provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        //Player p = plugin.getServer().getPlayerExact(name);
        Player p = Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> player.getName().startsWith(name))
                .findFirst()
                .orElse(null);
        if (p != null) {
            return p;
        }
        if (arg.getSender() instanceof Player player && annotations.stream().anyMatch(a -> a.annotationType() == OptArg.class)) {
            return player;
        }
        final String message = (providerMessages.containsKey(ProviderMessage.PLAYER))
                ? providerMessages.get(ProviderMessage.PLAYER)
                : ProviderMessage.PLAYER.msg();
        throw new CommandExitMessage(message.replace("%player%", name));
    }

    @Override
    public String argumentDescription() {
        return "player";
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        final String finalPrefix = prefix.toLowerCase();
        return plugin.getServer().getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .filter(s -> finalPrefix.isEmpty() || s.toLowerCase().startsWith(finalPrefix))
                .collect(Collectors.toList());
    }
}
