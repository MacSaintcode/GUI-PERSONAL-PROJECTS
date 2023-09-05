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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EnterPassword extends JFrame implements ActionListener, ItemListener {

    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    JButton reset, submit;
    JTextField Reg_num;
    JComboBox<String> department, faculty;
    Font font = new Font("Sans Seriff", Font.BOLD, 20);
    Statement st2;

    EnterPassword() {
        st2 = Practice_Connector.createStatement();

        JPanel centerpanel = new JPanel();
        centerpanel.setBackground(bgcolor);
        add(centerpanel);

        GridLayout gl = new GridLayout(3, 2);
        gl.setVgap(10);
        centerpanel.setLayout(gl);

        centerpanel.add(createlabel("Reg Number"));
        Reg_num = createtextfield();
        centerpanel.add(Reg_num);

        centerpanel.add(createlabel("Facaulty"));
        faculty = createjbox();
        centerpanel.add(faculty);
        faculty.addItemListener(this);

        centerpanel.add(createlabel("Department"));
        department = createjbox();
        centerpanel.add(department);

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.gray);
        add(southpanel, BorderLayout.SOUTH);

        reset = createbutton("Reset");
        southpanel.add(reset);

        submit = createbutton("Submit");
        southpanel.add(submit);

        generate();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Registeration");
        pack();
        setSize(500, 300);
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

    private JComboBox<String> createjbox() {
        JComboBox<String> box = new JComboBox<String>();
        box.setBackground(bgcolor);
        box.setForeground(fgcolor);
        box.setFont(font);

        return box;
    }

    JTextField createtextfield() {
        JTextField field = new JTextField();
        field.setBackground(bgcolor);
        field.setForeground(fgcolor);
        field.setFont(font);

        return field;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reset) {
            Reg_num.setText("");
            department.setSelectedItem("");
            faculty.setSelectedItem("CHOSSE A FACAULTY");
            return;

        } else if (e.getSource() == submit) {
            if (Reg_num.getText().isEmpty() || department.getSelectedItem().equals("")
                    || faculty.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(null, "Field cannot be Blank!");
                return;

            }

            String selectIntoTable1 = String.format("SELECT Registration_Number FROM Identity");
            String match, matches;
            ResultSet rs;
            try {
                rs = st2.executeQuery(selectIntoTable1);
                while (rs.next()) {
                    match = rs.getString("Registration_Number");
                    if (((String) Reg_num.getText()).equalsIgnoreCase(match)) {
                        selectIntoTable1 = String.format(
                                "SELECT lastname FROM register where Registration_Number = '%s' ", Reg_num.getText());
                        rs = st2.executeQuery(selectIntoTable1);
                        while (rs.next()) {
                            matches = rs.getString("Lastname");
                            JOptionPane.showMessageDialog(null, "YOU HAVE BEEN REGISTERED " + matches.toUpperCase() + "!");
                            Reg_num.setText("");
                            return;
                        }
                    } else if (rs.isLast()) {
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error occured....." + ex.getMessage() + "\tQuery has been terminated");
            }

            String InputQuery = String.format("INSERT INTO identity VALUES('%s','%s','%s','%s')",
                    Reg_num.getText(), null, (String) faculty.getSelectedItem(), (String) department.getSelectedItem());

            String selectIntoTable = String.format("SELECT Matric_Number FROM Identity");

            String matric = null;
            String matrics = null;

            try {
                String fa = (String) faculty.getSelectedItem();
                String da = (String) department.getSelectedItem();

                rs = st2.executeQuery(selectIntoTable);
                while (true) {
                    boolean res = false;
                    Random num = new Random();

                    String fl = fa.substring(0, 2).toUpperCase();
                    String ll = da.substring(0, 2).toUpperCase();
                    int fg = num.nextInt(100000);

                    matric = fl + String.valueOf(fg) + ll;

                    while (rs.next()) {
                        matrics = rs.getString("Matric_Number");

                        if (matrics.equalsIgnoreCase(matric)) {
                            res = true;
                            break;
                        } else if (rs.isLast()) {
                            break;
                        } else {
                            continue;
                        }

                    }
                    if (res = true) {
                        break;
                    } else {
                        continue;
                    }
                }
            } catch (SQLException sq) {
                System.out.println("Error occured....." + sq.getMessage() + "\tQuery has been terminated");
            }

            String InputQuery2 = String.format(
                    "UPDATE identity SET Matric_Number = '%s' WHERE Registration_Number = '%s' ", (String) matric,
                    (String) Reg_num.getText());
            try {
                st2.execute(InputQuery);
                System.out.println("Query Executed Sucessfully");
                st2.execute(InputQuery2);
                JOptionPane.showMessageDialog(null, "Registered Sucessfully!! ");
                JOptionPane.showMessageDialog(null, "Your Matric Number is" + matric);
                System.out.println("Query Executed Sucessfully");
                Reg_num.setText("");
                department.setSelectedItem("");
                faculty.setSelectedItem("");
                return;

            } catch (SQLException sq) {
                System.out.println("Error occured....." + sq.getMessage() + "\tQuery has been terminated");
            }
        }
    }

    void generate() {
        String gots;
        faculty.removeAllItems();
        faculty.addItem("CHOOSE A FACAULTY");
        String selectIntoTable = String.format("SELECT Facaultyname FROM Facaulty");
        try {
            ResultSet rs = st2.executeQuery(selectIntoTable);
            while (rs.next()) {
                gots = rs.getString("Facaultyname");
                System.out.println(gots);
                faculty.addItem(gots);
            }
            System.out.println("Completed");
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        String select = (String) faculty.getSelectedItem();
        // JComboBox comboBox = (JComboBox) e.getSource();
        String got;

        department.removeAllItems();
        if (select.equalsIgnoreCase("CHOOSE A FACAULTY")) {

        } else {
            String selectIntoTable = String.format("SELECT %s FROM department", select);
            try {
                ResultSet rs = st2.executeQuery(selectIntoTable);
                while (rs.next()) {
                    got = rs.getString(select);
                    department.addItem(got);
                }
            } catch (SQLException sqe) {
                System.out.println(sqe.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new EnterPassword();

    }

}
