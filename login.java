import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.sql.Connection;

public class login extends JFrame implements ActionListener {

	Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
	Font font;

	JTextField userNameField;
	JPasswordField passwordField;
	JButton submit, clear;
	Statement st2;

	public login() {

		font = new Font("Ink Free", Font.ITALIC, 30);
		st2 = Connector.createStatement();

		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(bgColor);
		add(centerPanel);

		centerPanel.setLayout(new GridLayout(4, 3));

		centerPanel.add(createLabel("User Name:"));
		userNameField = createTextField();
		centerPanel.add(userNameField);

		centerPanel.add(createLabel("Password:"));
		passwordField = createPasswordField();
		centerPanel.add(passwordField);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLUE);
		add(southPanel, BorderLayout.SOUTH);

		clear = createButton("Reset");
		southPanel.add(clear);

		submit = createButton("Submit");
		southPanel.add(submit);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LOGIN");
		setVisible(true);
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);

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

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if (userNameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"username or password field cannot be left blank!!");
				userNameField.setText("");
				passwordField.setText("");
				return;
			}
			String uname = userNameField.getText();
			String password = passwordField.getText();

			String query = String.format(
					"SELECT username, password FROM login where USERNAME = '%s' and password = '%s'", uname, password);
//        	System.out.println(query);
			try {
				ResultSet rs = st2.executeQuery(query);
				if (rs.next()) {
					System.out.println("Rows Returned");
					String pwFromDB = rs.getString(2);
//        			System.out.println("Value is: " + pwFromDB);

					pwFromDB = rs.getString("Password");
					System.out.println("Value is:" + pwFromDB);
					if (password.equals(pwFromDB))
						setVisible(false);
				} else
					System.out.println("Zero Rows Returned");
				System.out.println("ResultSet Loaded");
			} catch (SQLException se) {
				System.out.println("Error occured: " + se.getMessage());
			}
		}

	}

	public static void main(String[] args) {
		new login();
//        display all users
		Statement st = Connector.createStatement();
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM login");
			while (rs.next()) {
				rs.next();
				System.out.println(rs.getString("Username"));
			}
		} catch (SQLException e) {
			System.out.println("Error occured:" + e.getMessage());
		}

	}

}
