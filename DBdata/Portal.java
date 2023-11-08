package DBdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class Portal extends JFrame implements ActionListener, ItemListener, WindowListener {
    Statement st2;

    Color fgColor = Color.BLACK, bgColor = Color.WHITE;
    Font font;
    JPanel tab1Panel = new JPanel();
    JPanel tab2Panel = new JPanel();
    JPanel tab3Panel = new JPanel();
    JPanel tab4Panel = new JPanel();
    JPanel theme = new JPanel();
    JPanel bt = new JPanel();
    JRadioButton light, dark;
    JButton logout, apply;
    String them = "light";

    Portal() {
        // st2 = Practice_Connector.createStatement();
        font = new Font("Comic Sans", Font.BOLD, 30);
        GridLayout gl = new GridLayout(5, 1);
        JTabbedPane tab = new JTabbedPane();
        tab.setFont(new Font("calibri", Font.BOLD, 13));

        light = createradiobutton("Light");
        dark = createradiobutton("Dark");
        tab1Panel.setBackground(bgColor);
        tab2Panel.setBackground(bgColor);
        tab3Panel.setBackground(bgColor);
        tab4Panel.setBackground(bgColor);
        theme.setBackground(bgColor);
        bt.setBackground(bgColor);
        tab4Panel.setLayout(gl);

        tab.addTab("Profile", tab1Panel);

        tab.addTab("Accomodation", tab2Panel);

        tab.addTab("News", tab3Panel);

        tab.addTab("Settings", tab4Panel);

        light.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(light);
        group.add(dark);

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.DARK_GRAY);

        logout = createbutton("Logout");
        apply = createbutton("Apply Changes");

        theme.add(light);
        theme.add(dark);
        tab4Panel.add(theme);
        bt.add(logout);
        bt.add(apply);
        tab4Panel.add(bt);

        light.addItemListener(this);
        dark.addItemListener(this);
        addWindowListener(this);

        add(tab);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("My Portal");
        setVisible(true);
        pack();
        setSize(821, 450);
        setLocationRelativeTo(null);

    }

    JButton createbutton(String txt) {
        JButton btnr = new JButton(txt);
        btnr.setFont(font);
        btnr.setForeground(bgColor);
        btnr.setBackground(fgColor);
        btnr.setFocusable(false);
        btnr.addActionListener(this);
        return btnr;
    }

    JRadioButton createradiobutton(String txt) {
        JRadioButton btn = new JRadioButton(txt);
        btn.setFont(new Font("Comic Sans", Font.ITALIC, 12));
        btn.setBackground(bgColor);
        btn.setForeground(fgColor);
        btn.setFocusable(false);

        return btn;
    }

    @Override
    public void itemStateChanged(ItemEvent ea) {
        if (light.isSelected()) {
            them = "light";
        } else if (dark.isSelected()) {
            them = "dark";
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == apply) {

            if (them.equals("light")) {
                fgColor = Color.BLACK;
                bgColor = Color.WHITE;
                tab1Panel.setBackground(bgColor);
                tab2Panel.setBackground(bgColor);
                tab3Panel.setBackground(bgColor);
                tab4Panel.setBackground(bgColor);
                logout.setBackground(fgColor);
                logout.setForeground(bgColor);
                apply.setBackground(fgColor);
                apply.setForeground(bgColor);
                theme.setBackground(bgColor);
                bt.setBackground(bgColor);
                light.setBackground(bgColor);
                dark.setBackground(bgColor);
                dark.setForeground(fgColor);
                light.setForeground(fgColor);

            } else {
                fgColor = Color.WHITE;
                bgColor = Color.BLACK;
                tab1Panel.setBackground(bgColor);
                tab2Panel.setBackground(bgColor);
                tab3Panel.setBackground(bgColor);
                tab4Panel.setBackground(bgColor);
                logout.setBackground(fgColor);
                logout.setForeground(bgColor);
                apply.setBackground(fgColor);
                apply.setForeground(bgColor);
                bt.setBackground(bgColor);
                theme.setBackground(bgColor);
                light.setBackground(bgColor);
                light.setForeground(fgColor);
                dark.setBackground(bgColor);
                dark.setForeground(fgColor);

            }

        } else if (e.getSource() == logout) {
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.NO_OPTION) {
                new Portal();
            } else {
                dispose();
            }

        }
    }

    public static void main(String[] args) {
        new Portal();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        JOptionPane.showMessageDialog(null, "Logged In Sucessfully!", "Logged-In", 1);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int reply = JOptionPane.showConfirmDialog(null, "Are you sure ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) {
            new Portal();
        }
        // else {
        // JOptionPane.showMessageDialog(null, "Logged out Sucessfully!", "Logged-out",
        // 1);
        // }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        JOptionPane.showMessageDialog(null, "Logged out Sucessfully!", "Logged-out", 1);
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}