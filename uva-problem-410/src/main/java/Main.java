import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// NOTE: This solution was not accepted by Online Judge
public class Main {
  public static void main(String... args) {
    processInput(System.in, System.out);
  }

  public static void processInput(InputStream inputStream, PrintStream outputStream) {
    StringBuilder resultBuilder = new StringBuilder();
    Scanner scanner = new Scanner(inputStream);
    int inputSetCount = 0;

    while (scanner.hasNextLine()) {
      inputSetCount++;

      // read data for current input set
      // the first line contains the number of chambers and the number of specimens
      String firstLine = scanner.nextLine().trim();
      String[] firstLineTokens = firstLine.split("\\s+");
      final int numberOfChambers = Integer.parseInt(firstLineTokens[0]);
      final int numberOfSpecimens = Integer.parseInt(firstLineTokens[1]);

      // the second line contains the weight of each specimen
      String secondLine = scanner.nextLine().trim();
      String[] secondLineTokens = secondLine.split("\\s+");
      int[] inputMasses = new int[numberOfSpecimens];
      int totalMass = 0;

      for (int i = 0; i < numberOfSpecimens; i++) {
        inputMasses[i] = Integer.parseInt(secondLineTokens[i]);
        totalMass += inputMasses[i];
      }

      // compute average mass, which will be used to compute imbalance for each chamber
      double averageMass = ((double) totalMass) / numberOfChambers;

      // add extra items of weight 0 to ensure that the number of items is twice the number of
      // chambers. We can then sort the items and pair up items from the beginning and end of
      // the array
      int[] sortedMasses = new int[2 * numberOfChambers];
      System.arraycopy(inputMasses, 0, sortedMasses, 0, inputMasses.length);
      Arrays.sort(sortedMasses);

      // boolean array to indicate which items have already been processed
      boolean[] processedFlags = new boolean[sortedMasses.length];

      // the list that will contain items for each chamber
      List<List<Integer>> resultList = new ArrayList<>();

      // we are considering items in the order they were present in the input.
      for (int i = 0; i < inputMasses.length; i++) {
        // find current item's position in the sorted array
        int idx1 = Arrays.binarySearch(sortedMasses, inputMasses[i]);

        // check if the item has already been processed
        if (processedFlags[idx1]) {
          continue;
        }

        // find the counterpart of the current item
        int idx2 = sortedMasses.length - 1 - idx1;
        int m = sortedMasses[idx1];
        int n = sortedMasses[idx2];
        List<Integer> list = new ArrayList<>();

        // ensure that items with weight 0 are not included in result
        if (m > 0) {
          list.add(m);
        }

        if (n > 0) {
          list.add(n);
        }

        // mark items as processed
        processedFlags[idx1] = true;
        processedFlags[idx2] = true;
        resultList.add(list);
      }
      
      if (resultList.size() < numberOfChambers) {
        for (int i = resultList.size(); i < numberOfChambers; i++) {
          resultList.add(Collections.emptyList());
        }
      }

      // generate output for the current input set according to the required format
      resultBuilder.append("Set #" + inputSetCount).append("\n");
      double imbalance = 0.0;

      for (int i = 0, n = resultList.size(); i < n; i++) {
        List<Integer> values = resultList.get(i);
        resultBuilder.append(" ").append(i).append(":");
        double sum = 0.0;

        for (Integer x : values) {
          resultBuilder.append(" ").append(x);
          sum += x.doubleValue();
        }

        imbalance += Math.abs(averageMass - sum);
        resultBuilder.append("\n");
      }

      resultBuilder.append(String.format("IMBALANCE = %.5f", imbalance)).append("\n").append("\n");
    }

    scanner.close();
    outputStream.print(resultBuilder.toString());
  }
}
