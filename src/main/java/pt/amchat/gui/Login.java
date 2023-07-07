package pt.amchat.gui;

import pt.amchat.MyConnection;
import pt.amchat.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class Login extends JPanel implements ActionListener {
    private JFrame frame;
    private JTextField usernameField;
    private JButton launchButton;

    public Login(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Initialize components
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        launchButton = new JButton("Launch Game");

        // Add components to input panel
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(launchButton);

        // Add action listener to launch button
        launchButton.addActionListener(this);

        // Add input panel to the main panel
        add(inputPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == launchButton) {
            String username = usernameField.getText();
            MyConnection con = new MyConnection();
            Optional<Player> player = con.retrievePlayerByUsername(username);
            Player player1;
            if (player.isEmpty()) {
                player1 = new Player(username);
            } else {
                player1 = player.get();
            }
            if (!username.isEmpty()) {
                InitialMenu initialMenu = new InitialMenu(frame, player1); // Pass the username to the game panel
                frame.getContentPane().removeAll();
                frame.getContentPane().add(initialMenu);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
                initialMenu.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a username.");
            }
        }
    }
}
