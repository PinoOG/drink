package com.jonahseguin.drink.provider;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import com.jonahseguin.drink.parametric.DrinkProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

import static com.jonahseguin.drink.command.DrinkCommandService.providerMessages;

public class DoubleProvider extends DrinkProvider<Double> {

    public static final DoubleProvider INSTANCE = new DoubleProvider();

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
    public Double defaultNullValue() {
        return 0D;
    }

    @Override
    public Double provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage {
        String s = arg.get();
        try {
            return Double.parseDouble(s);
        }
        catch (NumberFormatException ex) {
            final String message = (providerMessages.containsKey(ProviderMessage.DOUBLE))
                    ? providerMessages.get(ProviderMessage.DOUBLE)
                    : ProviderMessage.DOUBLE.msg();
            throw new CommandExitMessage(message);
        }
    }

    @Override
    public String argumentDescription() {
        return "decimal number";
    }
}
