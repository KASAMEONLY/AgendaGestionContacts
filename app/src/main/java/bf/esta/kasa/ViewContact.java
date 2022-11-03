package bf.esta.kasa;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
public class ViewContact extends AndroidViewModel {
    // creating a new variable for course repository.
    private ContactRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<Contact>> allCourses;

    // constructor for our view modal.
    public ViewContact(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allCourses = repository.getAllCourses();
    }

    // below method is use to insert the data to our repository.
    public void insert(Contact model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(Contact model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(Contact model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<Contact>> getAllCourses() {
        return allCourses;
    }


    public LiveData<List<Contact>> searchForItems(String contact_name) {
        return repository.search(contact_name);
    }
}
