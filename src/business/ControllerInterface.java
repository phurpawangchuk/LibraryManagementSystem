package business;

import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public interface ControllerInterface {
	public String login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();

    public List<String> allBooks();

    public HashMap<String,Book> getAllBooks();

    public HashMap<String, LibraryMember> getAllMembers();

    public HashMap<String, User> getAllUsers();

    public boolean checkRecord(String memberId, String Isbn);

    public String getMemberId();

    public String getDueDate(String checkoutDate, String duration);
}
