package phonebook;

import javafx.beans.property.SimpleStringProperty;

public class Person {

    private final SimpleStringProperty lastName;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty email;
    private final SimpleStringProperty mobile;
    private final SimpleStringProperty id;

    public Person() {
        this.lastName = new SimpleStringProperty("");
        this.firstName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.id = new SimpleStringProperty("");
        this.mobile = new SimpleStringProperty("");
    }

    public Person(String lName, String fName, String eMail, String mObile) {
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(eMail);
        this.mobile = new SimpleStringProperty(mObile);
        this.id = new SimpleStringProperty("");
    }

    public Person(Integer id,String lName, String fName, String eMail,String mObile) {
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.email = new SimpleStringProperty(eMail);
        this.mobile = new SimpleStringProperty(mObile);
        this.id = new SimpleStringProperty(String.valueOf(id));
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lName) {
        lastName.set(lName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        firstName.set(fName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String eMail) {
        email.set(eMail);
    }
    
    public String getMobile() {
        return mobile.get();
    }

    public void setMobile(String mObile) {
        mobile.set(mObile);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String fId) {
        id.set(fId);
    }
}
