package info.ginpei.voicecalc;

import java.text.NumberFormat;

public class Calculator {
    final static private String TAG = "VoiceCalc#Calculator";

    protected String input;
    protected String lastInput;
    protected String operator;
    protected double result;

    public Calculator() {
        clear();
    }

    public void setInput(String number) {
        input = number;
    }

    public void addNumber(String digit) {
        if (digit.equals("0") && input.equals("0")) {
            // just ignore second zero
        } else if (digit.equals(".")) {
            if (input.isEmpty()) {
                // given a dot before numbers
                input = "0.";
            } else if (!input.contains(".")) {
                // ignore second dot
                input += digit;
            }
        } else {
            input += digit;
        }

        log(String.format("%s <- %s", input, digit));
    }

    public void calculate(String nextOperator) {
        log(nextOperator);

        if (input.isEmpty() && nextOperator.isEmpty()) {
            nextOperator = operator;
            input = lastInput;
        }

        // do not update result when just operator is changed
        if (!input.isEmpty()) {
            updateResult();
        }

        if (!nextOperator.isEmpty()) {
            operator = nextOperator;
        }
    }

    protected void updateResult() {
        double right = Double.parseDouble(input);

        switch (operator) {
            case "+":
                result += right;
                break;

            case "-":
                result -= right;
                break;

            case "*":
                result *= right;
                break;

            case "/":
                result /= right;
                break;

            case "":
                result = right;
                break;
        }

        lastInput = input;
        input = "";
    }

    public void clear() {
        input = "";
        lastInput = "";
        result = 0;
        operator = "";
    }

    public String getPrintText() {
        log(String.format("%f %s %s", result, operator, input));

        String text;
        if (input.isEmpty()) {
            text = NumberFormat.getInstance().format(result);
        } else {
            text = input;
        }
        return text;
    }

    private void log(String format) {
//        Log.d(TAG, format);
    }
}
