import java.io.*;
import java.util.*;
import javax.swing.*;

public class Redundancy {
  public static void main(String args[]) throws FileNotFoundException {
    Scanner sc = new Scanner(System.in);
    String inputFileName = inputFileName(sc);
    File inputFile = new File(inputFileName);
    String outputFileName = outputFileName(sc);
    boolean check = checker(inputFileName, outputFileName);
    if (check) {
      RedundancyManager manager = new RedundancyManager();
      manager.compare(inputFile, outputFileName);
    } else {

    }
  }

  // asking user to input file name
  public static String inputFileName(Scanner sc) {
    String fileName = JOptionPane.showInputDialog("Input File Name", "Enter Input File Name:");
    return fileName;
  }

  // asking user to enter output file name
  public static String outputFileName(Scanner sc) {
    String fileName = JOptionPane.showInputDialog("Output File Name", "Enter Output File Name:");
    return fileName;
  }

  // checking if file exists
  public static boolean checker(String inputFileName, String outputFileName)
      throws FileNotFoundException {
    File inputFile = new File(inputFileName);
    if (!inputFile.exists()) {
      JOptionPane.showMessageDialog(
          null, "alert", "File not found oh Shits!!!", JOptionPane.ERROR_MESSAGE);
    }
    return inputFile.exists();
  }
}
