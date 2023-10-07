package net.shadowclient.main.command;

public abstract class Command {
    public String CommandName;
    public abstract void OnExecute(String...args);

}
