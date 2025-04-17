package net.justacoder.shadowclient.main.command;

import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.command.commands.HelpCommand;
import net.justacoder.shadowclient.main.command.commands.PanicCommand;
import net.justacoder.shadowclient.main.module.ModuleManager;
import net.justacoder.shadowclient.main.util.ChatUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
    public static final Map<String, Command> commands = new ConcurrentHashMap<>();

    public static void registerCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new PanicCommand());
    }

    public static void registerCommand(Command command) {
        commands.put(command.CommandName, command);
    }

    public static void execute(String chatMessage) {
        String str = chatMessage.substring(SCMain.ClientCommandPrefix.length());
        String[] parts = str.split(" ");

        if (commands.get(parts[0]) != null) {
            commands.get(parts[0]).OnExecute(parts);
            return;
        }

        if (ModuleManager.getModule(parts[0]) != null) {
            SCMain.toggleModuleEnabled(parts[0]);
            return;
        }

        ChatUtils.sendMessageClient("§4Unknown Command: " + str);
    }
}
