package DBdata;

import java.awt.Color;
import java.awt.Font;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Portal extends JFrame {
    Statement st2;

    Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
    Font font;

    Portal() {
        st2 = Practice_Connector.createStatement();
        font = new Font("Comic Sans", Font.BOLD, 30);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        add(centerPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Portal Login");
        setVisible(true);
        pack();
        setSize(821, 450);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {
        new Portal();

    }
}
