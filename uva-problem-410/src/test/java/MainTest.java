import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class MainTest {
  private static final String CHAR_SET_NAME = "UTF-8";
  private static final int INITIAL_SIZE = 1024;
  
  private static final int[] INPUT_SET_DESIGNATORS = {1, 2, 3}; 
  
  @Test
  public void test() throws Exception {  
    
    for (int n : INPUT_SET_DESIGNATORS) {
      String inputFileName = String.format("/input/input%d.txt", n);
      String expectedResultFileName = String.format("/result/result%d.txt", n);
      
      InputStream inputStream = getClass().getResourceAsStream(inputFileName); 
      ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream(INITIAL_SIZE);
      PrintStream printStream = new PrintStream(byteOutStream);
      Main.processInput(inputStream, printStream);
      
      Path expectedResultPath = Paths.get(getClass().getResource(expectedResultFileName).toURI());
      String expectedResult = new String(Files.readAllBytes(expectedResultPath), CHAR_SET_NAME);
      String actualResult = byteOutStream.toString(CHAR_SET_NAME);
      
      assertEquals("output different than expected", expectedResult, actualResult);
    }
  }
}
