
package DBdata;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class confirmportalpass extends JFrame implements ActionListener, WindowListener, ItemListener {
    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    Font font = new Font("Comic sans", Font.BOLD, 30);
    JPasswordField Password, cPassword;
    JButton submit, reset;
    JRadioButton show, hide;
    String tick = "", User, phone;
    boolean result;
    Statement st2;

    confirmportalpass(String use, String pho, Statement st) {
        User = use;
        phone = pho;
        st2 = st;
        GridLayout gl = new GridLayout(4, 1);
        JPanel centerpanel = new JPanel();
        centerpanel.setFont(font);
        centerpanel.setBackground(bgcolor);
        centerpanel.setForeground(fgcolor);
        gl.setVgap(10);

        add(centerpanel);
        centerpanel.setLayout(gl);

        centerpanel.add(createlabel("Password"));
        Password = createpassfield();
        centerpanel.add(Password);

        centerpanel.add(createlabel("Confirm Password"));
        cPassword = createpassfield();
        centerpanel.add(cPassword);
        centerpanel.add(createlabel(""));

        JPanel combines = new JPanel();
        combines.setBackground(bgcolor);

        show = checkButton("Show");
        combines.add(show);

        hide = checkButton("Hide");
        combines.add(hide);

        ButtonGroup groupie = new ButtonGroup();
        groupie.add(hide);
        groupie.add(show);

        hide.addItemListener(this);
        show.addItemListener(this);
        centerpanel.add(combines);
        hide.setSelected(true);

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.DARK_GRAY);

        add(southpanel, BorderLayout.SOUTH);

        reset = createbutton("RESET");
        southpanel.add(reset);

        submit = createbutton("SUBMIT");
        southpanel.add(submit);
        addWindowListener(this);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Password Update");
        pack();
        setSize(600, 350);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JRadioButton checkButton(String txt) {
        JRadioButton btn = new JRadioButton(txt);
        btn.setForeground(fgcolor);
        btn.setBackground(bgcolor);
        btn.setFont(font);
        btn.setFont(font);
        return btn;
    }

    JPasswordField createpassfield() {
        JPasswordField pass = new JPasswordField();
        pass.setBackground(bgcolor);
        pass.setForeground(fgcolor);
        pass.setFont(font);

        return pass;
    }

    JLabel createlabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(font);
        label.setForeground(fgcolor);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    JButton createbutton(String txt) {
        JButton btn = new JButton(txt);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setBackground(bgcolor);
        btn.setForeground(fgcolor);
        btn.addActionListener(this);
        return btn;
    }

    boolean checkpass(String word) {

        String vowels = "_@&0123456789~!@#$%^&*()_+=-/.,><|][{}]";
        for (char letter : word.toCharArray()) {
            if (vowels.contains(letter + "")) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent en) {
        if (en.getSource() == reset) {
            Password.setText("");
            cPassword.setText("");
            return;

        } else if (en.getSource() == submit) {
            if (cPassword.getText().isEmpty() || Password.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field cannot be Blank!");

                return;

            }
            if (!cPassword.getText().equals(Password.getText())) {
                JOptionPane.showMessageDialog(null, "Passwords dont match!");
                cPassword.setText("");
                Password.setText("");
                return;
            }
            if (!(Password.getText().length() >= 8)) {
                JOptionPane.showMessageDialog(null, "Password Too Short!");
                return;
            }
            if (!checkpass(Password.getText()) && !checkpass(cPassword.getText())) {
                JOptionPane.showMessageDialog(null, "Must Contain Symbools Or Number");
                return;

            }
            String selectIntoTable2 = String.format(
                    "Update studentportal set password='%s' where Phone_Number='%s'and Username='%s' ",
                    Password.getText(),
                    phone, User);
            try {
                st2.execute(selectIntoTable2);
                JOptionPane.showMessageDialog(null, "PASSWORD CHANGED!");
                tick = "done";
                dispose();

            } catch (SQLException sq) {
                System.out.println("Error occured....." + sq.getMessage() + "\tQuery has been terminated");
            }

        }

    }

    @Override
    public void windowActivated(WindowEvent arg0) {

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        if (tick.equalsIgnoreCase("done")) {
            System.err.println("Password Changed!");
            new portallogin(st2);
        } else {
            JOptionPane.showMessageDialog(null, "PASSWORD UNCHANGED!");
            System.err.println("Password Unchanged!");
            new portallogin(st2);
        }

    }

    @Override
    public void windowClosing(WindowEvent arg0) {

    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {

    }

    @Override
    public void windowIconified(WindowEvent arg0) {

    }

    @Override
    public void windowOpened(WindowEvent arg0) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (show.isSelected()) {
            Password.setEchoChar((char) 0);
            cPassword.setEchoChar((char) 0);
        }
        if (hide.isSelected()) {

            cPassword.setEchoChar('*');
            Password.setEchoChar('*');
        }
    }

}
