package bf.esta.kasa;
import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// Adding annotation
// to our Dao class
@androidx.room.Dao

public interface ContactDao {
    // below method is use to
    // add data to database.
    @Insert
    void insert(Contact model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(Contact model);

    // below line is use to delete a
    // specific course in our database.
    @Delete
    void delete(Contact model);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM contact_table")
    void deleteAllCourses();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM contact_table ORDER BY contact_nom ASC")
    LiveData<List<Contact>> getAllCourses();

    //@Query("Select * from contact_table where contact_nom like  :contact_nom")
    //@Query("SELECT * FROM contact_table WHERE contact_nom LIKE :contact_nom OR contact_prenom LIKE :contact_nom")
    @Query("SELECT * FROM contact_table WHERE contact_nom LIKE '%' || :contact_nom || '%' OR contact_prenom LIKE '%' || :contact_nom || '%'")
    LiveData<List<Contact>>getSearchResults(String contact_nom);
    // fun getSearchResults(desc : String) :
}
