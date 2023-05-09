package ustis.fitnesscentre.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RegisterReqest {
    private String phoneNumber;
    private CharSequence password;
    private CharSequence passwordConfirmation;
    private String fullName;
    private String birthdayDate;
    private String gender;

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

    public CharSequence getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(CharSequence passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthdayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        formatter = formatter.withLocale(Locale.getDefault());
        return LocalDate.parse(birthdayDate, formatter);
    }

    public void setBirthdayDate(String birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
