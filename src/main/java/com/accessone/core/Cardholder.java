package com.accessone.core;

import javax.persistence.*;

/**
 * Current Project cardholder_dropwizard
 * Created by duncan.browne on 21/08/2016.
 */
@Entity
@Table(name = "cardholders")
public class Cardholder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String firstName;
    private String surname;
    private String employeeNumber;
    private long departmentID;
    private String emailAddress;

    /**
     * The default constructor for the Cardholder class.
     */
    public Cardholder() {
        // This is a default constructor that is necessary for some of the hibernate operations.
    }

    /**
     * The Cardholder class constructor with values set.
     * @param id The Cardholder ID of the Cardholder
     * @param title The title of the Cardholder.
     * @param firstName The first name of the Cardholder.
     * @param surname The surname of the Cardholder.
     * @param employeeNumber The employee number of the Cardholder.
     * @param departmentID The department ID of the Cardholder.
     * @param emailAddress The email address of the Cardholder
     */
    public Cardholder(long id, String title, String firstName, String surname,
                      String employeeNumber, long departmentID, String emailAddress)
    {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.employeeNumber = employeeNumber;
        this.emailAddress = emailAddress;
        this.departmentID = departmentID;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long cardholderID)
    {
        this.id = cardholderID;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmployeeNumber()
    {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public long getDepartmentID()
    {
        return departmentID;
    }

    public void setDepartmentID(long departmentID)
    {
        this.departmentID = departmentID;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Cardholder))
            return false;

        Cardholder that = (Cardholder) o;

        if (getId() != that.getId())
            return false;
        if (getDepartmentID() != that.getDepartmentID())
            return false;
        if (!getTitle().equals(that.getTitle()))
            return false;
        if (!getFirstName().equals(that.getFirstName()))
            return false;
        if (!getSurname().equals(that.getSurname()))
            return false;
        if (!getEmailAddress().equals(that.getEmailAddress()))
            return false;
        return getEmployeeNumber().equals(that.getEmployeeNumber());
    }

    @Override
    public int hashCode()
    {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmployeeNumber().hashCode();
        result = 31 * result + getEmailAddress().hashCode();
        result = 31 * result + (int) (getDepartmentID() ^ (getDepartmentID() >>> 32));
        return result;
    }
}
