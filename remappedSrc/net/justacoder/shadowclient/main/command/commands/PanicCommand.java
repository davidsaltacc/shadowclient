package net.justacoder.shadowclient.main.command.commands;

import net.justacoder.shadowclient.main.command.Command;
import net.justacoder.shadowclient.main.module.ModuleManager;

public class PanicCommand extends Command {
    @Override
    public void OnExecute(String... args_) {
        ModuleManager.getAllModules().forEach((name, module) -> module.setDisabled(true, false));
    }

    public PanicCommand() {
        this.CommandName = "panic";
    }
}
