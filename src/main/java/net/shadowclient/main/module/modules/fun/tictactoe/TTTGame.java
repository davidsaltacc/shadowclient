package net.shadowclient.main.module.modules.fun.tictactoe;

import net.shadowclient.main.util.ChatUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TTTGame {
    public String em = " ";
    public String p1 = "X";
    public String p2 = "O";

    public String player = p1;
    public boolean won = false;
    public String pwinner = null;

    public int difficulty = 5;
    public boolean aistart = false;

    public String[][] board = {
        {em, em, em},
        {em, em, em},
        {em, em, em}
    };

    public List<int[]> allPositions = List.of(
        new int[]{0, 0},
        new int[]{0, 1},
        new int[]{0, 2},
        new int[]{1, 0},
        new int[]{1, 1},
        new int[]{1, 2},
        new int[]{2, 0},
        new int[]{2, 1},
        new int[]{2, 2}
    );
    public static List<List<int[]>> winningPos = List.of( // only noobs complain about java class names, i have different problems
        List.of(new int[]{0, 0}, new int[]{1, 0}, new int[]{2, 0}),
        List.of(new int[]{0, 1}, new int[]{1, 1}, new int[]{2, 1}),
        List.of(new int[]{0, 2}, new int[]{1, 2}, new int[]{2, 2}),

        List.of(new int[]{0, 0}, new int[]{0, 1}, new int[]{0, 2}),
        List.of(new int[]{1, 0}, new int[]{1, 1}, new int[]{1, 2}),
        List.of(new int[]{2, 0}, new int[]{2, 1}, new int[]{2, 2}),

        List.of(new int[]{0, 0}, new int[]{1, 1}, new int[]{2, 2}),
        List.of(new int[]{0, 2}, new int[]{1, 1}, new int[]{2, 0})
    );

    public ArrayList<int[]> getEmpty() {
        ArrayList<int[]> empty = new ArrayList<>();
        allPositions.forEach((pos) -> {
            if (board[pos[1]][pos[0]] == em) {
                empty.add(pos);
            }
        });
        return empty;
    }

    public String winner() {
        AtomicReference<String> win = new AtomicReference<>("");
        winningPos.forEach((pos) -> {
            String pl0 = board[pos.get(0)[0]][pos.get(0)[1]];
            String pl1 = board[pos.get(1)[0]][pos.get(1)[1]];
            String pl2 = board[pos.get(2)[0]][pos.get(2)[1]];
            if (pl0 == pl1 && pl1 == pl2 && pl2 != em) {
                win.set(pl0);
            }
        });
        return win.get();
    }

    public int minimax(String[][] board, int depth, boolean ismax) {
        String result = winner();
        ArrayList<int[]> empty = getEmpty();
        if (result != "") {
            return result == p1 ? 1 : -1;
        } else if (empty.isEmpty()) {
            return 0;
        }
        if (ismax) {
            AtomicInteger bestscore = new AtomicInteger((int) -1e7);
            empty.forEach((pos) -> {
                board[pos[1]][pos[0]] = player;
                int score = minimax(board, depth + 1, false);
                board[pos[1]][pos[0]] = em;
                bestscore.set(Math.max(score, bestscore.get()));
            });
            return bestscore.get();
        } else {
            AtomicInteger bestscore = new AtomicInteger((int) 1e7);
            empty.forEach((pos) -> {
                if (player == p1) {
                    board[pos[1]][pos[0]] = p2;
                } else {
                    board[pos[1]][pos[0]] = p1;
                }
                int score = minimax(board, depth + 1, true);
                board[pos[1]][pos[0]] = em;
                bestscore.set(Math.min(score, bestscore.get()));
            });
            return bestscore.get();
        }
    }

    public int[] getaismove() {
        ArrayList<int[]> empty = getEmpty();
        AtomicInteger bestscore = new AtomicInteger((int) -1e7);
        AtomicInteger bestmove0 = new AtomicInteger(0); // i hate java
        AtomicInteger bestmove1 = new AtomicInteger(0);
        empty.forEach((pos) -> {
            board[pos[1]][pos[0]] = player;
            int score = minimax(board, 0, false);
            board[pos[1]][pos[0]] = em;
            if (score > bestscore.get()) {
                bestscore.set(score);
                bestmove0.set(pos[0]);
                bestmove1.set(pos[1]);
            }
        });
        return new int[]{bestmove0.get(), bestmove1.get()};
    }

    public int[] aimove() {
        ArrayList<int[]> empty = getEmpty();
        int[] randmove = empty.get((int) Math.floor(Math.random() * empty.toArray().length));
        int[] aismove = getaismove();

        if (difficulty == 1) {
            if (Math.random() > 0.2f) {
                return randmove;
            }
            return aismove;
        }
        if (difficulty == 2) {
            if (Math.random() > 0.4f) {
                return randmove;
            }
            return aismove;
        }
        if (difficulty == 3) {
            if (Math.random() > 0.65f) {
                return randmove;
            }
            return aismove;
        }
        if (difficulty == 4) {
            if (Math.random() > 0.85f) {
                return randmove;
            }
            return aismove;
        }
        if (difficulty == 5) {
            return aismove;
        }

        return randmove;
    }

    public void onwon() {
        ChatUtils.sendMessageClient(pwinner + " won!");
    }
    public void ondraw() {
        ChatUtils.sendMessageClient("No one won.");
    }

    public static void drawBoard(String[][] board) {
        ChatUtils.sendMessageClient("*board goes here*");
    }

    public void played(int y, int x) { // TODO implement entire UI (chat) things to make this playable
        if (won) {
            return;
        }
        if (board[y][x] != em) {
            drawBoard(board);
            return;
        }

        board[y][x] = player;
        drawBoard(board);

        pwinner = winner();
        if (pwinner != null) {
            won = true;
            onwon();
            return;
        }
        if (getEmpty().isEmpty()) {
            won = true;
            ondraw();
            return;
        }
        if (player == p1) {
            player = p2;
        } else {
            player = p1;
        }

        int[] move = aimove();
        board[move[0]][move[1]] = player;
        drawBoard(board);

        pwinner = winner();
        if (pwinner != null) {
            won = true;
            onwon();
            return;
        }
        if (getEmpty().isEmpty()) {
            won = true;
            ondraw();
            return;
        }
        if (player == p1) {
            player = p2;
        } else {
            player = p1;
        }
    }

    public void reset() {
        won = false;
        pwinner = null;
        board = new String[][]{{em, em, em}, {em, em, em}, {em, em, em}};

        if (aistart) {
            p1 = "X";
            p2 = "O";
            player = p1;
            int[] move = aimove();
            board[move[0]][move[1]] = player;
            drawBoard(board);
            player = p2;
        } else {
            p1 = "O";
            p2 = "X";
            player = p2;
            drawBoard(board);
        }
    }

}