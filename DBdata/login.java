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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.sql.Connection;

public class login extends JFrame implements ActionListener, WindowListener, ItemListener {

	Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
	Font font;

	JTextField userNameField;
	JPasswordField passwordField;
	JRadioButton show, hide;
	JButton submit, clear, Forgot, create;
	String tick = "";
	Statement st2;

	public login(Statement st) {
		st2 = st;

		font = new Font("Comic Sans", Font.BOLD, 30);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(bgColor);
		add(centerPanel);

		centerPanel.setLayout(new GridLayout(5, 1));

		centerPanel.add(createLabel("User Name"));
		userNameField = createTextField();
		centerPanel.add(userNameField);
		
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

		centerPanel.add(createLabel("Password"));
		passwordField = createPasswordField();
		centerPanel.add(passwordField);
		centerPanel.add(combines);
		hide.setSelected(true);

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLUE);
		add(southPanel, BorderLayout.SOUTH);

		Forgot = createButton("Forgot password");
		southPanel.add(Forgot);

		clear = createButton("Reset");
		southPanel.add(clear);

		submit = createButton("Submit");
		southPanel.add(submit);

		create = createButton("Create Account");
		southPanel.add(create);

		addWindowListener(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("LOGIN");
		setVisible(true);
		pack();
		setSize(821, 450);
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
		txtField.setEchoChar('*');

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
		if (e.getSource() == Forgot) {
			tick = "forgot";

			dispose();
		}
		if (e.getSource() == create) {
			tick = "create";

			dispose();
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
					String un = rs.getString("username");
					String pwFromDB = rs.getString("Password");
					if (password.equals(pwFromDB) && uname.equals(un)) {
						tick = "done";
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Invalid Login!");
						userNameField.setText("");
						passwordField.setText("");
						return;
					}

				} else {

					JOptionPane.showMessageDialog(null, "User Does Not Exist!");
					userNameField.setText("");
					passwordField.setText("");
					return;

				}

			} catch (SQLException se) {
				System.out.println("Error occured: " + se.getMessage());
			}
		}

	}

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		if (tick.equalsIgnoreCase("done")) {
			JOptionPane.showMessageDialog(null, "LOGIN SUCCESSFUL");
			System.exit(0);
		} else if (tick.equalsIgnoreCase("forgot")) {
			new changepassword(st2);
		} else if (tick.equalsIgnoreCase("create")) {
			new SignUp();
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

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (show.isSelected()) {
			passwordField.setEchoChar((char) 0);
		}
		if (hide.isSelected()) {
			passwordField.setEchoChar('*');
		}
	}

}
