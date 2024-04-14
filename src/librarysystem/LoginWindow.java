package librarysystem;

import business.ControllerInterface;
import business.LoginException;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private static boolean isLoggedIn = false;

    public static final boolean isLoggedIn(){
        return isLoggedIn;
    }

    public static final boolean setLoggedIn(boolean status){
        return isLoggedIn = status;
    }

	JButton btnLoginButton, btnExitAppButton;
	JTextField txtUsername;
	JTextField passwordField;
	ControllerInterface ci = new SystemController();

	LoginWindow() {

		setBounds(0, 0, 600, 400);
		setTitle("Log in");
		setLayout(null);
		setVisible(true);
		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 600, 400);
		p1.setLayout(null);
		add(p1);

		// username
		JLabel lblusername = new JLabel("Username");
		lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
		lblusername.setBounds(60, 20, 100, 25);
		p1.add(lblusername);
		txtUsername = new JTextField();
		txtUsername.setBounds(60, 60, 300, 30);
		txtUsername.setText("superuser");
		txtUsername.setBorder(BorderFactory.createEmptyBorder());
		p1.add(txtUsername);

		// password

		JLabel lblpassword = new JLabel("Password");
		lblpassword.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
		lblpassword.setBounds(60, 110, 100, 25);
		passwordField = new JPasswordField(20);
		passwordField.setBounds(60, 150, 300, 30);
		passwordField.setText("superuser");
		passwordField.setBorder(BorderFactory.createEmptyBorder());
		p1.add(passwordField);
		p1.add(lblpassword);

		// Buttons
		btnLoginButton = Util.newbuttonStyle(new JButton("Log in"));
		btnLoginButton.setBounds(60, 200, 130, 40);
		btnLoginButton.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
		btnLoginButton.addActionListener(this);
		p1.add(btnLoginButton);
		setVisible(true);

        btnExitAppButton = Util.newbuttonStyle(new JButton("Exit"));
        btnExitAppButton.setBounds(210, 200, 130, 40);
        btnExitAppButton.setBackground(new Color(255, 79, 48));
        btnExitAppButton.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
        btnExitAppButton.addActionListener(this);
        p1.add(btnExitAppButton);
        setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
        String username = txtUsername.getText().trim();
        String password = passwordField.getText();
        if(ae.getSource() == btnExitAppButton){
            for (Window window : Window.getWindows()) {
                if (window instanceof JFrame) {
                    window.dispose();
                }
            }
            JOptionPane.showMessageDialog(this, "Thanks for using the application.");

            System.exit(1);
        }else {
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            try {
                String role = ci.login(username, password);
                if (!role.isEmpty()) {
                    setLoggedIn(true);
                    openDashboardWindow(role);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
                }
            } catch (LoginException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }

    private void openDashboardWindow(String role) {
    	AdminDashboardWindow adw=new AdminDashboardWindow(role);
        setVisible(false);
          dispose();
        String message = "Welcome, " + role + "!";
        JOptionPane.showMessageDialog(this, message);
    }



	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginWindow();
			}
		});
	}// end of main

}
