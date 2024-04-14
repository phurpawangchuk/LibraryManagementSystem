package business;

import dataaccess.User;

import java.util.HashMap;
import java.util.List;

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
