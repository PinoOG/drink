package com.jonahseguin.drink.parametric;

import com.jonahseguin.drink.argument.CommandArg;
import com.jonahseguin.drink.exception.CommandExitMessage;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class DrinkProvider<T> {

    public abstract boolean doesConsumeArgument();

    public abstract boolean isAsync();

    public boolean allowNullArgument() {
        return true;
    }

    @Nullable
    public T defaultNullValue() {
        return null;
    }

    @Nullable
    public abstract T provide(@Nonnull CommandArg arg, @Nonnull List<? extends Annotation> annotations) throws CommandExitMessage;

    public abstract String argumentDescription();

    public List<String> getSuggestions(@Nonnull String prefix) {
        return new CopyOnWriteArrayList<>();
    }

    public List<String> getSuggestions(CommandSender sender, @Nonnull String prefix) {
        return getSuggestions(prefix);
    }

    public List<String> getSuggestions(CommandSender sender, @Nonnull String prefix, Map<CommandParameter, String> parameters) {
        return getSuggestions(sender, prefix);
    }

    public List<String> getSuggestions(CommandSender sender, @Nonnull String prefix, Map<CommandParameter, String> parameters, List<Annotation> annotations) {
        return getSuggestions(sender, prefix, parameters);
    }

    public List<String> getSuggestions(@Nonnull String prefix, List<String> parameters) {
        return getSuggestions(prefix);
    }

    public CompletableFuture<List<String>> getSuggestionsAsync(CommandSender sender, @Nonnull String prefix) {
        return getSuggestionsAsync(prefix);
    }

    public CompletableFuture<List<String>> getSuggestionsAsync(CommandSender sender, @Nonnull String prefix, Map<CommandParameter, String> parameters) {
        return getSuggestionsAsync(sender, prefix);
    }

    public CompletableFuture<List<String>> getSuggestionsAsync(CommandSender sender, @Nonnull String prefix, Map<CommandParameter, String> parameters, List<Annotation> annotations) {
        return getSuggestionsAsync(sender, prefix, parameters);
    }

    public CompletableFuture<List<String>> getSuggestionsAsync(@Nonnull String prefix) {
        return CompletableFuture.completedFuture(null);
    }

    protected boolean hasAnnotation(List<? extends Annotation> list, Class<? extends Annotation> a) {
        return list.stream().anyMatch(annotation -> annotation.annotationType().equals(a));
    }


}
