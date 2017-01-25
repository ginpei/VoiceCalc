package info.ginpei.voicecalc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.speech.SpeechRecognizer.*;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "VoiceCalc#MainActivity";
    protected Calculator calculator = new Calculator();
    private SpeechRecognizer speechRecognizer;

    ImageButton recognizeVoiceButton;
    TextView textStatus;
    TextView textResult;
    private Settings settings;
    private Intent recognizerIntent;
    boolean listening = false;
    boolean stopping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = Settings.createInstance(this);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new listener());

        recognizeVoiceButton = (ImageButton) findViewById(R.id.button_recognizeVoice);
        textStatus = (TextView) findViewById(R.id.textView_status);
        textResult = (TextView) findViewById(R.id.input_result);

        textResult.setText(calculator.getPrintText());

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        getPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void number_click(View view) {
        Button button = (Button) view;
        String digit = (String) button.getText();
        calculator.addNumber(digit);
        updateText();
    }

    public void dot_click(View view) {
        calculator.addNumber(".");
        updateText();
    }

    public void voice_click(View view) {
        if (listening) {
            stopListeningStatus();
            stopping = true;
            setStatusText("Stop listening.");
            speechRecognizer.stopListening();
        } else {
            startListeningStatus();
            stopping = false;
            setStatusText("Initializing recognition...");
            speechRecognizer.startListening(recognizerIntent);
        }
    }

    private void startListeningStatus() {
        listening = true;
        Drawable icon = getDrawableById(R.drawable.ic_record_voice_over_black_24dp);
        recognizeVoiceButton.setImageDrawable(icon);
    }

    private void stopListeningStatus() {
        listening = false;
        Drawable icon = getDrawableById(R.drawable.ic_mic_black_24dp);
        recognizeVoiceButton.setImageDrawable(icon);
    }

    private Drawable getDrawableById(@DrawableRes int id) {
        Drawable icon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            icon = getDrawable(id);
        } else {
            icon = getResources().getDrawable(id);
        }
        return icon;
    }

    protected void getPermission() {
        int permittedStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        boolean bShow = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO);
        if (permittedStatus != PackageManager.PERMISSION_GRANTED) {
            if (!bShow) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            }
        }
    }

    public void operator_click(View view) {
        Button button = (Button) view;
        String operator = (String) button.getText();
        calculator.calculate(operator);
        updateText();
    }

    public void equal_click(View view) {
        calculator.calculate("");
        updateText();
    }

    public void clear_click(View view) {
        calculator.clear();
        updateText();
    }

    private void setStatusText(String text) {
        textStatus.setText(text);
    }

    protected void updateText() {
        Log.d(TAG, calculator.getPrintText());
        textResult.setText(calculator.getPrintText());
    }

    protected void parseVoice(String phrase) {
//        parseVoice("3 plus 2 minus 1 equals");

        Log.d(TAG, "Phrase: " + phrase);

        String[] words = phrase.split(" ");
        boolean finishedWithEquals = false;
        for (String word : words) {
            boolean numeric = word.matches("-?\\d+(\\.\\d+)?");
            if (numeric) {
                calculator.setInput(word);
            } else switch (word) {
                case "one":
                    calculator.addNumber("1");
                    break;
                case "two":
                    calculator.addNumber("2");
                    break;
                case "three":
                    calculator.addNumber("3");
                    break;
                case "four":
                    calculator.addNumber("4");
                    break;
                case "five":
                    calculator.addNumber("5");
                    break;
                case "six":
                    calculator.addNumber("6");
                    break;
                case "seven":
                    calculator.addNumber("7");
                    break;
                case "eight":
                    calculator.addNumber("8");
                    break;
                case "nine":
                    calculator.addNumber("9");
                    break;
                case "zero":
                    calculator.addNumber("0");
                    break;
                case "dot":
                    calculator.addNumber(".");
                    break;
                case "plus":
                case "+":
                    calculator.calculate("+");
                    break;
                case "minus":
                case "-":
                    calculator.calculate("-");
                    break;
                case "multiply":
                case "multiplied":
                    calculator.calculate("*");
                    break;
                case "divide":
                case "divided":
                    calculator.calculate("/");
                    break;
                case "equal":
                case "equals":
                    calculator.calculate("");
                    finishedWithEquals = true;
                    break;
                case "clear":
                    calculator.clear();
                    break;
                default:
                    // ignore
            }

            updateText();
        }

        if (!finishedWithEquals && settings.getAutoEquals()) {
            calculator.calculate("");
            updateText();
            Log.d(TAG, "Equals is added automatically.");
        }
    }

    class listener implements RecognitionListener {
        public void onResults(Bundle results) {
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String phrase = "";
            if (data != null) {
                phrase = (String) data.get(data.size() - 1);
            }
            setStatusText("You might say: " + phrase);
            parseVoice(phrase);
        }

        public void onError(int error) {
            stopListeningStatus();
            if (error == ERROR_SPEECH_TIMEOUT && stopping) {
                stopping = false;
            } else {
                String message = getErrorMessage(error);
                setStatusText(message);
                Log.e(TAG, message);
            }
        }

        @NonNull
        private String getErrorMessage(int error) {
            String message;
            switch (error) {
                case ERROR_NETWORK_TIMEOUT:
                case ERROR_NETWORK:
                    message = "Network error. Make sure the network is available.";
                    break;

                case ERROR_AUDIO:
                    message = "Audio error. Do you have available audio stuff?";
                    break;

                case ERROR_SERVER:
                    message = "Server error. Something went wrong...";
                    break;

                case ERROR_CLIENT:
                    message = "Client error. Something went wrong...";
                    break;

                case ERROR_SPEECH_TIMEOUT:
                    message = "Timed out. Did you speak enough loudly?";
                    break;

                case ERROR_NO_MATCH:
                    message = "No match. Did you speak enough clearly?";
                    break;

                case ERROR_RECOGNIZER_BUSY:
                    message = "Recognizer is busy.";
                    break;

                case ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "No permissions. Please allow the app to access to your mic.";
                    break;

                default:
                    message = "Unknown error: " + error;
            }
            return message;
        }

        public void onReadyForSpeech(Bundle params) {
            setStatusText("Waiting for your voice...");
            Toast.makeText(getApplicationContext(), "Speak now", Toast.LENGTH_SHORT).show();
        }

        public void onBeginningOfSpeech() {
            setStatusText("Listening...");
            Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
        }

        public void onBufferReceived(byte[] buffer) {
        }

        public void onEndOfSpeech() {
            setStatusText("Recognizing your voice...");
            Log.d(TAG, "onEndOfSeech");
        }

        public void onPartialResults(Bundle partialResults) {
        }

        public void onEvent(int eventType, Bundle params) {
        }
    }
}
