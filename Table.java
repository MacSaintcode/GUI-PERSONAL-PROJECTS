import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JFrame {
	Statement st2 = Connector.createStatement();
	String FirstName;
	String LastName;
	String UserName;
	String Password;
	String Gender;
	

	Table() {

		setTitle("Table Data");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		

		String[] Columns = { " FirstName ", " LastName ","Gender", " UserName ", " Password "};

		DefaultTableModel DM = new DefaultTableModel(Columns, 0);
		JTable JT = new JTable(DM);
		JT.setBackground(Color.white);
		JT.setForeground(Color.black);
		JScrollPane scroll = new JScrollPane(JT);
		add(scroll);
		setVisible(true);
		
		String selectIntoTable = String.format("SELECT * FROM login");
		try {
			ResultSet rs = st2.executeQuery(selectIntoTable);
			while (rs.next()) {
				FirstName = rs.getString("FirstName");
				LastName = rs.getString("LastName");
				Gender = rs.getString("Gender");
				UserName = rs.getString("UserName");
				Password = rs.getString("Password");
				

				String[] tablearr = { FirstName, LastName, Gender,UserName, Password};
				DM.addRow(tablearr);
			}
		} catch (SQLException sqe) {
			System.out.println(sqe.getMessage());
		}

	}

	public static void main(String[] args) {
		new Table();
	}
}