package pl.edu.agh.zpsm.annotationTest;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultRes;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface MyPrefs {


    // @DefaultString("JAn")
    @DefaultRes(R.string.prefName)
    String name();

    // The field age will have default value 42
    @DefaultInt(42)
    int age();

//    // The field lastUpdated will have default value 0
//    long lastUpdated();

}