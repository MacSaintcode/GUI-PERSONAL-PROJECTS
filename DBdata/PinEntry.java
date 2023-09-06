package DBdata;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PinEntry extends JFrame implements ActionListener, WindowListener {
    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    JButton reset, submit;
    JTextField pin;
    static String User, phone, pass;
    boolean got = false;
    Font font = new Font("Comic sans", Font.BOLD, 20);
    Statement st2;

    PinEntry() {
        st2 = Practice_Connector.createStatement();

        JPanel centerpanel = new JPanel();
        centerpanel.setBackground(bgcolor);
        add(centerpanel);

        GridLayout gl = new GridLayout(3, 1);
        gl.setVgap(10);
        centerpanel.setLayout(gl);

        centerpanel.add(createlabel("Enter Your 6-Digit OTP"));
        pin = createtextfield();
        centerpanel.add(pin);

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.gray);
        add(southpanel, BorderLayout.SOUTH);

        reset = createbutton("Reset");
        southpanel.add(reset);

        submit = createbutton("Confirm");
        southpanel.add(submit);

        Generatepin();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Registeration");
        pack();
        setSize(320, 240);
        setLocationRelativeTo(null);
        addWindowListener(this);
        setVisible(true);
        if (got == false) {
            JOptionPane.showMessageDialog(null, "User Does not Exist!");
            dispose();
        }
    }

    JTextField createtextfield() {
        JTextField field = new JTextField();
        field.setBackground(bgcolor);
        field.setForeground(fgcolor);
        field.setFont(font);
        return field;
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

    void Generatepin() {
        System.out.println(User);

        Random rand = new Random();
        int my_pin = rand.nextInt(100000, 999999);
        String putpin = String.format("insert into acess VALUES ('%s','%s')", User, my_pin);
        String search = String.format("Select username from administrative");
        ResultSet rs;

        try {
            rs = st2.executeQuery(search);
            while (rs.next()) {
                String usern = rs.getString("Username");
                if (usern.equals(User)) {
                    st2.execute(putpin);
                    System.out.println("Query Executed Sucessfully!");
                    Thread.sleep(5000);
                    JOptionPane.showMessageDialog(null, "Your 6-Digit OTP is " + putpin);
                    got = true;
                }

            }

        } catch (SQLException ea) {
            System.err.println("Query Terminated " + ea.getMessage());
        } catch (InterruptedException ea) {
            System.err.println("Query Terminated " + ea.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            pin.setText("");

        } else if (e.getSource() == submit) {

            if (pin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field Cannot Be Blank!");
                return;

            }
            Boolean res = Pattern.matches("[0-9]{6}", pin.getText());
            if (res == false) {
                JOptionPane.showMessageDialog(null, "INVALID PIN!");
                pin.setText("");
                return;

            }

            ResultSet rs;
            String gotpin;
            String getpin = String.format("select Administrative_Pin from acess where Username='%s'", User);

            String selectIntoTable2 = String.format(
                    "Update Table Administrative set password='%s' where Phone_Number='%s'and Username='%s' ", pass,
                    phone, User);

            String deletefromTable = String.format(
                    "delete from acess where Username='%s' ", User);

            try {
                rs = st2.executeQuery(getpin);
                gotpin = rs.getString("Administrative_Pin");

                if (gotpin.equals(pin.getText())) {
                    st2.execute(selectIntoTable2);
                    JOptionPane.showMessageDialog(null, "PASSWORD CHANGED!");
                    st2.execute(deletefromTable);
                    System.out.println("Pin Expired!");
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "INVALID PIN!");
                    pin.setText("");
                    return;

                }

            } catch (SQLException ea) {
                System.err.println("Query Terminated " + ea.getMessage());
            }
        }

    }

    @Override
    public void windowActivated(WindowEvent arg0) {

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        System.out.println("OTP Terminated");
        new login();

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

    public static void main(String[] args) {
        new PinEntry();

    }
}