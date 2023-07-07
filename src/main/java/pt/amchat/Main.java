package pt.amchat;

import pt.amchat.gui.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MyConnection con = new MyConnection();
        con.createTable();
        con.close();

        JFrame frame = new JFrame("Snake Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setResizable(false);

        Login login = new Login(frame);
        frame.add(login);
        frame.setVisible(true);

    }

}