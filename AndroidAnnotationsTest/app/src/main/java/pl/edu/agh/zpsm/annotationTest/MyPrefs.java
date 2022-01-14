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

    @DefaultInt(42)
    int age();

}