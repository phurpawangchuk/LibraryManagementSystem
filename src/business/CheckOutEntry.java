package business;

import java.io.Serializable;

public class CheckOutEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Book book;
	private String checkOutDate;
	private String dueDate;
	
	public CheckOutEntry(Book book, String checkOutDate, String dueDate) {
		
		this.book = book;
		this.checkOutDate = checkOutDate;
		this.dueDate = dueDate;
	}

	public Book getBook() {
		return book;
	}

	public String getCheckOutDate() {
		return checkOutDate;
	}

	public String getDueDate() {
		return dueDate;
	}
	
	
	

}
