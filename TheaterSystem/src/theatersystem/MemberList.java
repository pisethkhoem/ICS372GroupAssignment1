package theatersystem;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The collection class for Member objects
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class MemberList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List members = new LinkedList();
    private static MemberList memberList;
    
    /**
     * Private constructor for singleton pattern
     */
    private MemberList() {
    }
    
    /**
     * Supports the singleton pattern
     * @return the singleton object
     */
    public static MemberList instance() {
        if (memberList == null) {
            return (memberList = new MemberList());
        } else {
            return memberList;
        }
    }
    
    /**
     * Inserts a member into the collection
     * @param member the member to be inserted
     * @return true if the member could be inserted
     */
    public boolean insertMember(Member member) {
        members.add(member);
        return true;
    }
    
    /**
     * Deletes a member in the collection
     * @param the member to be inserted
     * @return true if the member could be inserted
     */
    public boolean deleteMember(Member member) {
        members.remove(member);
        return true;
    }
    
    /**
     * Checks whether a member with a given member ID exists
     * @param memberId the ID of the member
     * @return true if member exists
     */
    public Member search(String memberId) {
        for (Iterator iterator = members.iterator(); iterator.hasNext(); ) {
            Member member = (Member) iterator.next();
            if (member.getId().equals(memberId)) {
            return member;
            }
        }
        return null;
    }
    
    /**
     * Gets an iterator to a collection of all members
     * @return the iterator to the collection
     */
    public Iterator getMembers() {
        return members.iterator();
    }
    
    /**
     * Supports serialization
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(memberList);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Supports serialization
     * @param input the stream to be read from
     */
    private void readObject(java.io.ObjectInputStream input) {
        try {
            if (memberList != null) {
            return;
            } else {
            input.defaultReadObject();
            if (memberList == null) {
                memberList = (MemberList) input.readObject();
            } else {
                input.readObject();
            }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
    
    /**
     * String form of the collection
     */
    @Override
    public String toString() {
        return members.toString();
        }
    }
