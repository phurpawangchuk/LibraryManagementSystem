package business;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public String login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
        return currentAuth.toString();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

    @Override
    public List<String> allBooks() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public HashMap<String,Book> getAllBooks() {
        DataAccess da = new DataAccessFacade();
        HashMap<String, Book> retval = da.readBooksMap();
        return retval;

    }

    @Override
    public HashMap<String, LibraryMember> getAllMembers() {
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> retval = da.readMemberMap();
        return retval;
    }

    @Override
    public HashMap<String, User> getAllUsers() {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> retval = da.readUserMap();
        return retval;
    }

    @Override
    public boolean checkRecord(String memberId, String Isbn) {
        DataAccess da = new DataAccessFacade();
        boolean IsbnFound = false;
        boolean memberIdFound = false;

        for(Map.Entry<String, Book> h: da.readBooksMap().entrySet()){
            if(h.getValue().getIsbn().equals(Isbn)){
                IsbnFound = true;
                break;
            }
        }
        //48-56882 ====1004
        for(Map.Entry<String, LibraryMember> hm: da.readMemberMap().entrySet()){
            if(hm.getValue().getMemberId().equals(memberId)){
                memberIdFound = true;
                break;
            }
        }
        if(IsbnFound && memberIdFound)
            return true;

        return false;
    }

    @Override
    public String getMemberId() {
        HashMap<String, LibraryMember> lm = getAllMembers();
        String maxMemberId = getMaxMemberId(lm);
        return maxMemberId;
    }

    @Override
    public String getDueDate(String txtCheckOutDate, String durationDay) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dueDateL = LocalDate.parse(txtCheckOutDate, dateFormatter);
        int duration = Integer.parseInt(durationDay);
        LocalDate newDueDate = dueDateL.plusDays(duration);
        String newDueDateString = newDueDate.format(dateFormatter);
        String dueDate = newDueDateString.toString();
        return dueDate;
    }

    public static String getMaxMemberId(HashMap<String, LibraryMember> lm) {
        String maxMemberId = "";
        for (Map.Entry<String, LibraryMember> entry : lm.entrySet()) {
            LibraryMember member = entry.getValue();
            String memberId = member.getMemberId();
            if (memberId.compareTo(maxMemberId) > 0) {
                maxMemberId = memberId;
            }
        }
        return addOneToMemberId(maxMemberId);
    }

    public static String addOneToMemberId(String memberId) {
        int id = Integer.parseInt(memberId);
        id++;
        return String.valueOf(id);
    }
}