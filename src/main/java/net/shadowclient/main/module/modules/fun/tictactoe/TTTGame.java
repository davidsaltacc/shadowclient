package net.shadowclient.main.module.modules.fun.tictactoe;

import net.shadowclient.main.util.ChatUtils;
import java.util.ArrayList;

public class TTTGame { // todo continue, video timestamp 13:??

    public int[][] board;
    public String player1 = "X";
    public String player2 = "O";
    public String currentPlayer;
    public boolean[][] available;

    public void startGame() {
        board = new int[3][3];
        board[0][0] = 0;
        board[0][1] = 0;
        board[0][2] = 0;
        board[1][0] = 0;
        board[1][1] = 0;
        board[1][2] = 0;
        board[2][0] = 0;
        board[2][1] = 0;
        board[2][2] = 0;
        currentPlayer = player1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                available[i][j] = true;
            }
        }
    }

    public ArrayList<Integer[]> getAvailable() {
        ArrayList<Integer[]> av = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (available[i][j]) {
                    Integer[] integer = new Integer[2];
                    integer[0] = i;
                    integer[1] = j;
                    av.add(integer);
                }
            }
        }
        return av;
    }

    public void nextTurn() {
        ArrayList<Integer[]> av = getAvailable();
        int index = (int) Math.floor(Math.random() * av.toArray().length);
        Integer[] spot = av.get(index);
        board[spot[0]][spot[1]] = toInt(currentPlayer);
        currentPlayer = toPlayer(toInt(currentPlayer) * -1); // switch player
    }

    public String toPlayer(int v) {
        if (v == 1) {
            return player1;
        }
        if (v == -1) {
            return player2;
        }
        return " ";
    }
    public int toInt(String v) {
        if (v == player1) {
            return 1;
        }
        if (v == player2) {
            return -1;
        }
        return 0;
    }

    public void showBoard() {
        String text = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                text += toPlayer(board[i][j]) + " ";
            }
            text += "\n";
        }
        ChatUtils.sendMessageClient(text);
    }
}
