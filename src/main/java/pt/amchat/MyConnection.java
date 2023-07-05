package pt.amchat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyConnection {
    private Optional<Connection> optionalConnection;

    public MyConnection() {
        this.optionalConnection = Optional.empty();
    }

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            optionalConnection = Optional.of(DriverManager.getConnection("jdbc:mysql://localhost:3306/snake", "root", "@Telemovel1"));
        } catch (ClassNotFoundException e) {
            System.out.println("DB failed!");
        } catch (
                SQLException e) {
            System.out.println("Error accessing db");
        }
    }

    private Connection getOptionalConnection() {
        return optionalConnection.orElseThrow(RuntimeException::new);
    }

    public void createTable() {
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String tableSql = "CREATE TABLE IF NOT EXISTS players "
                    + "(username varchar(30) PRIMARY KEY, "
                    + "highscore1 int, highscore2 int)";
            stmt.execute(tableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePlayer(Player player) {
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String insertSql = "INSERT INTO players(username, highscore1, highscore2)"
                    + " VALUES('" + player.getUsername() + "', " + player.getHighscoreOfLevel(1)
                    + ", " + player.getHighscoreOfLevel(2) + ")";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Player> retrievePlayerByUsername(String username) {
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String query = "SELECT username, highscore1, highscore2 FROM players WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String playerUsername = rs.getString("username");
                int highscore1 = rs.getInt("highscore1");
                int highscore2 = rs.getInt("highscore2");
                Player player = new Player(playerUsername);
                player.setHighscore(highscore1, 1);
                player.setHighscore(highscore2, 2);
                return Optional.of(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    public List<PlayerDTO> retrieveTop10(int levelNumber) {
        List<PlayerDTO> top10 = new ArrayList<>();
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String query = "SELECT username, highscore" + levelNumber + " FROM players ORDER BY highscore" + levelNumber + " DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("username");
                int highscore = rs.getInt("highscore" + levelNumber);
                top10.add(new PlayerDTO(username, highscore));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return top10;
    }

    public void close() {
        try {
            if (optionalConnection.isPresent()) {
                optionalConnection.get().close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing");
        }

    }
}
