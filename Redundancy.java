import java.util.*;
import java.io.*;
import javax.swing.*;

public class Redundancy{
  public static void main(String args[])throws FileNotFoundException{
    Scanner sc = new Scanner(System.in);
    String inputFileName = inputFileName(sc);
    String outputFileName = outputFileName(sc);
    checker(inputFileName, outputFileName);
  }

  //asking user to input file name
  public static String inputFileName(Scanner sc){
    String fileName = JOptionPane.showInputDialog("Input File Name", "Enter Input File Name:");
    return fileName;
  }

  //asking user to enter output file name
  public static String outputFileName(Scanner sc){
    String fileName = JOptionPane.showInputDialog("Output File Name", "Enter Output File Name:");
    return fileName;
  }

  //checking if file exists
  public static void checker(String inputFileName, String outputFileName)throws FileNotFoundException{
    File inputFile = new File(inputFileName);
    if(inputFile.exists()){
      compare(inputFile, outputFileName);
    }else{
      JOptionPane.showMessageDialog(null, "alert", "File not found oh Shits!!!", JOptionPane.ERROR_MESSAGE);
    }
  }

  //compares if 2 lines are similar and checks if a line is longer than 100 characters
  public static void compare(File inputFile, String outputFileName)throws FileNotFoundException{
    PrintStream output = new PrintStream(new File(outputFileName));
    Scanner comparingLine = new Scanner(inputFile);
    int mainLineNumber = 0;
    int aheadLineNumber = 0;
    int great = 0;
    double same = 0;
    double total = 0;
    String aheadLines="";
    while(comparingLine.hasNextLine()){
      String lineToCompare = comparingLine.nextLine();
      Scanner otherLines = new Scanner(inputFile);
      mainLineNumber++;
      aheadLineNumber = 0;
      boolean longLine = false;
      if(lineToCompare.length() >= 100){
        longLine = true;
      }
      lineToCompare = lineToCompare.replace(" ","");
      int comparingLineLength = lineToCompare.length();
      while(otherLines.hasNextLine()){
        aheadLines = otherLines.nextLine();
        aheadLineNumber++;
        aheadLines = aheadLines.replace(" ","");
        int aheadLinesLength = aheadLines.length();
        if(aheadLinesLength > comparingLineLength){
          great = comparingLineLength;
        }else{
          great = aheadLinesLength;
        }
        for(int i = 0; i < great; i++){
          if(aheadLines.charAt(i) != '{' && aheadLines.charAt(i) != '}' ){
            if(aheadLines.charAt(i) == lineToCompare.charAt(i)){
              same++;
            }
            total++;
          }
        }
        output(same,total,mainLineNumber,aheadLineNumber,aheadLines,lineToCompare,output,longLine);
        total = 0;
        same = 0;
      }
      if(longLine){
        output.println(">> Line number " + mainLineNumber + " has more than 100 characters");
        output.println();
      }
    }
  }

  //prints percentage similarity in lines
  public static void output(double same,double total,int mainLineNumber,int aheadLineNumber ,String aheadLines, String lineToCompare, PrintStream output, boolean longLine){
    double percentage = (same/total)*100;
    if(percentage>=98 && mainLineNumber<aheadLineNumber && same !=0){
      output.println(">> Line number "+mainLineNumber+" and "+aheadLineNumber+" are "+percentage+
      "% similar and could be redundant");
      output.println(lineToCompare+" & "+aheadLines);
      output.println();
    }
  }
}
