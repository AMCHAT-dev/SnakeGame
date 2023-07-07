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

    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            optionalConnection = Optional.of(DriverManager.getConnection("jdbc:mysql://localhost:3306/snake",
                    Constants.USER, Constants.PASSWORD));
        } catch (ClassNotFoundException e) {
            System.out.println("DB failed!");
        } catch (SQLException e) {
            System.out.println("Error accessing db");
        }
    }

    public Connection getOptionalConnection() {
        return optionalConnection.orElseThrow(() -> new RuntimeException("Empty Connection"));
    }

    public void createTable() {
        connect();
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String tableSql = "CREATE TABLE IF NOT EXISTS players "
                    + "(username varchar(30) PRIMARY KEY, "
                    + "highscore1 int, highscore2 int)";
            stmt.execute(tableSql);
        } catch (SQLException e) {
            System.out.println("Error creating table");
        }
    }

    public void savePlayer(Player player) {
        connect();
        try (Statement stmt = getOptionalConnection().createStatement()) {
            String insertSql = "INSERT INTO players(username, highscore1, highscore2)"
                    + " VALUES('" + player.getUsername() + "', " + player.getHighscoreOfLevel(1)
                    + ", " + player.getHighscoreOfLevel(2) + ")";
            stmt.executeUpdate(insertSql);
        } catch (SQLException e) {
            System.out.println("Error saving player");
        }
    }

    public void updateHighscore(String username, int levelNumber, int newHighscore) {
        connect();
        try (PreparedStatement stmt = getOptionalConnection().prepareStatement(
                "UPDATE players SET highscore" + levelNumber + " = ? WHERE username = ?")) {
            stmt.setInt(1, newHighscore);
            stmt.setString(2, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating highscore");
        }
    }


    public Optional<Player> retrievePlayerByUsername(String username) {
        connect();
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
            System.out.println("Error in reading one player");
        }
        return Optional.empty();
    }


    public List<PlayerDTO> retrieveTop10(int levelNumber) {
        connect();
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
            System.out.println("Error in Top10");
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
