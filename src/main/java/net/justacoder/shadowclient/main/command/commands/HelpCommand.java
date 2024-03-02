package net.justacoder.shadowclient.main.command.commands;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.justacoder.shadowclient.main.SCMain;
import net.justacoder.shadowclient.main.command.Command;

public class HelpCommand extends Command {
    @Override
    public void OnExecute(String... args_) {

        MinecraftClient.getInstance().player.sendMessage(Text.of(SCMain.createHelp()));

    }

    public HelpCommand() {
        this.CommandName = "help";
    }
}
