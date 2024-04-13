package librarysystem;

import java.awt.*;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.MaskFormatter;

public class Util {
	public static final Color DARK_BLUE = Color.BLUE.darker();
	public static final Color TEAL_COLOR=new Color(0, 184, 230);//TEAL COLOR
	public static Font makeSmallFont(Font f) {
        return new Font(f.getName(), f.getStyle(), (f.getSize()-2));
    }
	
	public static void adjustLabelFont(JLabel label, Color color, boolean bigger) {
		if(bigger) {
			Font f = new Font(label.getFont().getName(), 
					label.getFont().getStyle(), (label.getFont().getSize()+2));
			label.setFont(f);
		} else {
			Font f = new Font(label.getFont().getName(), 
					label.getFont().getStyle(), (label.getFont().getSize()-2));
			label.setFont(f);
		}
		label.setForeground(color);
		
	}
	/** Sorts a list of numeric strings in natural number order */
	public static List<String> numericSort(List<String> list) {
		Collections.sort(list, new NumericSortComparator());
		return list;
	}
	
	static class NumericSortComparator implements Comparator<String>{
		@Override
		public int compare(String s, String t) {
			if(!isNumeric(s) || !isNumeric(t)) 
				throw new IllegalArgumentException("Input list has non-numeric characters");
			int sInt = Integer.parseInt(s);
			int tInt = Integer.parseInt(t);
			if(sInt < tInt) return -1;
			else if(sInt == tInt) return 0;
			else return 1;
		}
	}
	
	public static boolean isNumeric(String s) {
		if(s==null) return false;
		try {
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	public static void centerFrameOnDesktop(Component f) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int height = toolkit.getScreenSize().height;
		int width = toolkit.getScreenSize().width;
		int frameHeight = f.getSize().height;
		int frameWidth = f.getSize().width;
		f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
	}

    public static JButton buttonStyle(JButton btn){
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.setPreferredSize(new Dimension(130, 30));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        return btn;
    }

    //Phurpa Added
    public static MaskFormatter IsbnFormatter(){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("##-#######");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    public static MaskFormatter TelephoneFormatter(){
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("###-###-####");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter;
    }

    public static boolean validateTelephone(String telephone){
        return telephone.matches("\\d{3}-\\d{3}-\\d{4}");
    }
    /*
     * Done by Binod Rasaili
     */
    /*****************************************************************************/
    public static JButton newbuttonStyle(JButton btn){
        btn.setBackground(TEAL_COLOR);
        btn.setForeground(Color.white);
        btn.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 18));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btn.setUI(new BasicButtonUI());
        return btn;
    }

}