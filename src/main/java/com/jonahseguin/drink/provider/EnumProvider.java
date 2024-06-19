package com.jonahseguin.drink.provider;

import com.google.common.collect.Lists;
import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.regex.Pattern;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

public class EnumProvider<T extends Enum<T>> extends DrinkProvider<T> {

    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^A-Za-z0-9]");
    private final Class<T> enumClass;

    public EnumProvider(Class<T> enumClass) {
        this.enumClass = enumClass;
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
    public T provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String name = arg.get();
        String s = simplify(name);

        for (T entry : enumClass.getEnumConstants()) {
            if (simplify(entry.name()).equalsIgnoreCase(s)) {
                return entry;
            }
        }
        final String message = (providerMessages.containsKey(ProviderMessage.ENUM))
                ? providerMessages.get(ProviderMessage.ENUM)
                : ProviderMessage.ENUM.msg();
        throw new CommandExitMessage(message.replace("%values%", StringUtils.join(getSuggestions(arg.getSender(),""), ' ')));
    }

    @Override
    public String argumentDescription() {
        return enumClass.getSimpleName();
    }

    @Override
    public List<String> getSuggestions(@Nonnull String prefix) {
        List<String> suggestions = Lists.newArrayList();
        String test = simplify(prefix);

        for (T entry : enumClass.getEnumConstants()) {
            String name = simplify(entry.name());
            if (test.isEmpty() || name.startsWith(test)) {
                suggestions.add(entry.name().charAt(0) + entry.name().substring(1).toLowerCase());
            }
        }

        return suggestions;
    }

    private static String simplify(String t) {
        return NON_ALPHANUMERIC.matcher(t.toLowerCase()).replaceAll("");
    }
}
