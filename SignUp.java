
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import java.sql.Connection;
import java.util.Random;
import java.util.regex.Pattern;

public class SignUp extends JFrame implements ActionListener, ItemListener {

    Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
    Font font;

    JTextField userNameField, firstnameField, lastNameField, Phone_Number, adminpin;
    JPasswordField passwordField, ConfirmpasswordField;
    JButton submit, clear;
    JRadioButton male, female;
    String status;
    Statement st2;

    public SignUp() {
        font = new Font("Sans Seriff", Font.BOLD, 30);
        st2 = Connector.createStatement();

        JPanel northPanel = new JPanel();
        // add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(bgColor);
        add(centerPanel);

        GridLayout gl = new GridLayout(8, 2);
        gl.setVgap(10);
        centerPanel.setLayout(gl);

        centerPanel.add(createLabel("Aministrative Pin"));
        adminpin = createTextField();
        centerPanel.add(adminpin);

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

        centerPanel.add(createLabel("ConfirmPassword"));
        ConfirmpasswordField = createPasswordField();
        centerPanel.add(ConfirmpasswordField);

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
        male.setSelected(true);
        male.addItemListener(this);
        female.addItemListener(this);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        add(southPanel, BorderLayout.SOUTH);

        clear = createButton("Reset");
        southPanel.add(clear);

        submit = createButton("Sign up");
        southPanel.add(submit);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Sign Up");
        setVisible(true);
        pack();
        setSize(570, 600);
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

    void adminpin() {
        Random rand = new Random();
        int addpin = rand.nextInt(100000, 999999);
        System.out.println(addpin);
        String insertval=String.format("insert into acess values(%s,%s)",firstnameField.getText(),addpin);
        ResultSet rs;
        try{
            rs=st2.executeQuery(insertval);
            System.out.println("Query Executed!");
            System.out.println("YOUR PIN HAS BEEN GENERATED");

        }catch(SQLException sq){


        }


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clear) {
            firstnameField.setText("");
            lastNameField.setText("");
            userNameField.setText("");
            passwordField.setText("");
            Phone_Number.setText("");
            male.setSelected(true);

        } else if (e.getSource() == submit) {

            if (userNameField.getText().isEmpty() || passwordField.getText().isEmpty()
                    || Phone_Number.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Field Cannot Be Empty!");
                return;
            }
            if (!ConfirmpasswordField.getText().equals(passwordField.getText())) {
                JOptionPane.showMessageDialog(null, "Passwors does not match!!");
                passwordField.setText("");
                ConfirmpasswordField.setText("");
                return;
            }

            Boolean res = Pattern.matches("\\+234||0[7-9][0-1][0-9]{8}", Phone_Number.getText());
            if (res == false) {
                JOptionPane.showMessageDialog(null, "INCORRECT PHONE NUMBER!");
                return;
            }

            String insertValue = String.format("INSERT INTO login VALUES ('%s','%s','%s', '%s', '%s')",
                    firstnameField.getText(), lastNameField.getText(), Phone_Number.getText(), userNameField.getText(),
                    passwordField.getText(), status);

            String selectIntoTable1 = String.format("SELECT Phone_Number FROM administrative");
            String match, matches;
            ResultSet rs;
            try {
                rs = st2.executeQuery(selectIntoTable1);
                while (rs.next()) {
                    match = rs.getString("Phone_Number");

                    if (((String) Phone_Number.getText()).equalsIgnoreCase(match)) {
                        JOptionPane.showMessageDialog(null, "PHONE NUMBER HAS BEEN REGISTERED TO ANOTHER USER");
                        return;
                    }

                }
                st2.execute(insertValue);
            } catch (SQLException e1) {
                System.out.println("Error occured..." + e1.getMessage());
            }
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (male.isSelected()) {
            status = "male";
        } else if (female.isSelected()) {
            status = "Female";
        } else {
            status = null;
        }

    }

    public static void main(String[] args) {
        new SignUp();

    }

}
