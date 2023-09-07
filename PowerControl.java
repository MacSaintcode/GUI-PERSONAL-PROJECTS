import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;

public class PowerControl extends JFrame implements ActionListener {

    String os;
    Runtime runtime;
    Color fgcolor = Color.YELLOW, bgcolor = Color.BLACK;
    Font font = new Font("Comic sans", Font.BOLD, 20);
    JButton action, close;
    JComboBox<String> option;

    public PowerControl() {
        os = System.getProperty("os.name");
        runtime = Runtime.getRuntime();

        JPanel centerpanel = new JPanel();
        centerpanel.setBackground(bgcolor);
        add(centerpanel);

        GridLayout gl = new GridLayout(3, 2);
        gl.setVgap(5);
        centerpanel.setLayout(gl);
        centerpanel.add(createlabel(""));
        centerpanel.add(createlabel(""));
        centerpanel.add(createlabel("Power Option:"));
        String[] opt = { "Shutdown", "Restart", "Sleep", "Lock" };
        option = createjbox(opt);

        centerpanel.add(option);
        centerpanel.add(createlabel(""));
        centerpanel.add(createlabel(""));

        JPanel southpanel = new JPanel();
        southpanel.setBackground(Color.gray);
        add(southpanel, BorderLayout.SOUTH);

        action = createbutton("Go");
        southpanel.add(action);

        close = createbutton("Cancel");
        southpanel.add(close);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Power Control");
        pack();
        setSize(500, 200);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    JLabel createlabel(String txt) {
        JLabel label = new JLabel(txt);
        label.setFont(font);
        label.setForeground(fgcolor);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    private JComboBox<String> createjbox(String[] arr) {
        JComboBox<String> box = new JComboBox<String>(arr);
        box.setBackground(bgcolor);
        box.setForeground(fgcolor);
        box.setFont(font);

        return box;
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

    public void shutdown() {
        try {
            if ("Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)
                    || "Windows 11".equals(os)) {
                Runtime.getRuntime().exec("shutdown -s");
                // runtime.exec("shutdown -s");
            } else {
                System.err.println("Unsupported operating system");
            }
        } catch (Exception e) {
            System.err.println("shutdown error");
            e.printStackTrace();
        }

    }

    public void restart() {
        try {
            if ("Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)
                    || "Windows 11".equals(os)) {
                runtime.exec("shutdown -r");
            } else {
                System.err.println("Unsupported operating system");
            }
        } catch (Exception e) {
            System.err.println("restart error");
            e.printStackTrace();
        }

    }

    public void sleep() {
        try {
            if ("Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)
                    || "Windows 11".equals(os)) {
                runtime.exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");
            } else {
                System.err.println("Unsupported operating system");
            }
        } catch (Exception e) {
            System.err.println("suspend error");
            e.printStackTrace();
        }
    }

    public void lock() {
        try {
            if ("Linux".equals(os) || "Mac OS X".equals(os)) {
            } else if ("Windows 8.1".equals(os) || "Windows 8.0".equals(os) || "Windows 10".equals(os)
                    || "Windows 11".equals(os)) {
                runtime.exec("Rundll32.exe user32.dll,LockWorkStation");
            } else {
                System.err.println("Unsupported operating system");
            }
        } catch (Exception e) {
            System.err.println("pc lock error");
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent click) {
        if (click.getSource() == action) {
            if (option.getSelectedItem().equals("Restart")) {
                restart();
                dispose();

            } else if (option.getSelectedItem().equals("Lock")) {
                lock();
                dispose();

            } else if (option.getSelectedItem().equals("Sleep")) {
                sleep();
                dispose();

            } else if (option.getSelectedItem().equals("Shutdown")) {
                shutdown();
                dispose();

            }

        } else if (click.getSource() == close) {
            dispose();

        }

    }

    public static void main(String args[]) {
        new PowerControl();
    }

}
