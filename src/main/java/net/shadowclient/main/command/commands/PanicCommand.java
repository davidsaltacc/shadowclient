package net.shadowclient.main.command.commands;

import net.shadowclient.main.command.Command;
import net.shadowclient.main.module.ModuleManager;

public class PanicCommand extends Command {
    @Override
    public void OnExecute(String... args_) {
        ModuleManager.getAllModules().forEach((name, module) -> module.setDisabled(true, false));
    }

    public PanicCommand() {
        this.CommandName = "panic";
    }
}
