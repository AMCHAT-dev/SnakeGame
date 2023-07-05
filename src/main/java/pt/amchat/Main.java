package pt.amchat;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        InitialMenu initialMenu = new InitialMenu(frame);
        frame.add(initialMenu);
        frame.setVisible(true);

        Player afonso = new Player("Afonso");
        afonso.setHighscore(10, 1);
        Player joana = new Player("Joana");
        joana.setHighscore(15, 1);

        MyConnection con = new MyConnection();
        con.connect();
        con.createTable();
        con.savePlayer(afonso);
        con.savePlayer(joana);
        for (PlayerDTO playerDTO : con.retrieveTop10(1)) {
            System.out.println(playerDTO);
        }
        con.close();
    }

}