package net.shadowclient.main.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.shadowclient.main.SCMain;
import net.shadowclient.main.command.commands.HelpCommand;
import net.shadowclient.main.command.commands.PanicCommand;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    public static final Map<String, Command> commands = new HashMap<>();

    public static void registerCommands() {
        registerCommand(new HelpCommand());
        registerCommand(new PanicCommand());
    }

    public static void registerCommand(Command command) {
        commands.put(command.CommandName, command);
    }

    public static void Execute(String chatMessage) {
        String str = chatMessage.substring(SCMain.ClientCommandPrefix.length());
        String[] parts = str.split(" ");

        boolean found = false;

        if (commands.get(parts[0]) != null) {
            found = true;
            commands.get(parts[0]).OnExecute(parts);
        }

        if (!found) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("§4Unknown Command " + str));
        }
    }
}
