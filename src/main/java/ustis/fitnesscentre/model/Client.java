package ustis.fitnesscentre.model;

import java.time.LocalDate;

public class Client {
    private Integer id;
    private String phoneNumber;
    private CharSequence password;
    private String fullName;
    private LocalDate birthdayDate;
    private String gender;

    public Client() {
    }

    public Client(String phoneNumber, CharSequence password, String fullName, LocalDate birthdayDate, String gender) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fullName = fullName;
        this.birthdayDate = birthdayDate;
        this.gender = gender;
    }

    public Client(Integer id, String phoneNumber, CharSequence password, String fullName, LocalDate birthdayDate, String gender) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fullName = fullName;
        this.birthdayDate = birthdayDate;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(CharSequence password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
