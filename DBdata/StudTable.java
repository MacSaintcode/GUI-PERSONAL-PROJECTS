package DBdata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudTable extends JFrame implements ActionListener {

    Statement st2 = Practice_Connector.createStatement();
    String Reg_num, FirstName, LastName, Others, DOB, Gender, Facaulty, Department, Matric_Number;
    JTextField Search;
    JComboBox colname;
    JButton Searching;
    Font font = new Font("Comic sans", Font.BOLD, 12);
    String[] Columns = { " FirstName ", " LastName ", " Other Name", " Date Of Birth ", "Gender",
            "Registeration Number", "Facaulty", "Department", "Matric Number" };
    DefaultTableModel DM = new DefaultTableModel(Columns, 0);

    StudTable() {

        setTitle("Table Data");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(null);
        GridLayout gl = new GridLayout(0, 5);
        gl.setVgap(20);

        JPanel northpanel = new JPanel();
        add(northpanel, BorderLayout.NORTH);
        northpanel.setBackground(Color.DARK_GRAY);

        northpanel.setLayout(gl);
        northpanel.add(createlabel("Search By >"));
        colname = createbox(Columns);
        northpanel.add(colname);

        northpanel.add(createlabel("Search Box>"));
        Search = creattextField();
        northpanel.add(Search);

        Searching = createbutton(".Search.");
        northpanel.add(Searching);

        JTable JT = new JTable(DM);
        JT.setBackground(Color.white);
        JT.setForeground(Color.black);
        JT.setFont(font);
        JScrollPane scroll = new JScrollPane(JT);
        add(scroll);
        setVisible(true);

        String selectIntoTable = "SELECT register.Firstname,register.lastname,register.Other_names,register.Date_of_Birth,register.Gender,register.Registration_Number,identity.facaulty,identity.department,identity.Matric_Number FROM register inner join identity on register.Registration_Number=identity.Registration_Number";
        try {
            ResultSet rs = st2.executeQuery(selectIntoTable);
            while (rs.next()) {
                FirstName = rs.getString("FirstName");
                LastName = rs.getString("LastName");
                Others = rs.getString("Other_names");
                DOB = rs.getString("Date_of_Birth");
                Gender = rs.getString("Gender");
                Reg_num = rs.getString("Registration_Number");
                Facaulty = rs.getString("Facaulty");
                Department = rs.getString("Department");
                Matric_Number = rs.getString("Matric_Number");

                String[] tablearr = { FirstName, LastName, Others, DOB, Gender, Reg_num, Facaulty, Department,
                        Matric_Number };
                DM.addRow(tablearr);
            }
        } catch (SQLException sqe) {
            System.out.println(sqe.getMessage());
        }

    }

    JLabel createlabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setAlignmentX(20);
        label.setFont(font);
        label.setForeground(Color.yellow);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    JTextField creattextField() {
        JTextField text = new JTextField();
        text.setCaretColor(Color.blue);
        text.setFont(font);
        text.setBackground(Color.white);
        text.setForeground(Color.black);
        return text;
    }

    JComboBox createbox(String arr[]) {
        JComboBox box = new JComboBox(arr);
        box.setBackground(Color.gray);
        box.setForeground(Color.yellow);
        box.setFont(font);

        return box;

    }

    JButton createbutton(String txt) {
        JButton btn = new JButton(txt);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setBackground(Color.black);
        btn.setForeground(Color.yellow);
        btn.addActionListener(this);
        return btn;
    }
  
    @Override
    public void actionPerformed(ActionEvent click) {
        String select = (String) colname.getSelectedItem();
        if (click.getSource() == Searching) {
            if (select.equals("") || Search.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "COMPLETE THE SEARCH REQUIREMENT!");
                return;

            }
            String selectIntoTable = String.format(
                    "SELECT register.Firstname,register.lastname,register.Other_names,register.Date_of_Birth,register.Gender,register.Registration_Number,identity.facaulty,identity.department,identity.Matric_Number FROM register inner join identity on register.Registration_Number=identity.Registration_Number where '%s'='%s' ",
                    (String) colname.getSelectedItem(), (String) Search.getText());

            try {
                ResultSet rs = st2.executeQuery(selectIntoTable);
                while (rs.next()) {
                    FirstName = rs.getString("FirstName");
                    LastName = rs.getString("LastName");
                    Others = rs.getString("Other_names");
                    DOB = rs.getString("Date_of_Birth");
                    Gender = rs.getString("Gender");
                    Reg_num = rs.getString("Registration_Number");
                    Facaulty = rs.getString("Facaulty");
                    Department = rs.getString("Department");
                    Matric_Number = rs.getString("Matric_Number");

                    String[] tablearr = { FirstName, LastName, Others, DOB, Gender, Reg_num, Facaulty, Department,
                            Matric_Number };
                    DM.addRow(tablearr);
                    
                }
            } catch (SQLException sqe) {
                System.out.println(sqe.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        new StudTable();
    }

}
