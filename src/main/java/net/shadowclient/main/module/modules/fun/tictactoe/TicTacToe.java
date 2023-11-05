package net.shadowclient.main.module.modules.fun.tictactoe;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.ReceiveNoUpdates;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;
import net.shadowclient.main.setting.settings.BooleanSetting;
import net.shadowclient.main.setting.settings.NumberSetting;

@OneClick
@ReceiveNoUpdates
@SearchTags({"tic tac toe", "tictactoe", "game", "bored"})
public class TicTacToe extends Module {

    public BooleanSetting AI_STARTS = new BooleanSetting("AI Starts", false);
    public NumberSetting DIFFICULTY = new NumberSetting("Difficulty", 0, 5, 5, 1);

    public TicTacToe() {
        super("tictactoegame", "Start TicTacToe", ModuleCategory.FUN);

        addSettings(AI_STARTS, DIFFICULTY);
    }

    public TTTGame game = new TTTGame();

    @Override
    public void OnEnable() {
        game.aistart = AI_STARTS.booleanValue();
        game.difficulty = DIFFICULTY.intValue();
        game.reset();
    }
}
