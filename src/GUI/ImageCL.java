package GUI;

import javax.swing.*;

import java.awt.*;


public class ImageCL extends JFrame {
//
//    private ImageIcon image1;
//    private JLabel label1;
//
//    private ImageIcon image2;
//    private JLabel label2;
//
//    ImageCL(){
//        setLayout(new FlowLayout());
//        image1=new ImageIcon(getClass().getResource("C:\\Users\\david\\Pictures\\Ex2-Icons\\forest"));
//        label1=new JLabel(image1);
//        this.add(label1);
//    }
//
//
//    public static void main(String[] args) {
//        ImageCL g=new ImageCL();
//        g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        g.setVisible(true);
//        g.pack();
//    }

    private Image bufferImage;
    private JLabel label;
    private Graphics g;


    public ImageCL(String str) {
//        frame = new JFrame("Game");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        try {
//            icon = new ImageIcon(getClass().getResource("forest.jpg"));
//            label = new JLabel(icon);
//            frame.add(label);
//        } catch (Exception e) {
//            System.out.println("Nope");
//        }
//        frame.pack();
//        frame.setVisible(true);
        super(str);
        paint(g);
        this.setTitle("Game");
        this.setVisible(true);
    }

    public void paint(Graphics g){
        int width=this.getWidth();
        int height=this.getHeight();
        bufferImage=createImage(width,height);

        g.drawString("Hello",0,0);
    }

    public static void main(String[] args) {


    }
}
