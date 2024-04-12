package business;

import java.io.Serializable;


final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckOutEntry[]  checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;	
		checkoutRecord = new CheckOutEntry[0];
	}
	
	public void addCheckOutRecord(CheckOutEntry coe) {
		CheckOutEntry[] newArr = new CheckOutEntry[checkoutRecord.length + 1];
		System.arraycopy(checkoutRecord, 0, newArr, 0, checkoutRecord.length);
		newArr[checkoutRecord.length] = coe;
		checkoutRecord = newArr;
	}	
	
	public String getMemberId() {
		return memberId;
	}
	
	public CheckOutEntry[] getCheckOutEntries()
	{
		return checkoutRecord;
	}

	
	
	@Override
	public String toString() {
		String string= "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
		StringBuilder sb=new StringBuilder();
		sb.append(string);
		for(CheckOutEntry cc:getCheckOutEntries())
		{
			sb.append(cc.getBook());
			sb.append(cc.getCheckOutDate());
			sb.append(cc.getDueDate());
		}
		return sb.toString();
		
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
