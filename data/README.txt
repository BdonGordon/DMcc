/*---------------------------------------------------------------------
 *                      README
 * Name: Brandon Gordon
 * Student ID: 0850874
 * Assignment: 3
 * Course: CIS2750 [W15]
 * Professor: David McCaughan 
 *---------------------------------------------------------------------
 */

**************
COMPILE
**************
On a Linux Operating System (or Windows 8 or Mac) open a terminal and on the command line type: 	
[make] 	
Note: DO NOT include the '[' ']'. 

******************
RUN ****OPTIONAL 
******************
Once the program is compiled on the command line type: 
[export LD_LIBRARY_PATH=./]
[java Dialogc]

Note: DO NOT include the '[' ']'. 
Note: the Library path does not work within the makefile there must be set manually.
*******NOTE: There will be an executable called "scanner" that is created from the makefile. Although I failed to a section of the program (explained in BUGS/FAILURES 7), if you type on the command line:
[./scanner < fileName.config]
it will show everything parsed by the Lex & Yacc program. It just was not applied to the java program. 

**************
RESOURCES
**************
1. http://stackoverflow.com/questions/14919366/how-to-compile-library-on-c-using-gcc 
2. http://www.nongnu.org/avr-libc/user-manual/library.html
->How to Make a library
3. http://www.geeksforgeeks.org/given-a-linked-list-which-is-sorted-how-will-you-insert-in-sorted-way/
4. Tutors: Michael -> helped with the lists of the ParameterList and the List that stores the values
           Eric -> helped with the same thing as Michael and helped with the helper functions to make the program 
           more modular
5. http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java
*****USED THIS WEBSITE IN ORDER TO USE SPRINGLAYOUT TO MAKE THE DIALOG GUI******
->Specifically the two methods: makeGrid() and makeCompactGrid()          
6. http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html 
->How to use a JFileChooser 
7. Online PDF provided on the main website (dmccaugh/teaching/CIS2750) on Lex/Yacc -> learned how to use and implement
Lex & Yacc
**************
BUGS/FAILURES
**************
1. If pname in PM_manage are the same, it returns a zero and print statement identifying which pname is a duplicate, BUT does not exit or handle it in any way. It continues to print out the parameters and values.
2. If you register a parameter and you do not use PM_getValue() function to get the value of that parameter, a segmentation fault occurs (when using the a1example.c that was provided by the professor).
3. THe Config menu does not apply the options that the user chooses to the program.
4.The .config file chosen and saved MUST be saved in the same file as alll the main files (Dialogc.java, GenerateGui.java, etc), in order to compile and run or else a segfault will occur. However, when the user chooses a new working directory, the .config file and all the other essential programs (.class, .java, etc), will be moved to the NEW working directory (but no folder in the directory that has the name of the .config file will be created).
5. If you do NOT choose a compile mode , there will be a "NullExceptionPointerError" because it will try to execute the command to generate the compiled GUI, HOWEVER the main program will still be prompted and not exit. Choosing a compile mode prior to compiling will make the program run smoothly. 
6. Does not handle a list of fields or buttons that are empty.
7. The bottom half of the program is not read or considered. When you enter anything in the textfields for the fields, nothing happens.

