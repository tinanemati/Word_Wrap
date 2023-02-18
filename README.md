# Word Wrap Program 
Implemented a program which will word wrap and pad a text file based on a given right margin. The program should run on all the system that have Java JDK and Java JRE; for this particular program I used Java SE version 10, and the IDE I used was eclipse IDE for java developers(version 4.11.0) on windows 10. 

### Example of an input and the expected output

Given the following text and a right margin of 20:

  ```
  JK Rowling, the creator of the Harry Potter adventures, is donating £1m to charities supporting vulnerable people during the lockdown.[\n]
  [\n]
  Half of the money will go to Crisis which helps homeless people, and half to Refuge to support victims of domestic abuse.
  ```

The following output will be created:

```
JK   Rowling,   the[\n]
creator    of   the[\n]
Harry        Potter[\n]
adventures,      is[\n]
donating   £1m   to[\n]
charities[\n]
supporting[\n]
vulnerable   people[\n]
during          the[\n]
lockdown.[\n]
[\n]
Half  of  the money[\n]
will  go  to Crisis[\n]
which         helps[\n]
homeless    people,[\n]
and  half to Refuge[\n]
to  support victims[\n]
of  domestic abuse.
```

* Where [\n] has been used to represent the newline character.

## The program takes in 3 parameters:
1. An input file containing the text unformatted
    * This file is a simple text file
    * This parameter will be specified via the command line flag `-input`
2. A number containing the right margin to be used for word wrapping 
    * This parameters will be specified via command line flag `-margin`
3. An output file to produce the output: a word-wrapped, padded, text
    * This parameter will be specified via command line flag `-output`

### As an Example: 

Assuming your main class is called `Main` 

```java -cp ... Main -input unformated_text.txt -margin 20 -output output_file.txt```

  * Part of the program requires to validate these parameters also (making sure the file exists, can be read etc, the margin number is valid etc).

## Rules implemented for word wrapping:
1. If the line contains only one word, or one word followed by punctuation (comma, fullstop, semi-colon etc) no padding needed.
2. If at least 2 words on the line, pad the text with spaces such that the first word starts at column 1 and the last character of the line is exactly on the right margin (20 in the above).
3. Empty lines (just [\n]) remain unchanged.
4. If a single word is encountered which is longer than the margin, it will appear by itself on a single line.
5. Last line of a paragraph doesn't have to be padded.
    * A paragraph is defined as the text in between 2 new lines or a new line and end of text.

##
**Note that this is NOT the optimal solution, but rather a solution which word wraps and pads the text in line with the guidelines above.**
