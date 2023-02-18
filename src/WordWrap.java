import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * A program that will word wrap and pad a text file with the right margin
 * @author Tina Nemati
 */
public class WordWrap {
	/**
     * Format a text with the given margin	
	 * @param list list of all the line in the text file(unformatted)
	 * @param margin the number of margin to be padded
	 * @return a string in a formatted way
	 */
  private static String FormatString(List<String> list, int margin) {
    StringBuilder finalOutput = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      String line = list.get(i).trim();
      String words[] = line.split(" "); // Split line in words.
      if (words.length <= 1) { // One or zero words: Just add them.
        finalOutput.append(line);
        finalOutput.append('\n');
        continue;
      }

      int wordCount = 0;
      // Process all words in the line.
      while (wordCount < words.length) {
        // Find number of words that fit in this line.
        int numWordsThisLine = 0;
        int charReminding = margin;
        int numChars = 0;
        for (int j = wordCount; j < words.length; j++) {
        
          if (words[j].length() < charReminding || charReminding == margin) {
            numWordsThisLine++;
            charReminding -= words[j].length() + 1; // we will need to have at least one 'space'.
            numChars += words[j].length();
          } else {
            break;
          }
        }
        // Add line with proper padding.
        int paddingBetween;
        int reminding;
        if (wordCount + numWordsThisLine == words.length || numWordsThisLine == 1) {
          // If it is end of line or just one word, do not do padding.
          paddingBetween = 1;
          reminding = 0;
        } else {
          paddingBetween = (int) Math.floor((margin - numChars - 1) / (numWordsThisLine - 1));
          reminding = margin - (1 + numChars + paddingBetween * (numWordsThisLine - 1));
        }
        String spaces = new String(new char[paddingBetween]).replace('\0', ' ');
        for (int j = 0; j < numWordsThisLine; j++) { // Use as a counter.
          if (j == numWordsThisLine - 1) { // Last word we might need to add extra.
            for (int r = 0; r < reminding; r++) {
              finalOutput.append(' ');
            }
            finalOutput.append(words[wordCount]);
          } else {
            finalOutput.append(words[wordCount]);
            finalOutput.append(spaces);
          }
          wordCount++;
        }
        finalOutput.append('\n');
      }
    }
    return finalOutput.toString();
  }
  
  public static final String howToRun = "Please run as `-input unformated_text.txt -margin 20 -output output_file.txt`.";

  public static void main(String[] args) {
    String input = "";
    String output = "";
    int margin = -1;

    // Check we get at least 6 arguments (input, output, and margin).
    // Otherwise print run command and example.
    if (args.length != 6) {
      System.err.println(howToRun);
      String exampleInput = "JK Rowling, the creator of the Harry Potter adventures, is donating £1m to charities supporting vulnerable people during the lockdown no nooooo.\n\nHalf of the money will go to Crisis which helps homeless people, and half to Refuge to support victims of domestic abuse.";
      List<String> exampleList = new ArrayList<String>(Arrays.asList(exampleInput.split("\n")));
      int exampleMargin = 20;
      System.err.println("For example for margin "+exampleMargin+" and the input \n--\n"+exampleInput+"\n--\nIt produces:\n");
      System.err.println("--\n"+FormatString(exampleList, exampleMargin)+"--\n");
      return;
    }
    
    // Parse parameters.
    for (int i = 0; i < args.length; i += 2) {
      String arg = args[i];
      if (arg.equals("-input")) input = args[i + 1];
      if (arg.equals("-output")) output = args[i + 1];
      if (arg.equals("-margin")) {
        try {
          margin = Integer.parseInt(args[i + 1]);
        } catch (NumberFormatException e) {
          System.err.println("Margin should be a number.");
          return;
        }
      }
    }
    
    if(input.equals("") || output.equals("") || margin == -1) {
        System.err.println(howToRun);
        return;
    }

    // Check parameters.
    File inputFile = new File(input);
    if (!inputFile.canRead()) {
      System.err.println("File " + input + " could not be read.");
      return;
    }
    File ouputFile = new File(output);
    if (!ouputFile.canWrite()) {
      System.err.println("File " + output + " could not be writen.");
      return;
    }

    if (margin <= 0) {
      System.err.println("Margin " + margin + " should be bigger than 0.");
      return;
    }

    // Read file.
    List<String> list;
    try {
      list = Files.readAllLines(inputFile.toPath(), Charset.defaultCharset());
    } catch (IOException e) {
      System.err.println("Problem reading " + input + ".");
      return;
    }

    // Parse file.
    String finalOutput = FormatString(list, margin);
    // Write result to file.
    try {
      PrintWriter out = new PrintWriter(ouputFile);
      out.print(finalOutput.toString());
      out.close();
    } catch (Exception e) {
      System.err.println("Error writing file.");
    }
  }
}
