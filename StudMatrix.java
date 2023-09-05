
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudMatrix extends JFrame implements ActionListener,ItemListener{
	
	JTextField DeptField, fullnameField,FacultyField,DOBField,YearofaddField;
	JCheckBox male,female;
	String Gender;
	Color fgColor = Color.YELLOW, bgColor = Color.BLACK;
	Font font;
	Statement st2;
	JButton submit, clear;

	public StudMatrix() {
		font = new Font("Sans Seriff", Font.BOLD, 30);
		st2 = Student_Connector.createStatement();
		
		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(bgColor);
		add(centerPanel);

		centerPanel.setLayout(new GridLayout(5, 2));
		
		centerPanel.add(createLabel("Fullname"));
		fullnameField = createTextField();
		centerPanel.add(fullnameField);

		centerPanel.add(createLabel("Date of Birth"));
		DOBField = createTextField();
		centerPanel.add(DOBField);
		
		centerPanel.add(createLabel("Faculty"));
		FacultyField = createTextField();
		centerPanel.add(FacultyField);
		
		centerPanel.add(createLabel("Depatment"));
		DeptField = createTextField();
		centerPanel.add(DeptField);
		
		male = checkButton("male");
		centerPanel.add(male);
		female = checkButton("female");
		centerPanel.add(female);
		
		ButtonGroup group=new ButtonGroup();
		group.add(male);
		group.add(female);
		
		male.addItemListener(this);
        female.addItemListener(this);
        
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.BLUE);
		add(southPanel, BorderLayout.SOUTH);
		
		clear = createButton("Reset");
		southPanel.add(clear);

		submit = createButton("Submit");
		southPanel.add(submit);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Student Registeration");
		setVisible(true);
		pack();
		setSize(500, 500);
		setLocationRelativeTo(null);

	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (male.isSelected()) {
			Gender="male";
		}else if (female.isSelected()) {
			Gender="Female";
		}else {
			Gender=null;
		}

	}

	private JLabel createLabel(String txt) {
		JLabel label = new JLabel(txt);
		label.setFont(font);
		label.setForeground(fgColor);
		label.setHorizontalAlignment(JLabel.CENTER);

		return label;
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


	
	private JCheckBox checkButton(String txt) {
		JCheckBox btn = new JCheckBox(txt);
		font = new Font("Sans Seriff", Font.BOLD, 20);
		btn.setForeground(fgColor);
		btn.setBackground(bgColor);
		btn.setFont(font);
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
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clear) {
			fullnameField.setText("");
			DeptField.setText("");
			FacultyField.setText("");
			DOBField.setText("");
			}
		}

			

	public static void main(String[] args) {
		new StudMatrix();

	}

}
