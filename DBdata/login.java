package DBdata;

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
	JButton submit, clear, Forgot;
	Statement st2;

	public login() {

		font = new Font("Comic Sans", Font.BOLD, 30);
		st2 = Practice_Connector.createStatement();

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(bgColor);
		add(centerPanel);

		centerPanel.setLayout(new GridLayout(5, 1));

		centerPanel.add(createLabel("User Name"));
		userNameField = createTextField();
		centerPanel.add(userNameField);

		centerPanel.add(createLabel("Password"));
		passwordField = createPasswordField();
		centerPanel.add(passwordField);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLUE);
		add(southPanel, BorderLayout.SOUTH);

		Forgot = createButton("Forgot password");
		southPanel.add(Forgot);

		clear = createButton("Reset");
		southPanel.add(clear);

		submit = createButton("Submit");
		southPanel.add(submit);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("LOGIN");
		setVisible(true);
		pack();
		setSize(600, 450);
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
		if (e.getSource() == clear) {
			userNameField.setText("");
			passwordField.setText("");
			return;

		}
		if (e.getSource() == submit) {
			if (userNameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Field Cannot Be Blank!!");
				userNameField.setText("");
				passwordField.setText("");
				return;
			}
			String uname = userNameField.getText();
			String password = passwordField.getText();

			String query = String.format(
					"SELECT username, password FROM administrative where Username = '%s' and password = '%s'", uname,
					password);
			try {
				ResultSet rs = st2.executeQuery(query);
				if (rs.next()) {
					String pwFromDB = rs.getString("Password");
					if (password.equals(pwFromDB))
						JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL");
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

	}

}
