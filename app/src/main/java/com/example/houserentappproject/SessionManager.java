package com.example.houserentappproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "HouseRentAppSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_PHOTO = "userPhoto";
    private static final String KEY_SELECTED_CITY = "selectedCity";
    private static final String KEY_SELECTED_LANDMARK = "selectedLandmark";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(String email, String name, String photoUrl) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_PHOTO, photoUrl);
        editor.commit();
    }

    public void saveSelectedCity(String city) {
        editor.putString(KEY_SELECTED_CITY, city);
        editor.commit();
    }

    public void saveSelectedLandmark(String landmark) {
        editor.putString(KEY_SELECTED_LANDMARK, landmark);
        editor.commit();
    }

    public String getSelectedCity() {
        return pref.getString(KEY_SELECTED_CITY, "");
    }

    public String getSelectedLandmark() {
        return pref.getString(KEY_SELECTED_LANDMARK, "");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    public String getUserPhoto() {
        return pref.getString(KEY_USER_PHOTO, "");
    }
} 