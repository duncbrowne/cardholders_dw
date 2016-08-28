package com.accessone.core;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    private long cardholderID;

    private String title;
    private String firstName;
    private String surname;
    private String employeeNumber;
    private long departmentID;
    private String emailAddress;
    private DateTime dateCreated;
    private DateTime lastModified;

    public Cardholder() {
    }

    public Cardholder(long cardholderID, String title, String firstName, String surname,
                      String employeeNumber, long departmentID, String emailAddress)
    {
        this.cardholderID = cardholderID;
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.employeeNumber = employeeNumber;
        this.emailAddress = emailAddress;
        this.departmentID = departmentID;
    }

    public Cardholder(long cardholderID, String title, String firstName, String surname,
                      String employeeNumber, long departmentID, String emailAddress,
                      DateTime dateCreated, DateTime lastModified)
    {
        this.cardholderID = cardholderID;
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.employeeNumber = employeeNumber;
        this.emailAddress = emailAddress;
        this.departmentID = departmentID;
        this.dateCreated = dateCreated;
        this.lastModified = lastModified;
    }

    public long getCardholderID()
    {
        return cardholderID;
    }

    public void setCardholderID(long cardholderID)
    {
        this.cardholderID = cardholderID;
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

    public String getDateCreated()
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(dateCreated);
    }

    public void setDateCreated (String dateCreatedAsString)
    {
        dateCreated = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                .parseDateTime(dateCreatedAsString)
                .withZone(DateTimeZone.getDefault());
    }

    public String getLastModified()
    {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        return fmt.print(lastModified);
    }

    public void setLastModified (String lastModifiedAsString)
    {
        lastModified = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                .parseDateTime(lastModifiedAsString)
                .withZone(DateTimeZone.getDefault());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Cardholder)) return false;

        Cardholder that = (Cardholder) o;

        if (getCardholderID() != that.getCardholderID()) return false;
        if (getDepartmentID() != that.getDepartmentID()) return false;
        if (!title.equals(that.title)) return false;
        if (!getFirstName().equals(that.getFirstName())) return false;
        if (!getSurname().equals(that.getSurname())) return false;
        if (!getEmailAddress().equals(that.getEmailAddress())) return false;
        return getEmployeeNumber().equals(that.getEmployeeNumber());
    }

    @Override
    public int hashCode()
    {
        int result = (int) (getCardholderID() ^ (getCardholderID() >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getSurname().hashCode();
        result = 31 * result + getEmployeeNumber().hashCode();
        result = 31 * result + getEmailAddress().hashCode();
        result = 31 * result + (int) (getDepartmentID() ^ (getDepartmentID() >>> 32));
        return result;
    }
}
