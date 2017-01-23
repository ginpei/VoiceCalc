package info.ginpei.voicecalc;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CalculatorTest {
    Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void setInput() throws Exception {
        assertEquals("", calculator.input);

        calculator.setInput("123");
        assertEquals("123", calculator.input);
    }

    @Test
    public void addNumber() throws Exception {
        assertEquals("", calculator.input);

        calculator.setInput("");
        calculator.addNumber("1");
        assertEquals("1", calculator.input);
        calculator.addNumber("2");
        assertEquals("12", calculator.input);

        calculator.setInput("");
        assertEquals("", calculator.input);
        calculator.addNumber("0");
        assertEquals("0", calculator.input);
        calculator.addNumber("0");
        assertEquals("adding some zeros makes one zero", "0", calculator.input);

        calculator.setInput("");
        calculator.addNumber(".");
        assertEquals("0.", calculator.input);
        calculator.addNumber(".");
        assertEquals("ignores a dot just after the other dot", "0.", calculator.input);
        calculator.addNumber("0");
        calculator.addNumber("0");
        assertEquals("accepts multiple zeros after dot", "0.00", calculator.input);
        calculator.addNumber(".");
        assertEquals("ignore a dot following numbers after the first dot", "0.00", calculator.input);
    }

    @Test
    public void calculate() throws Exception {

    }

    @Test
    public void updateResult() throws Exception {
        calculator.result = 12.3;
        calculator.operator = "+";
        calculator.input = "23.4";
        calculator.updateResult();
        assertEquals(35.7, calculator.result);
        assertEquals("23.4", calculator.lastInput);
        assertEquals("", calculator.input);

        calculator.result = 3.14;
        calculator.operator = "-";
        calculator.input = "1.1";
        calculator.updateResult();
        assertEquals(2.04, calculator.result);
        assertEquals("1.1", calculator.lastInput);
        assertEquals("", calculator.input);

        calculator.result = 1.2;
        calculator.operator = "*";
        calculator.input = "1.1";
        calculator.updateResult();
        assertEquals(1.32, calculator.result);
        assertEquals("1.1", calculator.lastInput);
        assertEquals("", calculator.input);

        calculator.result = 4.5;
        calculator.operator = "/";
        calculator.input = "1.5";
        calculator.updateResult();
        assertEquals(3.0, calculator.result);
        assertEquals("1.5", calculator.lastInput);
        assertEquals("", calculator.input);

        calculator.result = 4.5;
        calculator.operator = "";
        calculator.input = "1.5";
        calculator.updateResult();
        assertEquals(1.5, calculator.result);
        assertEquals("1.5", calculator.lastInput);
        assertEquals("", calculator.input);
    }

    @Test
    public void clear() throws Exception {
        calculator.input = "123";
        calculator.lastInput = "123";
        calculator.result = 123;
        calculator.operator = "+";

        calculator.clear();
        assertEquals("", calculator.input);
        assertEquals("", calculator.lastInput);
        assertEquals(0.0, calculator.result);
        assertEquals("", calculator.operator);
    }

    @Test
    public void getPrintText() throws Exception {
        assertEquals("0", calculator.getPrintText());

        calculator.result = 12.3;
        calculator.input = "";
        assertEquals("prints result for empty input", "12.3", calculator.getPrintText());

        calculator.result = 12.3;
        calculator.input = "23.4";
        assertEquals("prints input", "23.4", calculator.getPrintText());

        calculator.input = "123";
        assertEquals("prints value without dot", "123", calculator.getPrintText());
    }

    private void log()
    {

    }
}
