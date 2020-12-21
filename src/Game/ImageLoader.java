package Game;


import javax.swing.*;

public class ImageLoader extends JPanel {

    private JFrame frame;
    private ImageIcon icon;
    private JLabel label;



    public ImageLoader(){
        frame=new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            icon=new ImageIcon(getClass().getResource("forest.jpg"));
            label=new JLabel(icon);
            frame.add(label);
        }catch (Exception e){
            System.out.println("Nope");
        }
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ImageLoader i=new ImageLoader();
    }
}
