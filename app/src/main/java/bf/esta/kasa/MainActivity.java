package bf.esta.kasa;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // creating a variables for our recycler view.
    private RecyclerView coursesRV;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    private ViewContact viewmodal;
    // initializing adapter for recycler view.
    final ContactAdapter adapter = new ContactAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initializing our variable for our recycler view and fab.
        coursesRV = findViewById(R.id.contacts_id);
        FloatingActionButton fab = findViewById(R.id.btnadd_id);

        // adding on click listener for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // starting a new activity for adding a new course
                // and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });
        // setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);



        // setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);

        // passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewContact.class);
        // below line is use to get all the courses from view modal.
        viewmodal.getAllCourses().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
                //System.out.println("1ici: "+adapter.getItemCount());
                /*if (adapter.getItemCount()==0){
                    emptyView.setVisibility(View.VISIBLE);
                    coursesRV.setVisibility(View.INVISIBLE);
                }
                else{emptyView.setVisibility(View.INVISIBLE);
                    coursesRV.setVisibility(View.VISIBLE);}*/
            }
        });
        //Set onLoaded as observer lambda.
        viewmodal.getAllCourses().observe(this,this::onLoaded);

        // below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // Here is where you'll implement swipe to delete
                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Confirmez action?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Get the position of the item to be deleted
                                //int position = viewHolder.getAdapterPosition();
                                // Then you can remove this item from the adapter
                                // on recycler view item swiped then we are deleting the item of our recycler view.
                                viewmodal.delete(adapter.getContactAt(viewHolder.getAdapterPosition()));
                                Toast.makeText(MainActivity.this, "Contact supprimé!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog,
                                // so we will refresh the adapter to prevent hiding the item from UI
                                //mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                Toast.makeText(MainActivity.this, "Contact non supprimé!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        }).
                // below line is use to attach this to recycler view.
                        attachToRecyclerView(coursesRV);

        // below line is use to set item click listener for our item of recycler view.
        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtra(ContactActivity.EXTRA_ID, model.getId());
                intent.putExtra(ContactActivity.EXTRA_COURSE_NAME, model.getContact_nom());
                intent.putExtra(ContactActivity.EXTRA_COURSE_PRENAME, model.getContact_prenom());
                intent.putExtra(ContactActivity.EXTRA_DESCRIPTION, model.getContact_desc());
                intent.putExtra(ContactActivity.EXTRA_TEL, model.getContact_tel());
                intent.putExtra(ContactActivity.EXTRA_IMAGE, model.getContact_image());
                System.out.println("1:"+model.getContact_tel());
                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });
    }
    private void onLoaded(List<Contact> contacts) {
        TextView emptyView;
        emptyView = (TextView)findViewById(R.id.empty_view);
        if (contacts.size() == 0) {
            coursesRV.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            coursesRV.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
            adapter.submitList(contacts);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(ContactActivity.EXTRA_COURSE_NAME);
            String coursePrename = data.getStringExtra(ContactActivity.EXTRA_COURSE_PRENAME);
            String courseDescription = data.getStringExtra(ContactActivity.EXTRA_DESCRIPTION);
            String courseTel = data.getStringExtra(ContactActivity.EXTRA_TEL);
            String courseImage = data.getStringExtra(ContactActivity.EXTRA_IMAGE);
            Contact model = new Contact(courseName, coursePrename,Integer.parseInt(courseTel),courseDescription,courseImage);
            viewmodal.insert(model);
            Toast.makeText(this, "Contact enregistré!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ContactActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Contact ne peut pas être modifié!", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(ContactActivity.EXTRA_COURSE_NAME);
            String coursePrename = data.getStringExtra(ContactActivity.EXTRA_COURSE_PRENAME);
            String courseDesc = data.getStringExtra(ContactActivity.EXTRA_DESCRIPTION);
            String courseTel = data.getStringExtra(ContactActivity.EXTRA_TEL);
            String courseImage = data.getStringExtra(ContactActivity.EXTRA_IMAGE);
            Contact model = new Contact(courseName, coursePrename,Integer.parseInt(courseTel),courseDesc,courseImage);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Contact modifié", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Contact pas enregistré", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem menuItem=menu.findItem(R.id.menu_search);
        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    Log.e("ici s = ", s);
                    getItemsFromDb(s);Log.e("ici getItemsFromDb = ", "fait");
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null) {
                    Log.e("ici s2 = ", s);
                    getItemsFromDb(s);Log.e("ici getItemsFromDb2 = ", "fait");
                }
                return true;
            }

            private void getItemsFromDb(String s) {
               // String searchText="%$s%";
                viewmodal.searchForItems(s).observe(MainActivity.this,contacts ->{
                    if(contacts!=null){
                        System.out.println(contacts);
                        Log.e("Listsize = ", String.valueOf(contacts.size()));
                        Log.e("List = ", contacts.toString());
                        adapter.submitList(contacts);
                    }
                } );
            }
        });
        return true;
    }

   /* private void getItemsFromDb(String s) {
        var searchText = searchText
        searchText = "%$searchText%"

        myViewModel.searchForItems(desc = searchText).observe(this@MainActivity, Observer { list ->
                list?.let {
            Log.e("List = ", list.toString())
        }

        })
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Toast.makeText(this, "Agenda Gestion Contacts CRUD par KIENOU Aimé,\n  Swiper vers la gauche ou la droite pour supprimer!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}