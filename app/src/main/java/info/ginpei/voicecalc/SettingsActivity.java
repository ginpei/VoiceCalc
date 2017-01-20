package info.ginpei.voicecalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends AppCompatActivity {

    private Settings settings;
    private CheckBox autoEqualsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = Settings.createInstance(this);
        initViews();
    }

    private void initViews() {
        autoEqualsCheckBox = (CheckBox) findViewById(R.id.autoEqualsCheckBox);
        autoEqualsCheckBox.setChecked(settings.getAutoEquals());
    }

    public void autoEqualsCheckBox_click(View view) {
        boolean checked = autoEqualsCheckBox.isChecked();
        settings.setAutoEquals(checked);
    }
}
