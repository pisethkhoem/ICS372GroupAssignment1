package theatersystem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Represents a single client
 * 
 * @author Robert Novak, Piseth Khoem, Jared Johnson, Daniel Clark 3/2/2016
 * @source Brahma Dathan and Sarnath Ramnath 2010
 */
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String address;
    private String phone;
    private String id;
    private static final String CLIENT_STRING = "C";
    private List shows = new LinkedList();
    
    /**
     * Represents a single client
     * @param name of the client
     * @param address of the client
     * @param phone number of the client
     */
    public Client (String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        id = CLIENT_STRING + (ClientIdServer.instance()).getId();
    }
    
    /**
     * Adds a show for the client
     * @param show to be added for the client
     */
    public void addShow(Show show) {
        shows.add(show);
    }
    
    /**
     * Removes show for a specific client
     * @param name of the show to be removed
     * @return true if the show could be removed
     */
    public boolean removeShow(String name) {
        for (ListIterator iterator = shows.listIterator(); iterator.hasNext(); ) {
            Show show = (Show) iterator.next();
            if (name.equals(show.getName())) {
            iterator.remove();
            return true;
            }
        }
        return false;
    }  
    
    /**
     * Returns an iterator for the shows
     * @return iterator for the all show for the client
     */
    public Iterator getShows() {
        return shows.iterator();
    }
    
    /**
     * Getter for name
     * @return client name
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
     * @return client address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Getter for ID
     * @return client ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Setter for name
     * @param client's new name
     */
    public void setName(String newName) {
        name = newName;
    }
    
    /**
     * Setter for address
     * @param client's new address
     */
    public void setAddress(String newAddress) {
        address = newAddress;
    }
    
    /**
     * Setter for phone
     * @param client's new phone
     */
    public void setPhone(String newPhone) {
        phone = newPhone;
    }
    
    /**
     * Checks whether the client is equal to the one with the given ID
     * @param ID of the client who should be compared
     * @return true if the client IDs match
     */
    public boolean equals(String id) {
        return this.id.equals(id);
    }
    
    /** 
     * String form of the client
     */
    @Override
    public String toString() {
        String string = "Client: Name {" + name + "}, Address {" + address + "}, ID {" + id 
                + "}, Phone {" + phone + "}";
        return string;
    }
}
