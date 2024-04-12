package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;


public class BookCopyWindow extends JFrame implements LibWindow {
    public static final BookCopyWindow INSTANCE = new BookCopyWindow();

    ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;
    private boolean checkRecord = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;

	private JTextField memberId;
	private JTextField Isbn;
	private JLabel label;

    private JButton checkButton;
    private JButton backButton;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();

    private String message = "NA";
	/* This class is a singleton */
    private BookCopyWindow() {}
    
    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();

        BorderLayout bl = new BorderLayout();
        bl.setVgap(10);
        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER); // Add middleHalf panel to the center
        getContentPane().add(mainPanel);

        isInitialized(true);
        pack();
        setSize(660, 500);
        centerFrameOnDesktop(this); // Center the frame on the desktop
    }


    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    private void defineUpperHalf() {
    		upperHalf = new JPanel();
    		upperHalf.setLayout(new BorderLayout());
    		defineTopPanel();
    		defineMiddlePanel();
    		defineLowerPanel();
    		upperHalf.add(topPanel, BorderLayout.NORTH);
    		upperHalf.add(middlePanel, BorderLayout.CENTER);
    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);

    	}
    private void defineMiddleHalf() {
    		middleHalf = new JPanel();
    		middleHalf.setLayout(new BorderLayout());
    		JSeparator s = new JSeparator();
    		s.setOrientation(SwingConstants.HORIZONTAL);
    		middleHalf.add(s, BorderLayout.SOUTH);
    	}

    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
//        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Book Copy Entry");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(2, 1)); // 4 rows, 1 column
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLeftTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        memberId = new JTextField(10);
        memberId.setText("48-56882");
        JLabel label = new JLabel("ISBN");
        panel.add(label, BorderLayout.NORTH);
        panel.add(memberId, BorderLayout.CENTER);
        leftTextPanel = panel;
    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        Isbn = new JTextField(10);
        Isbn.setText("10");
        JLabel label = new JLabel("Number of copy");
        panel.add(label, BorderLayout.NORTH);
        panel.add(Isbn, BorderLayout.CENTER);

        rightTextPanel = panel;
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();

        backButton = Util.buttonStyle(new JButton("<< Main Menu"));
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

        lowerPanel.add(Box.createHorizontalStrut(10));

        checkButton = Util.buttonStyle(new JButton("Add"));
        checkRecordButtonListener(checkButton);
        lowerPanel.add(checkButton);
    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println("Back clicked");
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void checkRecordButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println("Enter clicked");
             checkRecord = ci.checkRecord(memberId.getText(), Isbn.getText());
            if(checkRecord){
                message = "Book is In.";
                // Update the UI to show the "Continue" button
                // Repaint the frame
                revalidate();
                repaint();
            }
            // ISBN: 48-56882, member: 1004
            //LibrarySystem.hideAllWindows();
            JOptionPane.showMessageDialog(this, message);
        });
    }
}
