package net.shadowclient.main.command;

import net.shadowclient.main.SCMain;
import net.shadowclient.main.command.commands.HelpCommand;
import net.shadowclient.main.command.commands.PanicCommand;
import net.shadowclient.main.module.ModuleManager;
import net.shadowclient.main.util.ChatUtils;
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
