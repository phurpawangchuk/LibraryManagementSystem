package dataaccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade.StorageType;
import dataaccess.User;


public class aa implements DataAccess {

    enum StorageType {
        BOOKS, MEMBERS, USERS;
    }

    // For Mac Users path can use /
//	public static final String OUTPUT_DIR = System.getProperty("user.dir")
//			+ "\\src\\dataaccess\\storage";

    public static final String OUTPUT_DIR = System.getProperty("user.dir")
            + "/src/dataaccess/storage";

    @Override
    public HashMap<String, Book> readBooksMap() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public HashMap<String, User> readUserMap() {
        //Returns a Map with name/value pairs being
        //   userId -> User
        return (HashMap<String, User>)readFromStorage(dataaccess.DataAccessFacade.StorageType.USERS);
    }

    @Override
    public HashMap<String, LibraryMember> readMemberMap() {
        return null;
    }

    @Override
    public void saveNewMember(LibraryMember member) {

    }

    @Override
    public void saveNewUser(User user) {
        System.out.println("AAAAAAA");
    }


    static void loadUserMap(List<User> userList) {
        HashMap<String, User> users = new HashMap<String, User>();
        userList.forEach(user -> users.put(user.getId(), user));
    }


    public static Object readFromStorage(dataaccess.DataAccessFacade.StorageType type) {
        ObjectInputStream in = null;
        Object retVal = null;
        try {
            Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
            in = new ObjectInputStream(Files.newInputStream(path));
            retVal = in.readObject();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch(Exception e) {}
            }
        }
        return retVal;
    }

    //Member
    //{1004=Member Info: ID: 1004, name: Ricardo Montalbahn, 641-472-2871 (501 Central, Mountain View, 94707), 1003=Member Info: ID: 1003, name: Sarah Eagleton, 451-234-8811 (42 Dogwood Dr., Fairfield, 52556), 1002=Member Info: ID: 1002, name: Drew Stevens, 702-998-2414 (1435 Channing Ave, Palo Alto, 94301), 1001=Member Info: ID: 1001, name: Andy Rogers, 641-223-2211 (5001 Venice Dr., Los Angeles, 93736)}

    //Book
    //{48-56882=isbn: 48-56882, maxLength: 7, available: true, 28-12331=isbn: 28-12331, maxLength: 7, available: true, 23-11451=isbn: 23-11451, maxLength: 21, available: true, 99-22223=isbn: 99-22223, maxLength: 21, available: true}

    public static void main(String[] args) {
        aa c = new aa();

        DataAccess da = new DataAccessFacade();
//
//        User admin = new User("admin","admin",Auth.ADMIN);
//        da.saveNewUser(admin);
//        User lm = new User("librarian","librarian",Auth.LIBRARIAN);
//        da.saveNewUser(lm);
//        User both = new User("superuser","superuser",Auth.BOTH);
//        da.saveNewUser(both);

        Object user = c.readFromStorage(DataAccessFacade.StorageType.USERS);

        System.out.println(user);
    }
}