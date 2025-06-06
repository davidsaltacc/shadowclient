package net.justacoder.shadowclient.main.module.modules.fun.tictactoe;

import net.justacoder.shadowclient.main.annotations.OneClick;
import net.justacoder.shadowclient.main.module.Module;
import net.justacoder.shadowclient.main.module.ModuleCategory;
import net.justacoder.shadowclient.main.setting.settings.BooleanSetting;
import net.justacoder.shadowclient.main.setting.settings.NumberSetting;

@OneClick
public class TicTacToe extends Module {

    public final BooleanSetting AI_STARTS = new BooleanSetting("AI Starts", false);
    public final NumberSetting DIFFICULTY = new NumberSetting("Difficulty", 0, 5, 5, 0);

    public TicTacToe() {
        super("tictactoegame", ModuleCategory.FUN, new String[]{"tic tac toe", "tictactoe", "games"});

        addSettings(AI_STARTS, DIFFICULTY);
    }

    public TTTGame game = new TTTGame();

    @Override
    public boolean onEnable() {
        game.aistart = AI_STARTS.booleanValue();
        game.difficulty = DIFFICULTY.intValueEased();
        game.reset();
        return super.onEnable();
    }
}
