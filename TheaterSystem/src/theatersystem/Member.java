package theatersystem;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a single member
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Adapted from Brahma Dathan and Sarnath Ramnath 2010
 */
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private String id;
    private static final String MEMBER_STRING = "M";
    private List creditCards = new LinkedList();
  
    /**
     * Represents a single member
     * @param name of the member
     * @param address of the member
     * @param phone number of the member
     * @param expirationDate 
     * @param cardNumber 
     */
    public Member (String name, String address, String phone, String cardNumber, Calendar expirationDate) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = MEMBER_STRING + (MemberIdServer.instance()).getId(); 
        CreditCard creditCard = new CreditCard(id, cardNumber, expirationDate);
        creditCards.add(creditCard);
    }

    /**
     * Adds a credit card for the member
     * @param credit card to be added
     */
    public void addCreditCard(CreditCard creditCard) {
        creditCards.add(creditCard);
    }
  
    /**
     * Removes credit card for a specific member
     * @param credit card number of the card to be removed
     * @return true if the credit card could be removed
     */
    public boolean removeCreditCard(String cardNumber) {
        for (ListIterator iterator = creditCards.listIterator(); iterator.hasNext(); ) {
            CreditCard creditCard = (CreditCard) iterator.next();
            if (cardNumber.equals(creditCard.getCardNumber())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
  
    /**
     * Checks if the member has multiple credit cards
     * @return true if the member has multiple credit cards
     */
    public boolean multipleCreditCards() {
        if (creditCards.size() > 1) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns an iterator for the credit cards
     * @return iterator for the credit cards of the member
     */
    public Iterator getCreditCards() {
        return creditCards.iterator();
    }
  
    /**
     * Getter for name
     * @return member name
     */
    public String getName() {
        return name;
    }
  
    /**
     * Getter for phone number
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }
  
    /**
     * Getter for address
     * @return member address
     */
    public String getAddress() {
        return address;
    }
  
    /**
     * Getter for ID
     * @return member ID
     */
    public String getId() {
        return id;
    }
  
    /**
     * Setter for name
     * @param member's new name
     */
    public void setName(String newName) {
        name = newName;
    }
  
    /**
     * Setter for address
     * @param member's new address
     */
    public void setAddress(String newAddress) {
        address = newAddress;
    }
  
    /**
     * Setter for phone
     * @param member's new phone
     */
    public void setPhone(String newPhone) {
        phone = newPhone;
    }
    
    /**
     * Checks whether the member is equal to the one with the given ID
     * @param ID of the member who should be compared
     * @return true if the member IDs match
     */
    public boolean equals(String id) {
        return this.id.equals(id);
    }
  
    /** 
     * String form of the member
     */
    @Override
    public String toString() {
        String string = "Member: Name {" + name + "}, Address {" + address + "}, ID {" + id + "}, Phone {"
                + phone + "}";
        Iterator iterator = creditCards.iterator();
        while(iterator.hasNext()) {
            string += "\n  " + iterator.next();
        }
        return string;
    }
}