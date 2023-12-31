package DBdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class changepassword extends JFrame implements ActionListener, WindowListener {

    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    Font font = new Font("Comic sans", Font.BOLD, 30);
    JTextField Phone_Number, Username;
    JButton submit, reset;
    String tick = "", User, phone,from="admin";
    Statement st2;

    changepassword(Statement st) {
        st2 = st;
        GridLayout gl = new GridLayout(4, 1);
        JPanel centerpanel = new JPanel();
        centerpanel.setFont(font);
        centerpanel.setBackground(bgcolor);
        centerpanel.setForeground(fgcolor);
        gl.setVgap(10);

        add(centerpanel);
        centerpanel.setLayout(gl);

        centerpanel.add(createlabel("Username"));
        Username = createtextfield();
        centerpanel.add(Username);

        centerpanel.add(createlabel("Phone Number"));
        Phone_Number = createtextfield();
        centerpanel.add(Phone_Number);

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

    JButton createbutton(String txt) {
        JButton btn = new JButton(txt);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setBackground(bgcolor);
        btn.setForeground(fgcolor);
        btn.addActionListener(this);
        return btn;
    }

    JLabel createlabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(font);
        label.setForeground(fgcolor);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    JTextField createtextfield() {
        JTextField field = new JTextField();
        field.setBackground(bgcolor);
        field.setForeground(fgcolor);
        field.setFont(font);

        return field;
    }

    JPasswordField createpassfield() {
        JPasswordField pass = new JPasswordField();
        pass.setBackground(bgcolor);
        pass.setForeground(fgcolor);
        pass.setFont(font);

        return pass;
    }

    @Override
    public void actionPerformed(ActionEvent es) {
        if (es.getSource() == reset) {
            Phone_Number.setText("");
            Username.setText("");
            return;

        } else if (es.getSource() == submit) {
            if (Username.getText().isEmpty() || Phone_Number.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field cannot be Blank!");

                return;

            }
            Boolean res = Pattern.matches("(0||\\+234)[7-9][01]\\d{8}", Phone_Number.getText());
            if (res == false) {
                JOptionPane.showMessageDialog(null, "INCORRECT PHONE NUMBER!");
                return;
            }

            String selectIntoTable = String.format(
                    "SELECT Username,Phone_Number FROM administrative where Phone_Number='%s' ",
                    Phone_Number.getText());

            String match, matches;
            Boolean got = false;
            ResultSet rs;
            try {
                rs = st2.executeQuery(selectIntoTable);
                while (rs.next()) {
                    matches = rs.getString("Username");
                    match = rs.getString("Phone_Number");

                    if (((String) Phone_Number.getText()).equalsIgnoreCase(match)
                            && Username.getText().equals(matches)) {
                        got = true;
                        tick = "done";
                        User = Username.getText();
                        phone = Phone_Number.getText();
                        Phone_Number.setText("");
                        Username.setText("");
                        dispose();
                    }
                }
                if (got.equals(false)) {
                    JOptionPane.showMessageDialog(null, "USER DOES NOT EXIST!");
                    Phone_Number.setText("");
                    Username.setText("");
                    return;
                }

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
            PinEntry call = new PinEntry(User, phone, st2,from);

        } else {
            System.err.println("Password Unchanged!");
            new login(st2);
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
}