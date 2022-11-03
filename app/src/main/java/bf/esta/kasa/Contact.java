package bf.esta.kasa;

import androidx.room.Entity;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// below line is for setting table name.
@Entity(tableName = "contact_table")
public class Contact {
    // below line is to auto increment
    // id for each course.
    @PrimaryKey(autoGenerate = true)

    // variable for our id.
    private int id;

    // below line is a variable
    // for course name.
    private String contact_nom;
    private String contact_prenom;
    private String contact_image;
    // below line is use for
    // course description.
    private String contact_desc;

    // below line is use
    // for course duration.
    private int contact_tel;

    // below line we are creating constructor class.
    // inside constructor class we are not passing
    // our id because it is incrementing automatically
    public Contact(String contact_nom, String contact_prenom,int contact_tel, String contact_desc,String contact_image) {
        this.contact_nom = contact_nom;
        this.contact_prenom = contact_prenom;
        this.contact_tel = contact_tel;
        this.contact_desc = contact_desc;
        this.contact_image=contact_image;
    }

    // on below line we are creating
    // getter and setter methods.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact_nom() {
        return contact_nom;
    }

    public void setContact_nom(String contact_nom) {
        this.contact_nom = contact_nom;
    }

    public String getContact_prenom() {
        return contact_prenom;
    }

    public void setContact_prenom(String contact_prenom) {
        this.contact_prenom = contact_prenom;
    }

    public String getContact_desc() {
        return contact_desc;
    }

    public void setContact_desc(String contact_desc) {
        this.contact_desc = contact_desc;
    }

    public int getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(int contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getContact_image() {
        return contact_image;
    }

    public void setContact_image(String contact_image) {
        this.contact_image = contact_image;
    }
}
