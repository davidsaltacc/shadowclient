package net.shadowclient.main.module.modules.fun.tictactoe;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.NumberSetting;

@OneClick
@SearchTags({"tic tac toe", "tictactoe", "game", "bored"})
public class TicTacToe extends Module {

    public final BooleanSetting AI_STARTS = new BooleanSetting("AI Starts", false);
    public final NumberSetting DIFFICULTY = new NumberSetting("Difficulty", 0, 5, 5, 1);

    public TicTacToe() {
        super("tictactoegame", "Start TicTacToe", "Tic Tac Toe game against AI.", ModuleCategory.FUN);

        addSettings(AI_STARTS, DIFFICULTY);
    }

    public TTTGame game = new TTTGame();

    @Override
    public void onEnable() {
        game.aistart = AI_STARTS.booleanValue();
        game.difficulty = DIFFICULTY.intValue();
        game.reset();
    }
}
