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

    public void registerNewHighScore(int newHighscore, int levelNumber) {
        MyConnection con = new MyConnection();
        setHighscore(newHighscore, levelNumber);
        con.updateHighscore(username, levelNumber, newHighscore);
        con.close();
    }

    public void registerPlayer() {
        MyConnection con = new MyConnection();
        con.savePlayer(this);
        con.close();
    }

    public boolean isRegisteredAlready() {
        MyConnection con = new MyConnection();
        boolean isPresent = con.retrievePlayerByUsername(username).isPresent();
        con.close();
        return isPresent;
    }

}
