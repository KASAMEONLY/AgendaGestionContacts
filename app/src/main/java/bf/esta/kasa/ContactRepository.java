package bf.esta.kasa;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;
public class ContactRepository {
    // below line is the create a variable
    // for dao and list for all courses.
    private ContactDao dao;
    private LiveData<List<Contact>> allCourses;

    // creating a constructor for our variables
    // and passing the variables to it.
    public ContactRepository(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        dao = database.Dao();
        allCourses = dao.getAllCourses();
    }

    // creating a method to insert the data to our database.
    public void insert(Contact model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    // creating a method to update data in database.
    public void update(Contact model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(Contact model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<Contact>> getAllCourses() {
        return allCourses;
    }

    @WorkerThread
    public LiveData<List<Contact>> search(String contact_name){
        return dao.getSearchResults(contact_name);
    }

    // we are creating a async task method to insert new course.
    private static class InsertCourseAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private InsertCourseAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateCourseAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private UpdateCourseAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao dao;

        private DeleteCourseAsyncTask(ContactDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Contact... models) {
            // below line is use to delete
            // our course modal in dao.
            dao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao dao;
        private DeleteAllCoursesAsyncTask(ContactDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllCourses();
            return null;
        }
    }
}
