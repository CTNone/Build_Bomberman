
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame extends JFrame implements  KeyListener{
    JLabel label;
    MyFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(null);
        this.addKeyListener(this);


        label = new JLabel();
        label.setBounds(0,0, 100, 100 );
        label.setBackground(Color.red);
        label.setOpaque(true);

        this.add(label);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override // giữ phím
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ){
            case 37 : label.setLocation(label.getX()-1, label.getY());
                break;//a
            case 39 : label.setLocation(label.getX()+1, label.getY());
                break;//d
            case 38 : label.setLocation(label.getX(), label.getY()-1);
                break;//w
            case 40 : label.setLocation(label.getX(), label.getY()+1);
                break;//s
        }
    }

    @Override // nhả phím
    public void keyReleased(KeyEvent e) {
       // System.out.println("release key char " +e.getKeyChar());
        //System.out.println("release key code " +e.getKeyCode());

    }
    public static void main(String [] args){
        new MyFrame();
    }
}
