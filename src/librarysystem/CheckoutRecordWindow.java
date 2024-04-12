package librarysystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import business.*;
import dataaccess.DataAccessFacade;

public class CheckoutRecordWindow extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField txtMemId, txtIsbn;
	JButton btnCheck, btnBack, btnContinue;
	ControllerInterface ci = new SystemController();
	DefaultTableModel recordTableModel;
	JTable recordtable;
	AdminDashboardWindow dashboard;
	Book book;

	public CheckoutRecordWindow(AdminDashboardWindow dashboard) {
		// TODO Auto-generated constructor stub
		this.dashboard = dashboard;
		setBounds(0, 0, 660, 600);
		setTitle("Check Out Record");
		setLayout(null);
		setVisible(true);
		setBackground(Color.gray);
		Util.centerFrameOnDesktop(this);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Two panels
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 660, 600);
		p1.setLayout(null);
		add(p1);

		// Left hand side labeling
		JLabel lblMemberID = new JLabel("Member ID");
		lblMemberID.setBounds(40, 40, 100, 30);
		p1.add(lblMemberID);

		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(40, 80, 100, 30);
		p1.add(lblIsbn);

		// left side text field
		txtMemId = new JTextField();
		txtMemId.setBounds(160, 40, 120, 25);
		txtMemId.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		txtMemId.setText("1004");
		p1.add(txtMemId);

		txtIsbn = new JTextField();
		txtIsbn.setBounds(160, 80, 120, 25);
		txtIsbn.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		p1.add(txtIsbn);
		txtIsbn.setText("28-12331");

		// Buttons

		btnCheck = Util.newbuttonStyle(new JButton("Check"));
		btnCheck.setBounds(40, 120, 100, 40);
		p1.add(btnCheck);
		btnCheck.addActionListener(this);

		btnBack = Util.newbuttonStyle(new JButton("<<Main Menu"));
		btnBack.setBounds(160, 120, 150, 40);
		p1.add(btnBack);
		btnBack.addActionListener(this);

		btnContinue = Util.newbuttonStyle(new JButton("Continue"));
		btnContinue.setBounds(330, 120, 100, 40);
		p1.add(btnContinue);
		btnContinue.setEnabled(false);
		btnContinue.addActionListener(this);

		// records table
		recordTableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to make all cells uneditable
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		recordTableModel.addColumn("ISBN No.");
		recordTableModel.addColumn("Book Title");
		recordTableModel.addColumn("Available Copies");
		recordTableModel.addColumn("Days Available ");

		recordtable = new JTable(recordTableModel);
		recordtable.setBounds(40, 180, 520, 50);
		recordtable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Adjust column width automatically
		recordtable.getTableHeader().setReorderingAllowed(false); // Disable column reordering
		JScrollPane scrollPane = new JScrollPane(recordtable);
		scrollPane.setBounds(40, 180, 520, 50);

		p1.add(scrollPane);

	}

	public String getMemId() {
		return txtMemId.getText();
	}

	public String getISBN() {
		return txtIsbn.getText();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

        String isbnNo = txtIsbn.getText();
        String memID = txtMemId.getText();
        DataAccessFacade daf = new DataAccessFacade();
        LibraryMember member = daf.readMemberMap().get(memID);
        if (ae.getSource() == btnBack) {
            dashboard.setVisible(true);
            dispose();

        }else{
        if (ae.getSource() == btnCheck) {
            boolean checkRecord = ci.checkRecord(memID, isbnNo);   //checking member and book availability
            if (checkRecord) {
                JOptionPane.showMessageDialog(null, "Record found! Click continue to check out");
                book = daf.readBooksMap().get(isbnNo);
                listOutBooks(book);
                btnContinue.setEnabled(true);

            } else {
                btnContinue.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Record Not found");
            }
        }

        if (ae.getSource() == btnContinue) {
            new CheckoutEntryWindow(this);
            dispose();
        }
    }
	}

	public void listOutBooks(Book book) {
		recordTableModel.setRowCount(0);
		
			String[] rowData = { book.getIsbn(), book.getTitle(), ""+book.getNumCopies(),""+book.getMaxCheckoutLength() };
			recordTableModel.addRow(rowData);
		
	}// end of list out records

	public void showAddedRecords(CheckOutEntry cc) {
		String[] rowData = { cc.getBook().getIsbn(), cc.getBook().getTitle(), cc.getCheckOutDate(), cc.getDueDate() };
		recordTableModel.addRow(rowData);
	}// end

}
