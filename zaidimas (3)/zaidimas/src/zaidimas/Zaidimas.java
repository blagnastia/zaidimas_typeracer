package zaidimas;

import java.util.Scanner;
import window.Langas;

public class Zaidimas {
    private static float score = 1000;
    private static int playerCount; // kiek zaideju
    private static int player = 1; //dabartinis zaidejas
    private static int winningPlayer;

    public static float getScore() {
        return score;
    }

    public static void setScore(float score) {
        Zaidimas.score = score;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public static void setPlayerCount(int playerCount) {
        Zaidimas.playerCount = playerCount;
    }

    public static int getPlayer() {
        return player;
    }

    public static void setPlayer(int player) {
        Zaidimas.player = player;
    }

    public static int getWinningPlayer() {
        return winningPlayer;
    }

    public static void setWinningPlayer(int winningPlayer) {
        Zaidimas.winningPlayer = winningPlayer;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.println("Kiek bus žaidėjų?");
        Scanner scanner = new Scanner(System.in);
        int playerCount = scanner.nextInt();
        setPlayerCount(playerCount);
        System.out.println("Žaidėjų skaičius: " + playerCount);
        
        createNewGame();
    }
    
    public static void createNewGame() {
        Langas ZaidimoLangas = new Langas();
        ZaidimoLangas.setVisible(true);
    }
}
