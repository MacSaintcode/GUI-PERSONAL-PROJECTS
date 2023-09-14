package DBdata;

//import DBdata.EnterPassword;
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
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.util.Objects;

public class Input_Practice extends JFrame implements ActionListener, ItemListener, WindowListener {

    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    Font font = new Font("Comic sans", Font.BOLD, 30);
    JTextField firstnamefield, lastnamefield, Other_Names, Date_Of_Birth, Reg_num, Phone_Number;
    JButton submit, reset;
    JRadioButton male, female;
    String tick = "", Gender = "Male", regnum;

    public Statement st2 = Practice_Connector.createStatement();

    public Input_Practice() {

        JPanel centerpanel = new JPanel();
        centerpanel.setBackground(bgcolor);
        add(centerpanel);

        GridLayout gl = new GridLayout(6, 2);
        gl.setVgap(10);
        centerpanel.setLayout(gl);

        centerpanel.add(createLabel("Firstname*"));
        firstnamefield = createtextfield();
        centerpanel.add(firstnamefield);

        centerpanel.add(createLabel("Lastname*"));
        lastnamefield = createtextfield();
        centerpanel.add(lastnamefield);

        centerpanel.add(createLabel(" Other Names"));
        Other_Names = createtextfield();
        centerpanel.add(Other_Names);

        centerpanel.add(createLabel("Date Of Birth*"));
        Date_Of_Birth = createtextfield();
        Date_Of_Birth.setText("YYYY-MM-DD");
        centerpanel.add(Date_Of_Birth);

        centerpanel.add(createLabel("Reg Number*"));
        Reg_num = createtextfield();
        centerpanel.add(Reg_num);

        JPanel genderpane = new JPanel();
        genderpane.setBackground(bgcolor);

        male = createradiobutton("Male");
        female = createradiobutton("Female");

        male.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);

        genderpane.add(male);
        genderpane.add(female);

        centerpanel.add(createLabel("Gender*"));
        centerpanel.add(genderpane);

        male.addItemListener(this);
        female.addItemListener(this);
        addWindowListener(this);

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.DARK_GRAY);

        add(southpanel, BorderLayout.SOUTH);

        reset = createbutton("RESET");
        southpanel.add(reset);

        submit = createbutton("SUBMIT");
        southpanel.add(submit);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("DETAILS MENU");
        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    JRadioButton createradiobutton(String txt) {
        JRadioButton btn = new JRadioButton(txt);
        btn.setFont(font);
        btn.setBackground(bgcolor);
        btn.setForeground(fgcolor);
        btn.setFocusable(false);
        return btn;

    }

    JTextField createtextfield() {
        JTextField txtfield = new JTextField(50);
        txtfield.setFont(font);
        txtfield.setBackground(bgcolor);
        txtfield.setForeground(fgcolor);
        txtfield.setCaretColor(Color.yellow);
        return txtfield;
    }

    JLabel createLabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(font);
        label.setForeground(fgcolor);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    JButton createbutton(String txt) {
        JButton btnr = new JButton(txt);
        btnr.setFont(font);
        btnr.setForeground(fgcolor);
        btnr.setBackground(bgcolor);
        btnr.setFocusable(false);
        btnr.addActionListener(this);
        return btnr;
    }

    @Override
    public void itemStateChanged(ItemEvent ea) {
        if (male.isSelected()) {
            Gender = "Male";
        } else if (female.isSelected()) {
            Gender = "Female";
        } else {
            Gender = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent eo) {
        if (eo.getSource() == reset) {

            firstnamefield.setText("");
            lastnamefield.setText("");
            Other_Names.setText("");
            Date_Of_Birth.setText("YYYY-MM-DD");
            Reg_num.setText("");
            male.setSelected(true);
            Gender = null;

        } else if (eo.getSource() == submit) {
            if (firstnamefield.getText().isEmpty() || lastnamefield.getText()
                    .isEmpty() || Date_Of_Birth.getText().isEmpty() || Gender == null || Reg_num.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "FIELD CANNOT BE EMPTY!");
                return;
            }

            if (Other_Names.getText().isEmpty()) {
                Other_Names.setText(null);
            }

            Boolean res = Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", Date_Of_Birth.getText());
            if (res == false) {
                JOptionPane.showMessageDialog(null, "INCORRECT DATE FORMAT");
                Date_Of_Birth.setText("YYYY-MM-DD");
                return;
            }
            if (!(Reg_num.getText().length() == 8)) {
                JOptionPane.showMessageDialog(null, "Invalid Registration Number!");
                Reg_num.setText("");
                return;
            }
            Boolean resu = Pattern.matches("[0-9]{8}", Reg_num.getText());
            if (resu == false) {
                JOptionPane.showMessageDialog(null, "Invalid Registration Number!");
                Reg_num.setText("");
                return;
            }
            String selectIntoTable1 = String.format("SELECT Registration_Number FROM register");
            String match, matches;
            ResultSet rs;
            try {
                rs = st2.executeQuery(selectIntoTable1);
                while (rs.next()) {
                    match = rs.getString("Registration_Number");

                    if (((String) Reg_num.getText()).equalsIgnoreCase(match)) {
                        JOptionPane.showMessageDialog(null,
                                "THIS REGISTRATION NUMBER HAS BEEN ASSIGNED TO ANOTHER STUDENT!");
                        Reg_num.setText("");
                        return;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error occured....." + ex.getMessage() + "\tQuery has been terminated");
            }

            String InputQuery = String.format("INSERT INTO Register VALUES('%s','%s','%s','%s','%s','%s')",
                    firstnamefield.getText(), lastnamefield.getText(), Other_Names.getText(), Date_Of_Birth.getText(),
                    Gender,
                    Reg_num.getText());

            try {
                st2.execute(InputQuery); 
                System.out.println("Query Executed Sucessfully");
                regnum = Reg_num.getText();
                firstnamefield.setText("");
                lastnamefield.setText("");
                Date_Of_Birth.setText("YYYY-MM-DD");
                Other_Names.setText("");
                male.setSelected(true);
                Reg_num.setText("");
                tick = "done";
                dispose();
            } catch (SQLException sq) {
                System.out.println("Error occured....." + sq.getMessage() + "\tQuery has been terminated");
                JOptionPane.showMessageDialog(null, "Software Crashed!", "Error Occured!", 0);

            } catch (NullPointerException po) {
                JOptionPane.showMessageDialog(null, "Server Not Online Please Rectify it!", "Error Occured!", 0);
                return;
            }

        }

    }

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("Registration portal Opened");
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        if (tick.equalsIgnoreCase("done")) {

            new EnterPassword(regnum);
        } else {
            System.err.println("GUI TERMINATED!");
        }
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

    public static void main(String[] args) {
        new Input_Practice();

    }
}
