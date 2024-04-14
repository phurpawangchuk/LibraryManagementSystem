package librarysystem;

import business.LibraryMember;
import business.SystemController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;


public class MemberTable {
	public static void main(String[] args) {
		SystemController sc=new SystemController();
		HashMap<String, LibraryMember> memberMap= sc.getAllMembers();
		for (Entry<String, LibraryMember> entry : memberMap.entrySet()) {
		    String memberId = entry.getKey();
		    LibraryMember member = entry.getValue();
		    // Do something with memberId and member
		    System.out.println("Member ID: " + memberId);
		    System.out.println("Member Info: " + member);
		    System.out.println();
		}
		
		
	}
	
}