package DBdata;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.GridLayout;

import java.util.Random;
import java.util.regex.Pattern;

public class SignUp extends JFrame implements ActionListener, ItemListener, WindowListener {

    Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
    Font font;

    JTextField userNameField, firstnameField, lastNameField, Phone_Number;
    JPasswordField passwordField, ConfirmpasswordField;
    JButton submit, clear, Login;
    JRadioButton male, female, show, hide;
    String status, tick = "";
    boolean result = false;
    Statement st2;

    public SignUp() {
        font = new Font("Comic sans", Font.BOLD, 30);
        st2 = Practice_Connector.createStatement();

        JPanel northPanel = new JPanel();

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        add(centerPanel);

        GridLayout gl = new GridLayout(8, 2);
        gl.setVgap(10);
        centerPanel.setLayout(gl);

        // centerPanel.add(createLabel("Aministrative Pin"));
        // adminpin = createTextField();
        // centerPanel.add(adminpin);

        centerPanel.add(createLabel("First Name"));
        firstnameField = createTextField();
        centerPanel.add(firstnameField);

        centerPanel.add(createLabel("Last Name"));
        lastNameField = createTextField();
        centerPanel.add(lastNameField);

        centerPanel.add(createLabel(" Phone Number"));
        Phone_Number = createTextField();
        centerPanel.add(Phone_Number);

        centerPanel.add(createLabel("User Name"));
        userNameField = createTextField();
        centerPanel.add(userNameField);

        centerPanel.add(createLabel("Password"));
        passwordField = createPasswordField();
        centerPanel.add(passwordField);

        JPanel combines = new JPanel();
        combines.setBackground(bgColor);

        show = checkButton("Show");
        combines.add(show);

        hide = checkButton("Hide");
        combines.add(hide);

        ButtonGroup groupie = new ButtonGroup();
        groupie.add(hide);
        groupie.add(show);

        hide.addItemListener(this);
        show.addItemListener(this);

        centerPanel.add(createLabel("ConfirmPassword"));
        ConfirmpasswordField = createPasswordField();
        centerPanel.add(ConfirmpasswordField);
        centerPanel.add(createLabel(""));

        centerPanel.add(combines);

        male = checkButton("male");
        female = checkButton("female");

        JPanel combine = new JPanel();

        combine.setBackground(bgColor);
        combine.add(male);
        combine.add(female);

        centerPanel.add(createLabel("Gender"));
        centerPanel.add(combine);

        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);

        male.addItemListener(this);
        female.addItemListener(this);

        male.setSelected(true);
        hide.setSelected(true);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        add(southPanel, BorderLayout.SOUTH);

        clear = createButton("Reset");
        southPanel.add(clear);

        submit = createButton("Sign up");
        southPanel.add(submit);

        Login = createButton("Login");
        southPanel.add(Login);

        addWindowListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sign Up");
        setVisible(true);
        pack();
        setSize(570, 650);
        setLocationRelativeTo(null);

    }

    private JRadioButton checkButton(String txt) {
        JRadioButton btn = new JRadioButton(txt);
        btn.setForeground(fgColor);
        btn.setBackground(bgColor);
        btn.setFont(font);
        btn.setFont(font);
        return btn;
    }

    private JButton createButton(String txt) {
        JButton btn = new JButton(txt);
        btn.setForeground(fgColor);
        btn.setBackground(bgColor);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.addActionListener(this);

        return btn;
    }

    private JTextField createTextField() {
        JTextField txtField = new JTextField(50);
        txtField.setFont(font);
        txtField.setForeground(fgColor);
        txtField.setBackground(bgColor);
        txtField.setCaretColor(Color.CYAN);

        return txtField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField txtField = new JPasswordField(50);
        txtField.setFont(font);
        txtField.setForeground(fgColor);
        txtField.setBackground(bgColor);
        txtField.setCaretColor(Color.CYAN);

        return txtField;
    }

    private JLabel createLabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(font);
        label.setForeground(fgColor);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
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

    void insert() {
        ResultSet rs;
        try {
            String insertValue = String.format("INSERT INTO administrative VALUES ('%s','%s','%s', '%s', '%s', '%s')",
                    firstnameField.getText(), lastNameField.getText(), Phone_Number.getText(), userNameField.getText(),
                    passwordField.getText(), status);

            st2.execute(insertValue);
            firstnameField.setText("");
            lastNameField.setText("");
            userNameField.setText("");
            passwordField.setText("");
            ConfirmpasswordField.setText("");
            Phone_Number.setText("");
            male.setSelected(true);
            JOptionPane.showMessageDialog(null, "User Registered Sucessfully");

        } catch (SQLException e1) {
            System.out.println("Error occured..." + e1.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Login) {
            tick = "done";
            dispose();

        }
        if (e.getSource() == clear) {
            firstnameField.setText("");
            lastNameField.setText("");
            userNameField.setText("");
            passwordField.setText("");
            ConfirmpasswordField.setText("");
            Phone_Number.setText("");
            male.setSelected(true);

        } else if (e.getSource() == submit) {

            if (userNameField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || Phone_Number.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field Cannot Be Empty!");
                return;
            }

            Boolean res = Pattern.matches("(0||\\+234)[7-9][01]\\d{8}", Phone_Number.getText());
            if (res == false) {
                JOptionPane.showMessageDialog(null, "INCORRECT PHONE NUMBER!");
                return;
            }
            if (!ConfirmpasswordField.getText().equals(passwordField.getText())) {
                JOptionPane.showMessageDialog(null, "Passwors does not match!!");
                passwordField.setText("");
                ConfirmpasswordField.setText("");
                return;
            }

            if (!(passwordField.getText().length() >= 8)) {
                JOptionPane.showMessageDialog(null, "Password Too Short!");
                return;
            }
            if (!checkpass(passwordField.getText()) && !checkpass(ConfirmpasswordField.getText())) {
                JOptionPane.showMessageDialog(null, "Hint: Must Contain Symbools Or Number");
                return;

            }

            String selectIntoTable1 = String.format("SELECT * FROM administrative");
            String match, matches;
            ResultSet rs;
            try {
                rs = st2.executeQuery(selectIntoTable1);
                while (rs.next()) {
                    match = rs.getString("Phone_Number");
                    if ((Phone_Number.getText()).equalsIgnoreCase(match)) {
                        Phone_Number.setText("");
                        JOptionPane.showMessageDialog(null, "PHONE NUMBER HAS BEEN REGISTERED TO ANOTHER USER");
                        return;
                    }
                    matches = rs.getString("Username");
                    if ((userNameField.getText()).equalsIgnoreCase(matches)) {
                        userNameField.setText("");
                        JOptionPane.showMessageDialog(null, "Username exists!");
                        return;

                    }

                }
                insert();
                tick = "done";
                dispose();

            } catch (SQLException e1) {
                System.out.println("Error occured..." + e1.getMessage());
            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (male.isSelected()) {
            status = "male";
        }
        if (female.isSelected()) {
            status = "Female";
        }
        if (show.isSelected()) {
            passwordField.setEchoChar((char) 0);
            ConfirmpasswordField.setEchoChar((char) 0);

        }
        if (hide.isSelected()) {
            passwordField.setEchoChar('*');
            ConfirmpasswordField.setEchoChar('*');

        }

    }

    public static void main(String[] args) {
        new SignUp();

    }

    @Override
    public void windowActivated(WindowEvent arg0) {

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
        if (tick.equalsIgnoreCase("done")) {
            new login(st2);
        } else {
            System.out.println("GUI TERMINATED!");
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
