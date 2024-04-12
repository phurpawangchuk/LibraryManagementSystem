package librarysystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import business.*;


public class AddAuthorWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtFirstName, txtLastName, txtStreet, txtCity, txtState, txtZip, txtTelephone, txtBio;
	ButtonGroup creditGroup;
	JRadioButton rbYes, rbNo;
	JButton btnAddAuthor, btnCancelAuthor;
	AddBookWindow parentWindow;

	public AddAuthorWindow(AddBookWindow parentWindow) {
		// TODO Auto-generated constructor stub
		this.parentWindow = parentWindow;
		setBounds(0, 0, 660, 380);
		setTitle("Add Book Author");
		setLayout(null);
		setVisible(true);
//		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 300, 240);
		p1.setLayout(null);
		add(p1);
		
		JPanel p2=new JPanel();
		p2.setBounds(300,0,360,240);
		p2.setLayout(null);
		add(p2);

		// Left hand side labeling
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(40, 40, 100, 30);
		p1.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(40, 40, 100, 30);
		p2.add(lblLastName);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(40, 80, 100, 30);
		p1.add(lblStreet);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(40, 80, 100, 30);
		p2.add(lblCity);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(40, 120, 100, 30);
		p1.add(lblState);

		JLabel lblZip = new JLabel("Zip Code");
		lblZip.setBounds(40, 120, 100, 30);
		p2.add(lblZip);

		JLabel lblTelephone = new JLabel("Telephone No.");
		lblTelephone.setBounds(40, 160, 100, 30);
		p1.add(lblTelephone);

		JLabel lbBio = new JLabel("Bio");
		lbBio.setBounds(40, 160, 100, 30);
		p2.add(lbBio);

		JLabel lblCredit = new JLabel("Credits");
		lblCredit.setBounds(40, 200, 100, 30);
		p1.add(lblCredit);

		// left side text field
		txtFirstName = new JTextField();
		txtFirstName.setBounds(160, 40, 120, 25);
		txtFirstName.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtFirstName.setText("Charles ");
		p1.add(txtFirstName);

		txtLastName = new JTextField();
		txtLastName.setBounds(160, 40, 120, 25);
		txtLastName.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p2.add(txtLastName);
		txtLastName.setText("Baggage ");

		txtStreet = new JTextField();
		txtStreet.setBounds(160, 80, 120, 25);
		txtStreet.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtStreet.setText("Burlington 10 ave");
		p1.add(txtStreet);

		txtCity = new JTextField();
		txtCity.setBounds(160, 80, 120, 25);
		txtCity.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtCity.setText("Chicago");
		p2.add(txtCity);

		txtState = new JTextField();
		txtState.setBounds(160, 120, 120, 25);
		txtState.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtState.setText("Illinos");
		p1.add(txtState);

		txtZip = new JTextField();
		txtZip.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtZip.setBounds(160, 120, 120, 25);
		txtZip.setText("52556");
		p2.add(txtZip);

		txtTelephone = new JTextField();
		txtTelephone.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtTelephone.setBounds(160, 160, 120, 25);
		txtTelephone.setText("641-123-4657");
		p1.add(txtTelephone);

		txtBio = new JTextField();
		txtBio.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtBio.setBounds(160, 160, 120, 25);
		txtBio.setText("Nice author");
		p2.add(txtBio);

		rbYes = new JRadioButton("Yes");
		rbYes.setBounds(160, 200, 70, 25);
		p1.add(rbYes);
		rbYes.setActionCommand("Yes");
		rbYes.setSelected(true);

		rbNo = new JRadioButton("No");
		rbNo.setBounds(0, 200, 50, 25);
		rbNo.setActionCommand("No");
		p2.add(rbNo);

		creditGroup = new ButtonGroup();
		creditGroup.add(rbYes);
		creditGroup.add(rbNo);

		// Buttons

		btnAddAuthor = Util.newbuttonStyle(new JButton("Add Author"));
		btnAddAuthor.setBounds(170, 270, 120, 40);
		add(btnAddAuthor);
		btnAddAuthor.addActionListener(this);

        btnCancelAuthor = Util.newbuttonStyle(new JButton("Cancel"));
        btnCancelAuthor.setBounds(300, 270, 120, 40);
        add(btnCancelAuthor);
        btnCancelAuthor.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
        String firstname = txtFirstName.getText();
        String lastname = txtLastName.getText();
        String biography = txtBio.getText();
        String credit = creditGroup.getSelection().getActionCommand();
        String street = txtStreet.getText();
        String state = txtState.getText();
        String city = txtCity.getText();
        String telephone = txtTelephone.getText();
        String zip = txtZip.getText();


        if (ae.getSource() == btnCancelAuthor) {
            dispose();
            setVisible(false);
            parentWindow.setVisible(true);
        }else{
        // Check for empty fields
        if (firstname.isEmpty() || lastname.isEmpty() || street.isEmpty() || city.isEmpty() || state.isEmpty()
                || zip.isEmpty() || telephone.isEmpty() || biography.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return; // Return without proceeding if any field is empty
        }

        // Telephone number validation
        if (!telephone.matches("\\d{3}-\\d{3}-\\d{4}")) {
            JOptionPane.showMessageDialog(null, "Please enter telephone number in format XXX-XXX-XXXX.");
            return; // Return without proceeding if telephone number format is incorrect
        }

        if (ae.getSource() == btnAddAuthor) {
            Address address = new Address(street, city, state, zip);
            Author author = new Author(firstname, lastname, telephone, address, credit, biography);
            parentWindow.addAuthor(author);
            dispose();
            setVisible(false);
            parentWindow.setVisible(true);
        }
    }
	}

}
