package librarysystem;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.DataAccessFacade;

public class AddMemberWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public static final AddMemberWindow INSTANCE = new AddMemberWindow();
	JTextField txtMemId, txtFirstName, txtLastName, txtStreet, txtCity, txtState, txtZip, txtTelephone;
	JRadioButton rbYes, rbNo;
	JButton btnAddMember, btnBack;
	AdminDashboardWindow dashboard;
    ControllerInterface ci = new SystemController();

	public AddMemberWindow(AdminDashboardWindow dashboard) {
		// TODO Auto-generated constructor stub
		this.dashboard = dashboard;
		setBounds(0, 0, 660, 380);
		setTitle("Add Library Members");
		setLayout(null);
		setVisible(true);
		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 330, 190);
		p1.setLayout(null);
		add(p1);

		JPanel p2 = new JPanel();
		p2.setBounds(330, 0, 330, 190);
		p2.setLayout(null);
		add(p2);

		// Left hand side labeling
		JLabel lblId = new JLabel("Member ID");
		lblId.setBounds(40, 40, 100, 20);
		p1.add(lblId);

		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(40, 40, 100, 20);
		p2.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(40, 80, 100, 20);
		p1.add(lblLastName);

		JLabel lblStreet = new JLabel("Street");
		lblStreet.setBounds(40, 80, 100, 20);
		p2.add(lblStreet);

		JLabel lblCity = new JLabel("City");
		lblCity.setBounds(40, 120, 100, 20);
		p1.add(lblCity);

		JLabel lblState = new JLabel("State");
		lblState.setBounds(40, 120, 100, 20);
		p2.add(lblState);

		JLabel lblZip = new JLabel("Zip Code");
		lblZip.setBounds(40, 160, 100, 20);
		p1.add(lblZip);

		JLabel lblTelephone = new JLabel("Telephone No.");
		lblTelephone.setBounds(40, 160, 100, 20);
		p2.add(lblTelephone);

		// left side text field

		txtMemId = new JTextField();
        txtMemId.setText(ci.getMemberId());
        txtMemId.setEditable(false);
		txtMemId.setBounds(160, 40, 50, 25);
		txtMemId.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtMemId);

		txtFirstName = new JTextField();
		txtFirstName.setText("Alice");
		txtFirstName.setBounds(160, 40, 120, 25);
		txtFirstName.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p2.add(txtFirstName);

		txtLastName = new JTextField();
		txtLastName.setText("Bob");
		txtLastName.setBounds(160, 80, 120, 25);
		txtLastName.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtLastName);

		txtStreet = new JTextField();
		txtStreet.setText("1000 North ");
		txtStreet.setBounds(160, 80, 120, 25);
		txtStreet.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p2.add(txtStreet);

		txtCity = new JTextField();
		txtCity.setText("Fairfield");
		txtCity.setBounds(160, 120, 120, 25);
		txtCity.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtCity);

		txtState = new JTextField();
		txtState.setText("Iowa");
		txtState.setBounds(160, 120, 120, 25);
		txtState.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p2.add(txtState);

		txtZip = new JTextField();
		txtZip.setText("52557");
		txtZip.setBounds(160, 160, 120, 25);
		txtZip.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtZip);

        txtTelephone = new JFormattedTextField(Util.TelephoneFormatter());
		txtTelephone.setBounds(160, 160, 120, 25);
		txtTelephone.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p2.add(txtTelephone);

		btnAddMember = new JButton("Add Member");
		btnAddMember.setBounds(380, 230, 150, 40);
		Util.newbuttonStyle(btnAddMember);
		btnAddMember.addActionListener(this);
		add(btnAddMember);
		
		btnBack=new JButton("<<Main Menu");
		btnBack.setBounds(80, 230, 150, 40);
		Util.newbuttonStyle(btnBack);
		btnBack.addActionListener(this);
		add(btnBack);
		

	}

	@Override
	public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        String memId = txtMemId.getText();
        String firstname = txtFirstName.getText();
        String lastname = txtLastName.getText();
        String street = txtStreet.getText();
        String state = txtState.getText();
        String city = txtCity.getText();
        String telephone = txtTelephone.getText();
        String zip = txtZip.getText();

        if(ae.getSource()==btnBack)
        {
            dashboard.setVisible(true);
            setVisible(false);
            dispose();
        }
        else{
        if (memId.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || street.isEmpty() || state.isEmpty()
                || city.isEmpty() || telephone.isEmpty() || zip.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Fields should not be empty");
            return;
        }

        boolean memIdCheck = memId.matches("\\d{4}");
        boolean telephoneCheck = telephone.matches("\\d{3}-\\d{3}-\\d{4}");
        String msgId = "", msgTelString = "";
        if (!memIdCheck)
            msgId = "Member Id should be 4 digit numbers\n";
        if (!telephoneCheck)
            msgTelString = "Telephone should be in XXX-XXX-XXXX format ";
        String msg = msgId + msgTelString;
        if (!(memIdCheck && telephoneCheck)) {
            JOptionPane.showMessageDialog(null, msg);
            return;
        }

        if (ae.getSource() == btnAddMember) {
            Address address = new Address(street, city, state, zip);
            LibraryMember libraryMember = new LibraryMember(memId, firstname, lastname, telephone, address);
            DataAccessFacade daf = new DataAccessFacade();
            HashMap<String, LibraryMember> map = daf.readMemberMap();
            if (map.containsKey(memId)) {
                JOptionPane.showMessageDialog(null, "Member already present.");
                return;
            } else {
                daf.saveNewMember(libraryMember);
                JOptionPane.showMessageDialog(null, "Members added sucessfully");

                // new dashboard;
            }
        }

    }

	}

}
