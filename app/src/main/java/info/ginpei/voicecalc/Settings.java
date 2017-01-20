package info.ginpei.voicecalc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Settings {
    final public static String NAME = "info.ginpei.voicecalc.SETTINGS";
    final public static String KEY_AUTO_EQUALS = "autoEquals";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public static Settings createInstance(Activity activity) {
        SharedPreferences preferences = activity.getSharedPreferences(NAME, MODE_PRIVATE);
        Settings settings = new Settings(preferences);
        return settings;
    }

    @SuppressLint("CommitPrefEdits")
    public Settings(SharedPreferences preferences) {
        this.preferences = preferences;
        editor = preferences.edit();
    }

    public boolean getAutoEquals() {
        return preferences.getBoolean(KEY_AUTO_EQUALS, false);
    }

    public void setAutoEquals(boolean autoEquals) {
        editor.putBoolean(KEY_AUTO_EQUALS, autoEquals);
        editor.apply();
    }
}
