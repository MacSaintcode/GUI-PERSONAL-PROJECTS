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
    static String User, phone, fin = "", wher = "";
    int my_pin, count = 5;;
    boolean got = false;
    Font font = new Font("Comic sans", Font.BOLD, 20);
    Statement st2;

    PinEntry(String use, String phon, Statement st, String caugth) {
        User = use;
        phone = phon;
        st2 = st;
        wher = caugth;

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
        } else {
            JOptionPane.showMessageDialog(null, "Your 6-Digit OTP is " + my_pin);
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
        my_pin = rand.nextInt(100000, 999999);
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

                    got = true;
                }

            }

        } catch (SQLException ea) {
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
            Boolean res = Pattern.matches("\\d{6}", pin.getText());

            if (res == false) {
                if (!(pin.getText().length() == 6)) {
                    JOptionPane.showMessageDialog(null, "Enter a 6-digits Pin");

                }
                if ((pin.getText().length() == 6)) {
                    JOptionPane.showMessageDialog(null, "INVALID PIN!");
                }
                count--;
                if (count == 0) {
                    JOptionPane.showMessageDialog(null, "You Are Out of Tries");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "You Have " + count + " More Tries");
                    return;
                }
                pin.setText("");
            }

            ResultSet rs;
            String gotpin;
            String getpin = String.format("select Administrative_Pin from acess where Username='%s'", User);

            String deletefromTable = String.format(
                    "delete from acess where Username='%s' ", User);
            boolean grt = false;
            try {
                rs = st2.executeQuery(getpin);
                while (rs.next()) {
                    gotpin = rs.getString("Administrative_Pin");
                    if (gotpin.equals(pin.getText())) {
                        grt = true;
                        st2.execute(deletefromTable);
                        System.out.println("Pin Expired!");
                        fin = "done";
                        dispose();

                    }
                }

                if (grt == false) {
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
        if (fin.equalsIgnoreCase("done")) {
            if (wher.equals("portal")) {
                new confirmportalpass(User, phone, st2);

            } else if (wher.equalsIgnoreCase("admin")) {
                new changepassconfirm(User, phone, st2);
            }

        } else {
            System.out.println("OTP Terminated");
            ResultSet rs;
            String gotpin;
            String getpin = String.format("select Administrative_Pin from acess where Username='%s'", User);

            String deletefromTable = String.format(
                    "delete from acess where Username='%s' ", User);
            try {
                rs = st2.executeQuery(getpin);
                while (rs.next()) {
                    gotpin = rs.getString("Administrative_Pin");
                    if (gotpin.equals(pin.getText())) {
                        st2.execute(deletefromTable);
                        System.out.println("Pin Expired!");

                    }
                }
            } catch (SQLException ea) {
                System.err.println("Query Terminated " + ea.getMessage());
            }
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