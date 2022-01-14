package pl.edu.agh.zpsm.annotationTest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextName, editTextAge;
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #1 RESOURCE
        String actionbarTitle = getResources().getString(R.string.basicActivity);
        getSupportActionBar().setTitle(actionbarTitle);


        // #2 BUTTON CLICK
        Button btnGoToAnnotatedActivity = (Button) findViewById(R.id.btnGoToAnnotatedActivity);
        // btnGoToAnnotatedActivity.setOnClickListener(v -> GoTOAnnotatedActivity(v));  // lambda approach
        btnGoToAnnotatedActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                GoToAnnotatedActivity(v);
            }
        });

        // #3 INTENT
        String someExtra = getIntent().getStringExtra("SOME_EXTRA");
        Toast toast = Toast.makeText(this, someExtra , Toast.LENGTH_SHORT);
        toast.show();


        // #4 SHARED PREFERENCES - simple dictionarylike data storage
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);


        // #5 SPINNER ITEM SELECTED
        spinner = findViewById(R.id.spinnerMonths);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //  Specify the interface implementation
        spinner.setOnItemSelectedListener(this);



    }


    public void GoToAnnotatedActivity(View v) {
        // #3 INTENT
        ActivityAnnotated_.intent(this).someExtra("Sent from basic activity").start();        // Underscore at the end of class name !!!
    }

    // Fetch the stored data in onResume() Because this is what will be called when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
        // Fetching the stored data from the SharedPreference
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("name", "");
        int a = sh.getInt("age", 0);

        // Setting the fetched data in the EditTexts
        editTextName.setText(s1);
        editTextAge.setText(String.valueOf(a));
    }

    // When the user closes the application onPause() will be called and data will be stored in the SharedPreference
    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("name", editTextName.getText().toString());
        myEdit.putInt("age", Integer.parseInt(editTextAge.getText().toString()));
        myEdit.apply();
    }




    // Required by OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view,  int pos, long id) {
        String selectedItem = parent.getItemAtPosition(pos).toString();
        if (!selectedItem.isEmpty()) {
            Toast toast = Toast.makeText(this, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
    }

}