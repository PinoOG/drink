package com.jonahseguin.drink.provider;

public enum ProviderMessage {
    UNAUTHORIZED("You are not authorized to use this command"),
    BOOLEAN("You can only use (true/false) as values"),
    DATE("You must specify a date in this format yyyy-MM-dd@HH:mm"),
    DOUBLE("You must provide a decimal number like 1.20 or 67.5"),
    DURATION("Duration must be in format hh:mm or hh:mm:ss or 1h2m3s"),
    ENUM("You can only use one of these values : %values%"),
    INTEGER("You must provide an integer value"),
    LONG("You must provide a long value"),
    CONSOLE("This is a console-only command"),
    PLAYER("Target player %player% not found"),
    PLAYER_SENDER("This is a player-only command"),
    WORLD("World %world% not found"),
    TEXT("Hey there is one or more arguments missing"),
    UNKNOWN_SUBCOMMAND("Unknown sub-command check %cmd% for available commands!"),
    PROVIDE_SUBCOMMAND("Please choose a sub-command from %cmd%"),
    MISSING_ARGUMENT("You need to provide %missing% to complete this command");

    public final String msg;

    ProviderMessage(String msg){
        this.msg = msg;
    }

    public String msg(){
        return msg;
    }
}
