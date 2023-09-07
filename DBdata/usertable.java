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

public class usertable extends JFrame implements ActionListener {
    Statement st2 = Practice_Connector.createStatement();
    String Phone_num, FirstName, LastName, Gender, User, Password;
    JTextField Search;
    JComboBox colname;
    JButton Searching, Refresh;
    Font font = new Font("Comic sans", Font.BOLD, 12);
    String[] Columns = { " FirstName ", " LastName ",
            "Phone Number", "Username", "Password", "Gender" };
    String[] col = { "Username", "Lastname", "Firstname", "Phone_Number" };
    DefaultTableModel DM = new DefaultTableModel(Columns, 0);

    usertable() {
        setTitle("Table Data");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(null);
        GridLayout gl = new GridLayout(0, 6);
        gl.setVgap(20);

        JPanel northpanel = new JPanel();
        add(northpanel, BorderLayout.NORTH);
        northpanel.setBackground(Color.DARK_GRAY);

        northpanel.setLayout(gl);
        Refresh = createbutton("Refresh");
        northpanel.add(Refresh);
        northpanel.add(createlabel("Search By >"));
        colname = createbox(col);
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

        String selectIntoTable = "SELECT * from administrative";
        try {
            ResultSet rs = st2.executeQuery(selectIntoTable);
            while (rs.next()) {

                FirstName = rs.getString("FirstName");
                LastName = rs.getString("LastName");
                Gender = rs.getString("Gender");
                Phone_num = rs.getString("Phone_Number");
                User = rs.getString("Username");
                Password = rs.getString("Password");

                String[] tablearr = { FirstName, LastName, Phone_num, User, Password, Gender
                };
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
        if (click.getSource() == Refresh) {
            int count = DM.getRowCount();
            int i = count - 1;
            while (i >= 0) {
                DM.removeRow(i);
                i--;
            }

            String selectIntoTable = "SELECT * from administrative";
            try {
                ResultSet rs = st2.executeQuery(selectIntoTable);
                while (rs.next()) {

                    FirstName = rs.getString("FirstName");
                    LastName = rs.getString("LastName");
                    Gender = rs.getString("Gender");
                    Phone_num = rs.getString("Phone_Number");
                    User = rs.getString("Username");
                    Password = rs.getString("Password");

                    String[] tablearr = { FirstName, LastName, Phone_num, User, Password, Gender
                    };
                    DM.addRow(tablearr);
                }
            } catch (SQLException sqe) {
                System.out.println(sqe.getMessage());
            }
        }
        if (click.getSource() == Searching) {
            if (select.equals("") || Search.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "COMPLETE THE SEARCH REQUIREMENT!");
                return;

            }

            int count = DM.getRowCount();
            int i = count - 1;
            while (i >= 0) {
                DM.removeRow(i);
                i--;
            }

            String selectIntoTable = String.format(
                    "SELECT * from administrative where %s='%s' ",
                    (String) colname.getSelectedItem(), (String) Search.getText());

            try {
                ResultSet rs = st2.executeQuery(selectIntoTable);
                while (rs.next()) {

                    FirstName = rs.getString("FirstName");
                    LastName = rs.getString("LastName");
                    Gender = rs.getString("Gender");
                    Phone_num = rs.getString("Phone_Number");
                    User = rs.getString("Username");
                    Password = rs.getString("Password");

                    String[] tablearr = { FirstName, LastName, Phone_num, User, Password, Gender };
                    DM.addRow(tablearr);
                    Search.setText("");

                }
            } catch (SQLException sqe) {
                System.out.println(sqe.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        new usertable();
    }

}
