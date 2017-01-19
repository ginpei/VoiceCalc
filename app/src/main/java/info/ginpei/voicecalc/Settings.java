package info.ginpei.voicecalc;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

public class Settings {
    final public static String NAME = "info.ginpei.voicecalc.SETTINGS";
    final public static String KEY_AUTO_EQUALS = "autoEquals";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
