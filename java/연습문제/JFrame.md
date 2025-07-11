# 소스 코드
```
import java.awt.*;
import javax.swing.*;

public class Demo extends JFrame {
    Demo() {
        setTitle("원 넓이 구하기");
        setLayout(new BorderLayout(10, 10));
        showNorth();
        showCenter();
        showSouth();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setVisible(true);
    }

    JTextField t1, t2;
    JTextArea a1;

    void showNorth() {
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel panel = new JPanel(new GridLayout(2, 0));

        JLabel l1 = new JLabel("원의 반지름");
        JLabel l2 = new JLabel("     원의 넓이");

        t1 = new JTextField(10);
        t2 = new JTextField(10);
        t2.setEnabled(false);

        p1.add(l1);
        p1.add(t1);
        p2.add(l2);
        p2.add(t2);

        panel.add(p1);
        panel.add(p2);

        add(panel, BorderLayout.NORTH);
    }

    void showCenter() {
        a1 = new JTextArea(5, 20);
        add(a1, BorderLayout.CENTER);
    }

    void showSouth() {
        JPanel SouthPanel = new JPanel();

        JButton Btn1 = new JButton("계산");
        JButton Btn2 = new JButton("리셋");
        String[] unit = {" ", "cm", "m"};
        JComboBox<String> comboBox = new JComboBox<>(unit);

        SouthPanel.add(Btn1);
        SouthPanel.add(comboBox);
        SouthPanel.add(Btn2);

        add(SouthPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new Demo();
    }
}
```
