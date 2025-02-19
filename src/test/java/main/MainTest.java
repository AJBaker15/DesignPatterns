package main;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
//Test class to ensure main prints "Hello World" during run.
public class MainTest {
    @Test
    void mainRunsOK() throws Exception {
        //grab the output from the system and store it in an array.
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        //change the System output to print to the array instead of normal System.out
        System.setOut(new PrintStream(output));
        //run the main method
        Main.main(new String[]{});
        //Set the System.out back to its original point
        System.setOut(System.out);
        //test that the output stored in the array is what is expected.
        assertEquals("Hello World!", output.toString().trim());
    }
}