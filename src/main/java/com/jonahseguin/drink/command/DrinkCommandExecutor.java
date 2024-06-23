package com.jonahseguin.drink.command;

import com.jonahseguin.drink.provider.ProviderMessage;
import com.jonahseguin.drink.util.ComponentHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

public class DrinkCommandExecutor implements CommandExecutor {

    private final DrinkCommandService commandService;
    private final DrinkCommandContainer container;

    public DrinkCommandExecutor(DrinkCommandService commandService, DrinkCommandContainer container) {
        this.commandService = commandService;
        this.container = container;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (!command.getName().equalsIgnoreCase(container.getName())) {
            return false;
        }
        try {
            Map.Entry<DrinkCommand, String[]> data = container.getCommand(args);
            if (data != null && data.getKey() != null) {
                if (args.length > 0) {
                    if (args[args.length - 1].equalsIgnoreCase("help") && !data.getKey().getName().equalsIgnoreCase("help")) {
                        // Send help if they ask for it, if they registered a custom help sub-command, allow that to override our help menu
                        commandService.getHelpService().sendHelpFor(sender, container);
                        return true;
                    }
                }
                commandService.executeCommand(sender, data.getKey(), label, data.getValue());
            } else {
                if (args.length > 0) {
                    if (args[args.length - 1].equalsIgnoreCase("help")) {
                        // Send help if they ask for it, if they registered a custom help sub-command, allow that to override our help menu
                        commandService.getHelpService().sendHelpFor(sender, container);
                        return true;
                    }
                    final String message = (providerMessages.containsKey(ProviderMessage.UNKNOWN_SUBCOMMAND))
                            ? providerMessages.get(ProviderMessage.UNKNOWN_SUBCOMMAND)
                            : ProviderMessage.UNKNOWN_SUBCOMMAND.msg();
                    sender.sendMessage(ComponentHelper.format(message.replace("%cmd%", "/"+label)));
                } else {
                    if (container.isDefaultCommandIsHelp()) {
                        commandService.getHelpService().sendHelpFor(sender, container);
                    }
                    else {
                        final String message = (providerMessages.containsKey(ProviderMessage.PROVIDE_SUBCOMMAND))
                                ? providerMessages.get(ProviderMessage.PROVIDE_SUBCOMMAND)
                                : ProviderMessage.PROVIDE_SUBCOMMAND.msg();
                        sender.sendMessage(ComponentHelper.format(message.replace("%cmd%", "/"+label)));
                    }
                }
            }
            return true;
        }
        catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + "An exception occurred while performing this command.");
            ex.printStackTrace();
        }
        return false;
    }
}
