package phonebook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {

    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DB() {
        //megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection létrehozásával");
            System.out.println("" + ex);
        }
        //ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Nem sikerült létrehozni a createstatement-et");
                System.out.println("" + ex);
            }
        }
        //megnézzük üres-e az adatbázis
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Nem sikerült a Databasemetadata létrehozása");
            System.out.println("" + ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if (!rs.next()) {
                createStatement.execute("create table contacts("
                        + "id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "lastname varchar(20), firstname varchar(20), email varchar(40), mobile varchar(30))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásával");
            System.out.println("" + ex);
        }

    }

    public ArrayList<Person> getAllContacts() {
        String sql = "select * from contacts";
        ArrayList<Person> persons = null;

        try {
            ResultSet rs = createStatement.executeQuery(sql);
            persons = new ArrayList<>();

            while (rs.next()) {
                Person actualPerson = new Person(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"), rs.getString("email"), rs.getString("mobile"));
                persons.add(actualPerson);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az arraylistbe töltéssel");
            System.out.println("" + ex);
        }
        return persons;
    }

    public void addPerson(Person person) {
        try {
            String sql = "insert into contacts (lastname,firstname,email,mobile) values (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, person.getLastName());
            pstmt.setString(2, person.getFirstName());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getMobile());
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a person hozzáadásakor");
            System.out.println("" + ex);
        }

    }

    public void updatePerson(Person person) {
        try {
            String sql = "update contacts set lastname = ?, firstname = ?, email = ?, mobile = ?, where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, person.getLastName());
            pstmt.setString(2, person.getFirstName());
            pstmt.setString(3, person.getEmail());
            pstmt.setString(4, person.getMobile());
            pstmt.setInt(4, Integer.parseInt(person.getId()));
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a modosítások rögzitésével");
            System.out.println("" + ex);
        }

    }

    public void removeContact(Person person) {
        try {
            String sql = "delete from contacts where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(person.getId()));
            pstmt.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println("" + ex);
        }
    }
}
