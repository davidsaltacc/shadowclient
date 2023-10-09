package net.shadowclient.main.module.modules.fun.tictactoe;

import net.shadowclient.main.annotations.OneClick;
import net.shadowclient.main.annotations.SearchTags;
import net.shadowclient.main.module.Module;
import net.shadowclient.main.module.ModuleCategory;

@OneClick
@SearchTags({"tic tac toe", "tictactoe", "game"})
public class TicTacToe extends Module {
    public TTTGame game;
    public TicTacToe() {
        super("tictactoegame", "TicTacToe Game", ModuleCategory.FUN);
    }

    @Override
    public void OnEnable() {
        game = new TTTGame();
        game.startGame();
    }
}
