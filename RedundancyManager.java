import java.io.*;
import java.util.*;

public class RedundancyManager {

  private Map<Integer, Set<Integer>> lineMap;

  public RedundancyManager() {
    lineMap = new TreeMap<Integer, Set<Integer>>();
  }

  // compares if 2 lines are similar and checks if a line is longer than 100 characters
  public void compare(File inputFile, String outputFileName) throws FileNotFoundException {
    PrintStream output = new PrintStream(new File(outputFileName));
    Scanner comparingLine = new Scanner(inputFile);
    int mainLineNumber = 0;
    int aheadLineNumber = 0;
    int great = 0;
    double same = 0;
    double total = 0;
    String aheadLines = "";
    while (comparingLine.hasNextLine()) {
      String lineToCompare = comparingLine.nextLine();
      Scanner otherLines = new Scanner(inputFile);
      mainLineNumber++;
      aheadLineNumber = 0;
      boolean longLine = false;
      if (lineToCompare.length() >= 100) {
        longLine = true;
      }
      if (lineToCompare.contains("//")) {
        continue;
      }
      lineToCompare = lineToCompare.replace(" ", "");
      int comparingLineLength = lineToCompare.length();
      while (otherLines.hasNextLine()) {
        aheadLines = otherLines.nextLine();
        aheadLineNumber++;
        aheadLines = aheadLines.replace(" ", "");
        int aheadLinesLength = aheadLines.length();
        if (aheadLinesLength > comparingLineLength) {
          great = comparingLineLength;
        } else {
          great = aheadLinesLength;
        }
        for (int i = 0; i < great; i++) {
          if (aheadLines.charAt(i) != '{' && aheadLines.charAt(i) != '}') {
            if (aheadLines.charAt(i) == lineToCompare.charAt(i)) {
              same++;
            }
            total++;
          }
        }

        output(
            same,
            total,
            mainLineNumber,
            aheadLineNumber,
            aheadLines,
            lineToCompare,
            output,
            longLine);
        total = 0;
        same = 0;
      }
      if (longLine) {
        output.println(">> Line number " + mainLineNumber + " has more than 100 characters");
        output.println();
      }
    }

    String stringy = Arrays.toString(lineMap.entrySet().toArray());
    System.out.println(stringy);
    for (int i = 1; i < stringy.length() - 1; i++) {
      if (stringy.charAt(i) == ']') {
        output.println();
        i += 2;
      } else if (stringy.charAt(i) != '[') {
        output.print(stringy.charAt(i));
      }
    }
  }

  public void output(
      double same,
      double total,
      int mainLineNumber,
      int aheadLineNumber,
      String aheadLines,
      String lineToCompare,
      PrintStream output,
      boolean longLine) {
    double percentage = (same / total) * 100;
    if (percentage >= 98 && mainLineNumber < aheadLineNumber && same != 0) {
      if (lineMap.containsKey(mainLineNumber)) {
        lineMap.get(mainLineNumber).add(aheadLineNumber);
      } else {
        Set<Integer> aheadLineSet = new TreeSet<Integer>();
        aheadLineSet.add(aheadLineNumber);
        lineMap.put(mainLineNumber, aheadLineSet);
      }
    }
  }
}
