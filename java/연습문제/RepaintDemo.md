# 소스 코드

```
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class RepaintDemo extends JFrame {
    Random r = new Random();                     
    List<Rectangle> list = new ArrayList<>();   

    public RepaintDemo() {
        setTitle("클릭할 때마다 임의의 사각형 그리기");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        MousePanel panel = new MousePanel();   
        add(panel);                             

        setVisible(true);
    }

    class MousePanel extends JPanel {
        public MousePanel() {
           
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    int size = r.nextInt(20) + 5;           
                    int x = r.nextInt(getWidth() - size);   
                    int y = r.nextInt(getHeight() - size);   

                    list.add(new Rectangle(x, y, size, size));
                    repaint();                               
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Rectangle rect : list) {
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    public static void main(String[] args) {
        new RepaintDemo(); 
    }
}
```
