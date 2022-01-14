package pl.edu.agh.zpsm.annotationTest;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.HttpsClient;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.web.client.RestClientException;

@EActivity(R.layout.activity_annotated) // Sets content view to R.layout.activity_annotated
public class ActivityAnnotated extends AppCompatActivity  {

    @RestService
    RestClient restClient;

    @Extra String someExtra;

//    @ViewById Button btnGoToBasicActivity;  // @ViewById(R.id.btnGoToBasicActivity)
    @ViewById EditText editTextName;            // @ViewById(R.id.editTextName)
    @ViewById EditText editTextAge;             // @ViewById(R.id.editTextAge)
    @ViewById Spinner spinnerMonths;            // @ViewById(R.id.spinnerMonths)
    @ViewById TextView taskDesc;


    @StringRes String annotatedActivity;        // @StringRes (R.string.annotatedActivity)

    @AfterViews
    void init(){
        // #1 RESOURCE
        getSupportActionBar().setTitle(annotatedActivity);


        // #5 SPINNER ITEM SELECTED
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerMonths.setAdapter(adapter);
        //  spinnerMonths.setOnItemSelectedListener(this); // brak setOnItemSelectedListener
    }

    // #2 BUTTON CLICK
    @Click      // ({R.id.btnGoToBasicActivity})
    public void btnGoToBasicActivityClicked() {
        // #3 INTENT
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SOME_EXTRA", "Sent from annotated activity");
        startActivity(intent);
    }

    @AfterExtras
    public void onAfterExtrasInjection() {
        // someExtra is set to the value contained in the incoming intent
        // if an intent does not contain one of the extra values the field remains unchanged
        Toast toast = Toast.makeText(this, someExtra, Toast.LENGTH_SHORT);
        toast.show();
    }


    // #4 SHARED PREFERENCES - simple dictionary like data storage
    @Pref
    MyPrefs_ myPrefs;

    // Fetch the stored data in onResume() Because this is what will be called when the app opens again
    @Override
    protected void onResume() {
        super.onResume();
        editTextName.setText(myPrefs.name().getOr("Jan"));
        editTextAge.setText(String.valueOf(myPrefs.age().get())); //String.valueOf(a)
    }

    // When the user closes the application onPause() will be called and data will be stored in the SharedPreference
    @Override
    protected void onPause() {
        super.onPause();
        // Batch edit
        myPrefs.edit().name().put(editTextName.getText().toString())
                      .age().put(Integer.parseInt(editTextAge.getText().toString())).apply();
    }


    // #5 SPINNER ITEM SELECTED
    @ItemSelect(R.id.spinnerMonths)
    public void spinnerMonthsItemSelected(boolean selected, String selectedItem) {
        Toast toast = Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT);
        toast.show();
    }


    @Click
    public void btnTaskClicked() {
        taskDesc.setVisibility(View.VISIBLE);
    }




//    @Click
//    public void btnRestClicked() {
//        searchAsync();
//    }
//
//    @Background
//    void searchAsync(){
//        try {
//            String response = restClient.getResult("1507");
//        } catch (RestClientException e) {
//            System.out.println("errorREST "+e);
//        }
//    }

    // #6 BACKGROUND TASK & UITHREAD
    @HttpsClient HttpClient httpsClient;

    @AfterInject
    @Background
    public void securedRequest() {
        try {
            HttpGet httpget = new   HttpGet("https://gorest.co.in/public/v1/posts/1358");
            HttpResponse response = httpsClient.execute(httpget);
            doSomethingWithResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void doSomethingWithResponse(HttpResponse resp) {
        try {
            Toast.makeText(this, "HTTP status " + resp.getStatusLine().getStatusCode(), Toast.LENGTH_SHORT).show();
        } catch (WindowManager.BadTokenException e) {
            System.out.println(e);
        }
    }
}