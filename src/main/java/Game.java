import java.util.*;

public class Game {

    private final char[][] player = new char[10][10];
    private final char[][] bot = new char[10][10];

    private final boolean[][] playerShips = new boolean[10][10];
    private final boolean[][] botShips = new boolean[10][10];

    private final Random r = new Random();

    private final int[] ships = {4,3,3,2,2,2,1,1,1,1};

    private int shipIndex = 0;

    private boolean battle = false;
    private boolean finished = false;
    private boolean win = false;

    private int playerLeft = 20;
    private int botLeft = 20;

    private int botMsgId;
    private int playerMsgId;

    public Game() {

        for (int i = 0; i < 10; i++) {
            Arrays.fill(player[i], '·');
            Arrays.fill(bot[i], '·');
        }

        int c = 0;
        while (c < 20) {
            int x = r.nextInt(10);
            int y = r.nextInt(10);
            if (!botShips[y][x]) {
                botShips[y][x] = true;
                c++;
            }
        }
    }

    // ================= РАССТАНОВКА =================
    public String place(String c) {

        if (battle) return "бой";

        int x = c.charAt(0) - 'A';
        int y = Integer.parseInt(c.substring(1)) - 1;

        if (player[y][x] == 'S') return "занято";

        player[y][x] = 'S';
        playerShips[y][x] = true;

        playerLeft--;

        if (playerLeft == 0) {
            battle = true;
            return "🚢 ВСЕ КОРАБЛИ ГОТОВЫ";
        }

        return "🚢 поставил";
    }

    // ================= ИГРОК =================
    public String playerShot(String c) {

        int x = c.charAt(0) - 'A';
        int y = Integer.parseInt(c.substring(1)) - 1;

        if (bot[y][x] != '·') return "уже";

        if (botShips[y][x]) {
            bot[y][x] = 'X';
            botLeft--;

            if (botLeft == 0) {
                finished = true;
                win = true;
                return "🏆 ПОБЕДА";
            }

            return "💥 попал";
        }

        bot[y][x] = '•';
        return "🌊 мимо";
    }

    // ================= БОТ =================
    public String botShot() {

        int x, y;

        do {
            x = r.nextInt(10);
            y = r.nextInt(10);
        } while (player[y][x] != '·');

        if (playerShips[y][x]) {
            player[y][x] = 'X';
            playerLeft--;

            if (playerLeft == 0) {
                finished = true;
                win = false;
                return "💀 ПОРАЖЕНИЕ";
            }

            return "💥 бот попал";
        }

        player[y][x] = '•';
        return "🌊 бот мимо";
    }

    public boolean isBattle() { return battle; }
    public boolean isFinished() { return finished; }
    public boolean isWin() { return win; }

    public void setBotMsgId(int id) { botMsgId = id; }
    public void setPlayerMsgId(int id) { playerMsgId = id; }

    public int getBotMsgId() { return botMsgId; }
    public int getPlayerMsgId() { return playerMsgId; }

    public char getPlayer(int i,int j){ return player[i][j]; }
    public char getBot(int i,int j){ return bot[i][j]; }
}