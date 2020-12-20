package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lets implements ActionListener {
    private static JLabel idLabel;
    private static JTextField idText;
    private static JLabel levelLabel;
    private static JTextField levelText;
    private static JButton button;

    public static void main(String[] args) {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        idLabel = new JLabel("Enter ID");
        idLabel.setBounds(30, 30, 80, 25);
        panel.add(idLabel);

        idText = new JTextField();
        idText.setBounds(90, 30, 165, 25);
        panel.add(idText);

        levelLabel = new JLabel("Enter Level");
        levelLabel.setBounds(20, 70, 100, 25);
        panel.add(levelLabel);

        levelText = new JTextField();
        levelText.setBounds(90, 70, 165, 25);
        panel.add(levelText);

        button = new JButton("Start Game");
        button.setBounds(10, 110, 110, 25);
        button.addActionListener(new Lets());
        panel.add(button);

        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String id = idText.getText();
        String level = levelText.getText();
        try {
            Integer.parseInt(id);
            Thread Ex2Client = new Thread(new MyGame(level));
            Ex2Client.start();
        } catch (NumberFormatException numberFormatException) {
            System.out.println("ID should contains only numbers");

        }
    }
}
