package theatersystem;

import theatersystem.Client;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Represents a single theater
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class Theater implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int NO_SUCH_CLIENT = 0;
    public static final int OPERATION_COMPLETED= 1;
    public static final int ACTIVE_CLIENT = 2;
    public static final int NO_SUCH_MEMBER = 3;
    public static final int CREDIT_CARD_INVALID = 4;
    public static final int CREDIT_CARD_ADDED = 5;
    public static final int NO_SUCH_CREDIT_CARD = 6;
    public static final int ONLY_CREDIT_CARD = 7;
    public static final int SHOW_DATES_INVALID = 8;
    public static final int SHOW_ADDED = 9;
    private ClientList clientList;
    private MemberList memberList;
    private static Theater theater;
    
    /**
     * Private for the singleton pattern
     * Creates the catalog and member collection objects
     */
    private Theater() {
        clientList = ClientList.instance();
        memberList = MemberList.instance();
    }
    
    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static Theater instance() {
        if (theater == null) {
            MemberIdServer.instance(); // instantiate all singletons
            return (theater = new Theater());
        } else {
            return theater;
        }
    }
    
    /**
    * Organizes the operations for adding a member
    * @param name member name
    * @param address member address
    * @param phone member phone
    * @param expirationDate 
    * @param cardNumber 
    * @return the Member object created
    */
    public Member addMember(String name, String address, String phone, String cardNumber, Calendar expirationDate) {
        Member member = new Member(name, address, phone, cardNumber, expirationDate);
        if (memberList.insertMember(member)) {
            return (member);
        }
        return null;
    }
    
    /**
     * Removes a member
     * @param ID of the member to be removed
     * return indication on the outcome
     */
    public int removeMember(String memberID) {
        Member member = memberList.search(memberID);
        if (member == null) {
        return(NO_SUCH_MEMBER);
        }
        return memberList.deleteMember(member)? OPERATION_COMPLETED: NO_SUCH_CLIENT;
    }
    
    /**
    * Organizes the operations for adding a client
    * @param name member name
    * @param address member address
    * @param phone member phone
    * @return the Member object created
    */
    public Client addClient(String name, String address, String phone) {
        Client client = new Client(name, address, phone);
        if (clientList.insertClient(client)) {
            return (client);
        }
        return null;
    }
    
    /**
     * Removes a client
     * @param ID of the client to be removed
     * @return indication on the outcome
     */
    public int removeClient(String clientID) {
        Client client = clientList.search(clientID);
        if (client == null) {
            return(NO_SUCH_CLIENT);
        }
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        Iterator result = client.getShows();
        while (result.hasNext()) {
            Show show = (Show) result.next();
            if (currentDate.before(show.getEndDate())) {
                return(ACTIVE_CLIENT);
            }
        }
        return clientList.deleteClient(client)? OPERATION_COMPLETED: NO_SUCH_CLIENT;
    }
    
    /**
     * Organizes the adding of a credit card
     * @param member ID of the credit card holder
     * @param number of the credit card
     * @param expiration date of the credit card
     * @return indication on the outcome
     */
    public int addCreditCard(String memberID, String cardNumber, Calendar expirationDate) {
        Member member = memberList.search(memberID);
        if (member == null) {
            return(NO_SUCH_MEMBER);
        }
        if (!validCreditCard(cardNumber, expirationDate)) {
            return(CREDIT_CARD_INVALID);
        }
        CreditCard creditCard = new CreditCard(memberID, cardNumber, expirationDate);
        member.addCreditCard(creditCard);
        return(CREDIT_CARD_ADDED);
    }
    
    /**
     * Removes hold for a specific member
     * @param memberId whose hold has to be removed
     * @return true iff the hold could be removed
     */
    public int removeCreditCard(String memberID, String number) {
        Member member = memberList.search(memberID);
        if (member == null) {
        return(NO_SUCH_MEMBER);
        }
        if (member.multipleCreditCards()) {
            return member.removeCreditCard(number)? OPERATION_COMPLETED: NO_SUCH_CREDIT_CARD;
        }
        return(ONLY_CREDIT_CARD);
    }
    
    /**
     * Organizes the adding of a show
     * @param name of the show
     * @param ID of the client
     * @param start date of the show 
     * @param end date of the show 
     * @return indication on the outcome
     */
    public int addShow(String name, String clientID, Calendar startDate, Calendar endDate) {
        Client client = clientList.search(clientID);
        if (client == null) {
            return(NO_SUCH_CLIENT);
        }
        if (!validShowDates(startDate, endDate)) {
            return(SHOW_DATES_INVALID);
        }
        Show show = new Show(name, clientID, startDate, endDate);
        client.addShow(show);
        return(SHOW_ADDED);
    }
    
    /**
     * Removes a client
     * @param memberId id of the member
     * @param bookId book id
     * @return result of the operation 
     */
    public int removeShow(String clientId) {
        Client client = clientList.search(clientId);
        if (client == null) {
            return (NO_SUCH_CLIENT);
        }
        return client.removeShow(clientId)? OPERATION_COMPLETED: NO_SUCH_CLIENT;
    }
    
    /**
     * Checks if the credit card is valid
     * @param member ID of the credit card holder
     * @param number of the credit card
     * @param expiration date of the credit card
     * @return true if the credit card is valid
     */
    public boolean validCreditCard(String cardNumber, Calendar expirationDate) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        if (expirationDate.after(currentDate)) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the credit card is valid
     * @param member ID of the credit card holder
     * @param number of the credit card
     * @param expiration date of the credit card
     * @return true if the credit card is valid
     */
    public boolean validShowDates(Calendar startDate, Calendar endDate) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        if (startDate.after(endDate) || startDate.before(currentDate)) {
            return false;
        }
        Iterator clientResult = theater.getClients();
        if (clientResult != null) {
            while(clientResult.hasNext()) {
                Client client = (Client) clientResult.next();
                Iterator showResult = client.getShows();
                while(showResult.hasNext()) {
                    Show show = (Show) showResult.next();
                    if (!((endDate.after(show.getEndDate()) && startDate.after(show.getEndDate()))
                            || (endDate.before(show.getStartDate()) && startDate.before(show.getStartDate())))){
                        return false;
                    }

                }
            }
        }
        return true;
    }
    
    /**
     * Returns an iterator to the clients collection
     * @return iterator to the collection
     */
    public Iterator getClients() {
        return clientList.getClients();
    }
    
    /**
     * Returns an iterator to the members collection
     * @return iterator to the collection
     */
    public Iterator getMembers() {
        return memberList.getMembers();
    }
    
    /**
     * Retrieves a deserialized version of the theater from disk
     * @return a Theater object
     */
    public static Theater retrieve() {
        try {
            FileInputStream file = new FileInputStream("TheaterData");
            ObjectInputStream input = new ObjectInputStream(file);
            input.readObject();
            MemberIdServer.retrieve(input);
            ClientIdServer.retrieve(input);
            return theater;
        } catch(IOException ioe) {
            return null;
        } catch(ClassNotFoundException cnfe) {
            return null;
        }
    }
    
    /**
     * Serializes the Theater object
     * @return true if the data could be saved
     */
    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream("TheaterData");
            ObjectOutputStream output = new ObjectOutputStream(file);
            output.writeObject(theater);
            output.writeObject(MemberIdServer.instance());
            output.writeObject(ClientIdServer.instance());
            return true;
        } catch(IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    /**
     * Writes the object to the output stream
     * @param output the stream to be written to
     */
    private void writeObject(java.io.ObjectOutputStream output) {
        try {
            output.defaultWriteObject();
            output.writeObject(theater);
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }
    /**
     * Reads the object from a given stream
     * @param input the stream to be read
     */
    private void readObject(java.io.ObjectInputStream input) {
        try {
            input.defaultReadObject();
            if (theater == null) {
            theater = (Theater) input.readObject();
            } else {
            input.readObject();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /** String form of the theater
    */
    @Override
        public String toString() {
        return clientList + "\n" + memberList;
    }
}
