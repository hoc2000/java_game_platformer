package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {
    public GameWindow(GamePanel gamePanel){
        //tao window
        JFrame jframe = new JFrame();//pack luon cai Panel

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);//co dinh man hinh
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        //cho window neu bi swtch thi thao tac giao dien khong bi giu nguyen
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}
            @Override
            public void windowLostFocus(WindowEvent e) {
//                System.out.println("Focus Lost");
                gamePanel.getGame().widowFocusLost();
            }
        });
    }
}
