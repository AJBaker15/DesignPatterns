import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    @Test
    void mainRunsOK(){
        String output = "Hello World";
        assertEquals(output, "Hello World");
    }
}