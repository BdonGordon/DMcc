
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brandon
 */
public class mainFrame extends javax.swing.JFrame {
    /**
     * Simple class that lets user know if there has been contents removed
     * or added to the jTextArea
     */
    private class DocListener implements DocumentListener{        

        @Override
        public void insertUpdate(DocumentEvent e) {
            modLabel.setText("[modified]");
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            modLabel.setText("[modified]");
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            modLabel.setText("[modified]");
        }       
    }
    /**
     * 
     */
    private void resetTextArea(){
        jTextArea1.setText("");
    }
    /**
     *This ActionListener is used to handle "New" option from the File
     * Menu
     */
    private class NewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser newFp = new JFileChooser();
            newFp.setDialogTitle("New");
            newFp.setApproveButtonText("Create");           
            String ext = ".config";
            int result;
            result = newFp.showOpenDialog(null);            
            
            if(result == JFileChooser.APPROVE_OPTION){    
                resetTextArea();
                File fp = new File(newFp.getSelectedFile() + ext);
                try {
                    fp.createNewFile() ;
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                String filename = fp.getName();
                String filepath = HelpListeners.noExt(fp.getName());
                jLabel3.setText(filepath);
                mainNameLabel.setText(filename);
                
                String fire = HelpListeners.noExt(mainNameLabel.getText());
                System.out.println(fire);
                modLabel.setText("[modified]");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fp.getPath()));
                    String line = br.readLine();
                    while(line != null){
                        jTextArea1.append(line + "\n");
                        line = br.readLine();
                    }
                    if(line != null){
                        br.close();
                    }
                }catch (FileNotFoundException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                                  
        }
    }
    /**
     * Creates new form mainFrame
     */
    private class OpenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {  
            FileNameExtensionFilter fil = new FileNameExtensionFilter("Config Files (.config)", "config");
            JFileChooser openFile = new JFileChooser();   
            openFile.addChoosableFileFilter(fil);
            int result;
            result = openFile.showOpenDialog(null);            
            
            if(result == JFileChooser.APPROVE_OPTION){
                File fp = openFile.getSelectedFile();
                String filename = fp.getName();
                String filepath = HelpListeners.noExt(fp.getName());
                jLabel3.setText(filepath);                
                mainNameLabel.setText(filename);
                //conFilePath = openFile.getSelectedFile().getAbsolutePath();
                System.out.println(conFilePath);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(fp.getPath()));
                    String line = br.readLine();
                    while(line != null){
                        jTextArea1.append(line + "\n");
                        line = br.readLine();
                    }
                    if(line != null){
                        br.close();
                    }
                }catch (FileNotFoundException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                      
        }
    }
    /**
     * 
     */
    private class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser saveFile = new JFileChooser();
                 
            int result = saveFile.showSaveDialog(null);  
            if(result == JFileChooser.APPROVE_OPTION){
                String line = jTextArea1.getText(); 
                String ext = ".config";                
                File fp = new File(saveFile.getSelectedFile() + ext);                                              
                //saveFile.setCurrentDirectory(fp);
                String filename = fp.getName();
                saveFile.setSelectedFile(new File(filename));
                
                String filepath = HelpListeners.noExt(fp.getName());
                jLabel3.setText(filepath);
                mainNameLabel.setText(filename);
                try{
                    FileWriter fw = new FileWriter(fp.getPath());
                    fw.write(line);
                    fw.flush();
                    fw.close();
                    modLabel.setText("[saved]");
                } catch (IOException ex) {
                    Logger.getLogger(HelpListeners.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }
    
    private class SaveAsListener implements ActionListener {
                
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser saveAs = new JFileChooser();
            saveAs.setApproveButtonText("Save As");
            saveAs.setDialogTitle("Save As");            
            int exit;
            int result = saveAs.showSaveDialog(null);              
            
            if(result == JFileChooser.APPROVE_OPTION){
                String line = jTextArea1.getText(); 
                String ext = ".config";
                File fp = new File(saveAs.getSelectedFile() + ext);
                if(fp.exists() || fp.isFile()){
                    JOptionPane.showMessageDialog(null, "Directory or File already exists");
                }
                else{
                    saveAs.setCurrentDirectory(fp.getParentFile());
                    String filename = fp.getName();
                    String filepath = HelpListeners.noExt(fp.getName());                
                    jLabel3.setText(filepath);
                    mainNameLabel.setText(filename);
                    try{
                        FileWriter fw = new FileWriter(fp.getPath());
                        fw.write(line);
                        fw.flush();
                        fw.close();                    
                        modLabel.setText("[saved]");
                    } catch (IOException ex) {
                        Logger.getLogger(HelpListeners.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    private class EndingListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(modLabel.getText().equals("[modified]")){
                quit.addActionListener(new SaveListener());
            }
            else if(modLabel.getText().equals("[saved]")){
                System.exit(0);
            }
            else{
                System.exit(0);
            }
        }
    }
    
    private class CompiledListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {           
            GenerateGui gui = new GenerateGui();
            dialogTitle = HelpListeners.noExt(mainNameLabel.getText());
            String dialogT = mainNameLabel.getText();
            Dialogc obj = new Dialogc();

            //System.out.println(currWkDir);
            if(dialogTitle.equals("-")){
                JOptionPane.showMessageDialog(null, "Please choose a .config file first.");
            }
            else{
                try {
                    if(ide.isSelected()){                        
                        title = obj.parseConfig(dialogT, "title", 3, 0);
                        dialogFields = obj.parseConfig(dialogT, "fields", 4, 0);
                        dialogButtons = obj.parseConfig(dialogT, "buttons", 4, 0);

                        fieldStr = dialogFields.split(",");
                        buttonStr = dialogButtons.split(",");                        
                    }
                    else if(lexYacc.isSelected()){
                        title = obj.parseConfig(dialogT, "title", 3, 1);
                        dialogFields = obj.parseConfig(dialogT, "fields", 4, 1);
                        dialogButtons = obj.parseConfig(dialogT, "buttons", 4, 1);

                        fieldStr = dialogFields.split(",");
                        buttonStr = dialogButtons.split(",");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select Compile Mode first (found under Config Menu).");
                    }

                    gui.generateGui(title, dialogFields, dialogButtons); 
                    
                    // fieldStr = dialogFields.split(",");
                    // buttonStr = dialogButtons.split(",");
                    //IF(LEXYACC RADIO IS CHECKED)
                    //for (loop through fieldStr)
                    // dialogBotField[0][i] = fieldStr
                    //  dialobotField[1][j] = obj.parseConfig(dialogT, fieldStr[i], 3);
                    //  
                    //}

                    //for (loop through buttonStr)
                    //   dialogBotButton[0][i] = buttonStr[i]
                    //  dialoBotButton[1][i] = obj.parseConfig(dialogT, fieldStr[i], 3);
                    //  
                    //}                    

                    Process newDir = Runtime.getRuntime().exec("mv ./" + dialogTitle + ".java " + "./" + dialogT + " " + currWkDir);                    
                    Process pro = Runtime.getRuntime().exec("javac " + currWkDir + "/" + dialogTitle + ".java");
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } //catch (InterruptedException ex) {
                //     Thread.currentThread().interrupt();
                // }
            }
        }       
    
    }
    
    private class CompiledRunListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){   
            GenerateGui gui = new GenerateGui();
            dialogTitle = HelpListeners.noExt(mainNameLabel.getText());
            String dialogT = mainNameLabel.getText();
            //System.out.println(currWkDir);
            Dialogc obj = new Dialogc();

            if(dialogTitle.equals("-")){
                JOptionPane.showMessageDialog(null, "Please choose a .config file first.");
            }
            else{
                try {
                    if(ide.isSelected()){                        
                        title = obj.parseConfig(dialogT, "title", 3, 0);
                        dialogFields = obj.parseConfig(dialogT, "fields", 4, 0);
                        dialogButtons = obj.parseConfig(dialogT, "buttons", 4, 0);

                        fieldStr = dialogFields.split(",");
                        buttonStr = dialogButtons.split(",");                        
                    }
                    else if(lexYacc.isSelected()){
                        title = obj.parseConfig(dialogT, "title", 3, 1);
                        dialogFields = obj.parseConfig(dialogT, "fields", 4, 1);
                        dialogButtons = obj.parseConfig(dialogT, "buttons", 4, 1);

                        fieldStr = dialogFields.split(",");
                        buttonStr = dialogButtons.split(",");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please select Compile Mode first (found under Config Menu).");
                    }

                    gui.generateGui(title, dialogFields, dialogButtons); 
                    
                    

                    Process newDir = Runtime.getRuntime().exec("mv ./" + dialogTitle + ".java " + currWkDir);                    
                    Process pro = Runtime.getRuntime().exec("javac " + currWkDir + "/" + dialogTitle + ".java");
                    Thread.sleep(3000);
                    Process runT = Runtime.getRuntime().exec("java -classpath " + currWkDir + " " + dialogTitle);
                } catch (IOException ex) {
                    Logger.getLogger(mainFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private class JCompListener implements ActionListener{
        @Override

        public void actionPerformed(ActionEvent e) {
            final JDialog dialog = new JDialog();
            final JLabel current = new JLabel(javaCompiler);
            final JTextField optInp = new JTextField(10);            
            JLabel option = new JLabel("Java Compile Setting:");  
            dialog.setTitle("Java Compiler");
            
            dialog.setSize(300, 90);
            dialog.setLayout(new FlowLayout());
            
            dialog.add(currentSet);
            dialog.add(current);
            
            dialog.add(option);
            dialog.add(optInp);
            
            JButton button = new JButton("Ok");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String theText = optInp.getText();
                    if(theText.equals("")){
                        current.setText(javaCompiler);
                    }
                    else{
                        javaCompiler = theText;
                        current.setText(javaCompiler);                    
                    }
                }              
            });            
            JButton button2 = new JButton("Browse");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    int result;
                    
                    result = fc.showDialog(null, "Select");
                    if(result == JFileChooser.APPROVE_OPTION){
                        File fp = fc.getSelectedFile();
                        String filepath = fp.getAbsolutePath();
                        optInp.setText(filepath);
                    }
                }              
            });
            
            JButton button3 = new JButton("Exit");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog.dispose();
                }              
            });
            button.setVisible(true);
            button.setBounds(10,10,40,40);
            dialog.add(button);
            dialog.add(button2);
            dialog.add(button3);

            //Make dialog visible
            dialog.setVisible(true);
        }
    }
    
    private class CompOptions implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            final JDialog dialog = new JDialog();
            JLabel option = new JLabel("Compiler Setting:"); 
            final JLabel current = new JLabel(cOptDef);
            final JTextField optInp = new JTextField(10);  
            dialog.setTitle("Compile Options");
                       
            dialog.setSize(300, 90);
            dialog.setLayout(new FlowLayout());
            dialog.add(currentSet);
            dialog.add(current);
            dialog.add(option);
            dialog.add(optInp);
            
            JButton button = new JButton("Ok");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String theText = optInp.getText();
                    if(theText.equals("")){
                        current.setText(cOptDef);
                    }
                    else{
                        cOptDef = theText;
                        current.setText(cOptDef);                    
                    }
                }              
            });           
            JButton button2 = new JButton("Browse");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    int result;
                    
                    result = fc.showDialog(null, "Select");
                    if(result == JFileChooser.APPROVE_OPTION){
                        File fp = fc.getSelectedFile();
                        String filepath = fp.getAbsolutePath();
                        optInp.setText(filepath);
                    }
                }              
            });
            
            JButton button3 = new JButton("Exit");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog.dispose();
                }              
            });
            button.setVisible(true);
            button.setBounds(10,10,40,40);
            dialog.add(button);
            dialog.add(button2);
            dialog.add(button3);

            //Make dialog visible
            dialog.setVisible(true);
        }        
    }
    
    private class JavaRunTime implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            final JDialog dialog = new JDialog();
            JLabel option = new JLabel("Java Run-Time Setting:");  
            final JLabel current = new JLabel(jRunDef);
            final JTextField optInp = new JTextField(10);  
            dialog.setTitle("Java Run-Time");
            
            dialog.setSize(300, 90);
            dialog.setLayout(new FlowLayout());
            
            dialog.add(currentSet);
            dialog.add(current);
            
            dialog.add(option);
            dialog.add(optInp);
            
            JButton button = new JButton("Ok");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String theText = optInp.getText();
                    if(theText.equals("")){
                        current.setText(jRunDef);
                    }
                    else{
                        jRunDef = theText;
                        current.setText(jRunDef);                    
                    }
                }              
            });           
            JButton button2 = new JButton("Browse");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    int result;
                    
                    result = fc.showDialog(null, "Select");
                    if(result == JFileChooser.APPROVE_OPTION){
                        File fp = fc.getSelectedFile();
                        String filepath = fp.getAbsolutePath();
                        optInp.setText(filepath);
                    }
                }              
            });
            
            JButton button3 = new JButton("Exit");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog.dispose();
                }              
            });
            button.setVisible(true);
            button.setBounds(10,10,40,40);
            dialog.add(button);
            dialog.add(button2);
            dialog.add(button3);

            //Make dialog visible
            dialog.setVisible(true);
        }
    }
    
    private class RunTimeOptions implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            final JDialog dialog = new JDialog();
            JLabel option = new JLabel("Run-Time Setting:");   
            final JLabel current = new JLabel(runTDef);
            final JTextField optInp = new JTextField(10);  
            dialog.setTitle("Run-Time Options");
            
            dialog.setSize(300, 90);
            dialog.setLayout(new FlowLayout());
            
            dialog.add(currentSet);
            dialog.add(current);
            
            dialog.add(option);
            dialog.add(optInp);
            
            JButton button = new JButton("Ok");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String theText = optInp.getText();
                    if(theText.equals("")){
                        current.setText(runTDef);
                    }
                    else{
                        runTDef = theText;
                        current.setText(runTDef);                    
                    }
                }              
            });             
            JButton button2 = new JButton("Browse");
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    int result;
                    
                    result = fc.showDialog(null, "Select");
                    if(result == JFileChooser.APPROVE_OPTION){
                        File fp = fc.getSelectedFile();
                        String filepath = fp.getAbsolutePath();
                        optInp.setText(filepath);
                    }
                }              
            });
            
            JButton button3 = new JButton("Cancel");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog.dispose();
                }              
            });
            button.setVisible(true);
            button.setBounds(10,10,40,40);
            dialog.add(button);
            dialog.add(button2);
            dialog.add(button3);

            //Make dialog visible
            
        }
    }

    private class ModeListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Radio Stuff            
            final JDialog radioIde = new JDialog();
            ButtonGroup group = new ButtonGroup();
            group.add(ide);
            group.add(lexYacc);
            radioIde.setTitle("Compile Mode");

            radioIde.setSize(200,50);
            radioIde.setLayout(new GridLayout(0,2));

            radioIde.add(ide);
            radioIde.add(lexYacc);            
            

            ide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   //System.out.println("Ide");
                }              
            });

            lexYacc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   //System.out.println("Lex & Yacc");
                }              
            });

            //ide.setSelected(true);
            radioIde.setVisible(true);            
        }
    }
    
    private class WorkDirListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {    
            final JDialog dialog = new JDialog();
            JLabel option = new JLabel("Working Directory:"); 
            //final JLabel current = new JLabel(pathStr);
            final JTextField optInp = new JTextField(10);  
            dialog.setTitle("Working Directory");
            
            dialog.setSize(300, 90);
            dialog.setLayout(new FlowLayout());
            
            dialog.add(currentSet);
            dialog.add(current);
            
            dialog.add(option);
            dialog.add(optInp);
            
            JButton button = new JButton("Ok");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    String theText = optInp.getText();
                    if(theText.equals("")){
                        current.setText(pathStr);                        
                    }
                    else{
                        pathStr = theText;
                        current.setText(pathStr);     
                        currWkDir = theText;   
                        File fp = new File(pathStr);
                        if(fp.exists() || fp.isFile()){
                            JOptionPane.showMessageDialog(null, "Directory or File already exists");
                        }
                        else{
                            if(!fp.exists()){
                                fp.mkdir();
                            }
                        }
                    }
                }              
            });                         
            
            JButton button3 = new JButton("Exit");
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog.dispose();
                }              
            });
            button.setVisible(true);
            button.setBounds(10,10,40,40);
            dialog.add(button);            
            dialog.add(button3);

            //Make dialog visible
            dialog.setVisible(true);
        }            
    }
    
    private class HelpListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "/*-----MORE README IN README.TXT file. PLEASE LOOK AT THAT FIRST-----------------------------\n" +
" *                      README\n" +
" * Name: Brandon Gordon\n" +
" * Student ID: 0850874\n" +
" * Assignment: 2\n" +
" * Course: CIS2750 [W15]\n" +
" * Professor: David McCaughan \n" +
" *---------------------------------------------------------------------\n" +
" */\n" +
"\n" +
"**************\n" +
"COMPILE\n" +
"**************\n" +
"On a Linux Operating System (or Windows 8 or Mac) open a terminal and on the command line type:    \n" +
"[make]     \n" +
"Note: DO NOT include the '[' ']'. \n" +
"\n" +
"******************\n" +
"RUN ****OPTIONAL \n" +
"******************\n" +
"Once the program is compiled on the command line type: \n" +
"[java Main]\n" +
"Note: DO NOT include the '[' ']'. \n" +
"\n" +
"**************\n" +
"RESOURCES\n" +
"**************\n" +
"1. http://stackoverflow.com/questions/14919366/how-to-compile-library-on-c-using-gcc \n" +
"2. http://www.nongnu.org/avr-libc/user-manual/library.html\n" +
"->How to Make a library\n" +
"3. http://www.geeksforgeeks.org/given-a-linked-list-which-is-sorted-how-will-you-insert-in-sorted-way/\n" +
"4. Tutors: Michael -> helped with the lists of the ParameterList and the List that stores the values\n" +
"           Eric -> helped with the same thing as Michael and helped with the helper functions to make the program \n" +
"           more modular\n" +
"5. http://docs.oracle.com/javase/tutorial/uiswing/examples/layout/SpringGridProject/src/layout/SpringUtilities.java\n" +
"*****USED THIS WEBSITE IN ORDER TO USE SPRINGLAYOUT TO MAKE THE DIALOG GUI******\n" +
"->Specifically the two methods: makeGrid() and makeCompactGrid()          \n" +
"6. http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html \n" +
"->How to use a JFileChooser \n" +
"**************\n" +
"BUGS/FAILURES\n" +
"**************\n" +
"1. In terms of the REAL_TYPE values in enumeration, the number must have a decimal point in it or else it will be considered as \"not entered\".\n" +
"2. If pname in PM_manage are the same, it returns a zero and print statement identifying which pname is a duplicate, BUT does not exit or handle it in any way. It continues to print out the parameters and values.\n" +
"3. If you register a parameter and you do not use PM_getValue() function to get the value of that parameter, a segmentation fault occurs (when using the a1example.c that was provided by the professor).\n" +
"4. The application GUI is not created HOWEVER the JNI parsing is fully complete and functional.\n" +
"5. THe Config menu does not apply the options that the user chooses to the program.");
        }
    }
    
    private class AboutListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "* Name: Brandon Gordon\n" +"* Student ID: 0850874\n" +"* Course: CIS2750 [W15]\n" + "* Assignment: 2\n" + "* Professor: David McCaughan");            
        }
    }
    
    public mainFrame() {
        initComponents();

        fileMenu.add(newMenu);
        newMenu.addActionListener(new NewListener());
        fileMenu.add(open);
        open.addActionListener(new OpenListener());
        fileMenu.add(save);
        save.addActionListener(new SaveListener());
        fileMenu.add(saveAs);
        saveAs.addActionListener(new SaveAsListener());
        fileMenu.add(quit);
        quit.addActionListener(new EndingListener());
        //Compile
        compileMenu.add(comp);      
        comp.addActionListener(new CompiledListener());
        compileMenu.add(compRun);
        compRun.addActionListener(new CompiledRunListener());
        //Config
        configMenu.add(jCompiler);
        jCompiler.addActionListener(new JCompListener());
        configMenu.add(compOpt);
        compOpt.addActionListener(new CompOptions());
        configMenu.add(jRunTime);
        jRunTime.addActionListener(new JavaRunTime());
        configMenu.add(runTimeOpt);
        runTimeOpt.addActionListener(new RunTimeOptions());
        configMenu.add(workDir);
        configMenu.add(compMode);
        compMode.addActionListener(new ModeListener());
        workDir.addActionListener(new WorkDirListener());
        //Help
        helpMenu.add(helpMen);
        helpMen.addActionListener(new HelpListener());
        helpMenu.add(about);      
        about.addActionListener(new AboutListener());
        
        jTextArea1.getDocument().addDocumentListener(new DocListener());

        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        southPanel = new javax.swing.JPanel();
        currentProjLab = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        modLabel = new javax.swing.JLabel();
        namePanel = new javax.swing.JPanel();
        mainNameLabel = new javax.swing.JLabel();
        textPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        buttonPanel = new javax.swing.JPanel();
        openButton = new javax.swing.JButton();
        openButton.addActionListener(new OpenListener());
        openButton.setIcon(openIcon);
        newButton = new javax.swing.JButton();
        newButton.addActionListener(new NewListener());
        newButton.setIcon(newIcon);
        saveAsButton = new javax.swing.JButton();
        saveAsButton.addActionListener(new SaveAsListener());
        saveAsButton.setIcon(saveAsIcon);
        stopButton = new javax.swing.JButton();
        stopButton.setIcon(stopIcon);
        compButton = new javax.swing.JButton();
        compButton.addActionListener(new CompiledListener());
        compButton.setIcon(compIcon); //play
        saveButton = new javax.swing.JButton();
        saveButton.addActionListener(new SaveListener());
        saveButton.setIcon(saveIcon);
        doneButton = new javax.swing.JButton();
        doneButton.addActionListener(new EndingListener());
        doneButton.setIcon(doneIcon); //done
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        compileMenu = new javax.swing.JMenu();
        configMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        currentProjLab.setText("Current Project:");
        
        jLabel3.setText("-");

        modLabel.setText("[ ]");

        javax.swing.GroupLayout southPanelLayout = new javax.swing.GroupLayout(southPanel);
        southPanel.setLayout(southPanelLayout);
        southPanelLayout.setHorizontalGroup(
            southPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(southPanelLayout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(currentProjLab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        southPanelLayout.setVerticalGroup(
            southPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, southPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(currentProjLab)
                .addComponent(jLabel3)
                .addComponent(modLabel))
        );

        mainNameLabel.setText("-");

        javax.swing.GroupLayout namePanelLayout = new javax.swing.GroupLayout(namePanel);
        namePanel.setLayout(namePanelLayout);
        namePanelLayout.setHorizontalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(namePanelLayout.createSequentialGroup()
                .addComponent(mainNameLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        namePanelLayout.setVerticalGroup(
            namePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, namePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainNameLabel))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
        );

       

        

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(saveAsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(compButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveAsButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doneButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setToolTipText("");

        fileMenu.setText("File");
        jMenuBar1.add(fileMenu);

        compileMenu.setText("Compile");
        jMenuBar1.add(compileMenu);

        configMenu.setText("Config");
        jMenuBar1.add(configMenu);

        helpMenu.setText("Help");
        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(southPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(namePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(textPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(southPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
         
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // static String[] fieldStr;
    // static String[] buttonStr;
    // static String dialogTitle;
    // static String dialogFields;
    // static String dialogButtons;
    public static String[] fieldStr;
    private String[] buttonStr;
    public static String dialogTitle;
    // private String dialogFields;
    public static String dialogFields;
    public static String dialogButtons;
    public static String title;
    private String javaCompiler = "javac";
    private String cOptDef = "export LD_LIBRARY_PATH=./";
    private String jRunDef = "/usr/lib/jvm";
    private String runTDef = "No options chosen yet.";
    private String pathStr = new File(".").getAbsolutePath();
    final JLabel current = new JLabel(pathStr);
    private String currWkDir = pathStr;
    private String conFilePath = ".";
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JMenu compileMenu;
    private javax.swing.JMenu configMenu;
    private javax.swing.JLabel currentProjLab;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel mainNameLabel;
    private javax.swing.JLabel modLabel;
    private javax.swing.JPanel namePanel;
    private javax.swing.JButton newButton;


    //RADIO BUTTON
    private javax.swing.JPanel radioPanel = new JPanel(new GridLayout(1, 0));
    private javax.swing.JRadioButton lexYacc = new JRadioButton("Lex/Yacc");
    private javax.swing.JRadioButton ide = new JRadioButton("IDE");
    private javax.swing.JOptionPane damn = new JOptionPane();

    
    private javax.swing.JButton saveAsButton;
    private javax.swing.JLabel jCompLabel;
    
    private javax.swing.JPanel southPanel;
    private javax.swing.JPanel textPanel;
    // End of variables declaration//GEN-END:variables

    private javax.swing.JMenu file = new JMenu("File");
    private javax.swing.JMenu compile = new JMenu("Compile");
    private javax.swing.JMenu config = new JMenu("Config");
    private javax.swing.JMenu help = new JMenu("Help");
    //File Menu Tab
    private javax.swing.JMenuItem newMenu = new JMenuItem("New");
    private javax.swing.JMenuItem open = new JMenuItem("Open");
    private javax.swing.ImageIcon newIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/new.png");   
    private javax.swing.ImageIcon openIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/open.png");   
    private javax.swing.ImageIcon saveIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/saver.png");
    private javax.swing.ImageIcon saveAsIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/saveAs.png");
    private javax.swing.ImageIcon stopIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/stop.png");
    private javax.swing.ImageIcon compIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/compile.png");
    private javax.swing.ImageIcon doneIcon = new ImageIcon("/home/brandon/NetBeansProjects/2750A2/src/Images/done.png");
    private javax.swing.JButton openButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton doneButton;
    private javax.swing.JButton compButton;
    private javax.swing.JMenuItem save = new JMenuItem("Save");
    private javax.swing.JMenuItem saveAs = new JMenuItem("Save As");
    private javax.swing.JMenuItem quit = new JMenuItem("Quit");
    //Compile Menu Tab
    private javax.swing.JMenuItem comp = new JMenuItem("Compile");
    private javax.swing.JMenuItem compRun = new JMenuItem("Compile and run");
    //Config Menu Tab
    private javax.swing.JMenuItem jCompiler = new JMenuItem("Java Compiler");
    private javax.swing.JMenuItem compOpt = new JMenuItem("Compile options");
    private javax.swing.JMenuItem jRunTime = new JMenuItem("Java Run-time");
    private javax.swing.JMenuItem runTimeOpt = new JMenuItem("Run-time options");
    private javax.swing.JMenuItem workDir = new JMenuItem("Working Directory");
    private javax.swing.JMenuItem compMode = new JMenuItem("Compile Mode");
    //Help Menu Tab
    private javax.swing.JMenuItem helpMen = new JMenuItem("Help");
    private javax.swing.JMenuItem about = new JMenuItem("About");
    private javax.swing.JLabel currentSet = new JLabel("Current Setting: ");
    
    private static mainFrame instance = null;
    private String myline = "";
}
