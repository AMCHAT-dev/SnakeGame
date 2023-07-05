package pt.amchat;

public class Player {
    private String username;
    private int[] highscore = new int[Constants.NUMBER_OF_LEVELS];

    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getHighscoreOfLevel(int i) {
        return highscore[i - 1];
    }

    public void setHighscore(int highscore, int levelNumber) {
        this.highscore[levelNumber - 1] = highscore;
    }

}
