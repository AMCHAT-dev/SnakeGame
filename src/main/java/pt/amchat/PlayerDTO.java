package pt.amchat;

public record PlayerDTO(String username, int highscore) {
    public String toString(int levelNumber) {
        return "Player{" +
                "username='" + username + '\'' +
                ", highscore of level" + levelNumber + "=" + highscore +
                '}';
    }
}
