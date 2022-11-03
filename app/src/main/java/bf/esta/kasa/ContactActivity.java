package bf.esta.kasa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//Step 12: Working with the NewCourseActivity.java file
public class ContactActivity extends AppCompatActivity {
    // creating a variables for our button and edittext.
    private EditText courseNameEdt,coursePrenameEdt, courseTelEdt,courseDescEdt;
    private Button courseBtn;
    private ImageView imageView;
    private static final int YOUR_IMAGE_CODE = 3;
    // creating a constant string variable for our
    // course name, description and duration.
    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_COURSE_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_PRENAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_PRENAME";
    public static final String EXTRA_TEL = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_TEL";
    public static final String EXTRA_DESCRIPTION = "com.gtappdevelopers.gfgroomdatabase.EXTRA_COURSE_DESCRIPTION";
    public static final String EXTRA_IMAGE = "bf.esta.kasa.EXTRA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        // initialize imageView
        // with method findViewById()
        imageView = findViewById(R.id.item_planete_image);
        Drawable oldDrawable = imageView.getDrawable();

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivty is the name of new created EmptyActivity.
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "CHOISIR IMAGE!"), YOUR_IMAGE_CODE);
            }
        });

        // initializing our variables for each view.
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        coursePrenameEdt = findViewById(R.id.idEdtCoursePrename);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        courseTelEdt = findViewById(R.id.idEdtCourseTel);
        courseBtn = findViewById(R.id.idBtnSaveCourse);

        // below line is to get intent as we
        // are getting data via an intent.
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            int id = intent.getIntExtra(EXTRA_ID, -1);
            System.out.println("2:"+id);
            int tel = intent.getIntExtra(EXTRA_TEL, 0000);
            System.out.println("3:"+tel);
            // if we get id for our data then we are
            // setting values to our edit text fields.
            courseNameEdt.setText(intent.getStringExtra(EXTRA_COURSE_NAME));
            coursePrenameEdt.setText(intent.getStringExtra(EXTRA_COURSE_PRENAME));
            courseDescEdt.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            //courseTelEdt.setText(Integer.parseInt(intent.getStringExtra(EXTRA_TEL)));
            courseTelEdt.setText(Integer.toString(tel));
            imageView.setImageURI(Uri.parse(intent.getStringExtra(EXTRA_IMAGE)));
        }
        // adding on click listener for our save button.
        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String courseName = courseNameEdt.getText().toString();
                String coursePrename = coursePrenameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String courseTel = courseTelEdt.getText().toString();
                System.out.println("lol===================="+courseImage);
                if (courseName.isEmpty() || coursePrename.isEmpty() || courseDesc.isEmpty() || courseTel.isEmpty()|| courseImage!=null&&courseImage.isEmpty()||imageView.getDrawable()==oldDrawable) {
                    Toast.makeText(ContactActivity.this, "Veuillez entrer les détails contact. Choisir une image aussi!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveCourse(courseName,coursePrename, courseDesc, courseTel,courseImage);
            }
        });
    }
private String courseImage;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == YOUR_IMAGE_CODE && resultCode == RESULT_OK) {
            imageView = findViewById(R.id.item_planete_image);
            Drawable oldDrawable = imageView.getDrawable();
            Uri selectedImageUri = data.getData();
            courseImage=selectedImageUri.toString();
            System.out.println("ori+++++++++++++++++++++"+courseImage);
            imageView.setImageURI(selectedImageUri);
            if (imageView.getDrawable() == oldDrawable)
            {

                Toast.makeText(ContactActivity.this, "Vous n'avez pas changé l'image!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveCourse(String courseName, String coursePrename, String courseDescription, String courseTel,String courseImage) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(EXTRA_COURSE_NAME, courseName);
        data.putExtra(EXTRA_COURSE_PRENAME, coursePrename);
        data.putExtra(EXTRA_DESCRIPTION, courseDescription);
        data.putExtra(EXTRA_TEL, courseTel);
        data.putExtra(EXTRA_IMAGE,courseImage);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Contact a été enregistré dans la Room Database. ", Toast.LENGTH_SHORT).show();
    }
}