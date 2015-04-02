/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author brandon
 */
public class GenerateGui {
    
    public void generateGui(String title, String fields, String buttons) throws IOException{
        mainFrame framer = new mainFrame();
        String titles = framer.dialogTitle;
        
        File fp = new File(titles + ".java"); //working directory + name will be changed to .config file title
        fp.createNewFile();
        
        FileWriter fw = new FileWriter(fp);
        String pubClass = "public class " + titles + " extends JFrame{       ";
        String construct = "    public " + titles +"(){";
        String setVis = "                new " + titles + "().setVisible(true);                    \n"; 
        String[] fieldStrT = framer.dialogFields.split(",");  
        String[] buttonStrT = framer.dialogButtons.split(",");

        fw.write(generateOne);
        fw.write(pubClass);
        fw.write(setUp);
        for(int j=0; j<fieldStrT.length; j++){  
            fw.write("    private String " +fieldStrT[j]+ ";\n");
        }
        fw.write("\n");

        for(int j=0; j<buttonStrT.length; j++){  
            fw.write("    private String " +buttonStrT[j]+ ";\n");
        }
        fw.write("\n");

        fw.write(construct);        

        fw.write(generateTwo);
        fw.write("\n        labels = new String[]{"); 
        // System.out.println("lol" + framer.dialogFields); 
        
        for(int j=0; j<fieldStrT.length - 1; j++){  
            fw.write("\"" +fieldStrT[j]+ "\",");
        }
        fw.write("\"" + fieldStrT[fieldStrT.length - 1] + "\"};");  //  access the last field here
        
        fw.write(generateThr);

        fw.write("\n        buttNames = new String[]{");     
        for(int i=0; i<buttonStrT.length - 1; i++){
            fw.write("\"" +buttonStrT[i]+ "\",");
        }
        fw.write("\"" + buttonStrT[buttonStrT.length - 1] + "\"};");  //  access the last field here
        fw.write(generateFour);

        for(int k=0; k<fieldStrT.length; k++){
            fw.write("            setDC" +fieldStrT[k]+"(\""+fieldStrT[k]+"\");\n");
        }
        fw.write("            statusTxtArea.append(");
        for(int k=0; k<fieldStrT.length; k++){
            fw.write("getDC" +fieldStrT[k] + "() + " + "\"=\" + " + "holder[" + k + "] + " + "\"\\n\"" + "+");
        }                                                                          
        fw.write("\"\\n\"" + ");\n");


        fw.write(buttonEnd);
        for(int j=0; j<fieldStrT.length; j++){  // These should be buttStr.
            fw.write("    public String getDC" +fieldStrT[j]+ "(){\n");
            fw.write("        return " +fieldStrT[j]+ ";\n");
            fw.write("    }\n");
        }
        for(int j=0; j<fieldStrT.length; j++){  // These should be buttStr.
            fw.write("    public void setDC" +fieldStrT[j]+ "(String " + fieldStrT[j]+"){\n");
            fw.write("        this." +fieldStrT[j]+ " = " + fieldStrT[j] + ";\n");
            fw.write("    }\n");
        }

        fw.write(generateFive);
        fw.write(setVis);
        fw.write(end);
        fw.flush();
        fw.close();
                
    }
    // mainFrame framer = new mainFrame();
    // String titles = framer.dialogTitle;    
    private String btnString = "String[] buttNames = {\"Add\", \"Delete\", \"Update\", \"Query\"}; //getDCButtons() to get button names";
    
    //Something like that except we will utilize our getters and setts to get the names 
    private String fieldString = "//get String[] labels = {};\n" +
"    //for(i=0; i<labels.length(); i++){\n" +
"        //write(\"\\\"+labels[i]+\"\\\", \"); "
            + "}";
    
    private String generateOne = "import java.awt.BorderLayout;\n" +
    "import java.awt.Color;\n" +
    "import java.awt.FlowLayout;\n" +
    "import java.awt.GridLayout;\n" +
    "import java.awt.event.ActionEvent;\n" +
    "import java.awt.event.ActionListener;\n" +
    "import javax.swing.BoxLayout;\n" +
    "import javax.swing.JButton;\n" +
    "import javax.swing.JFrame;\n" +
    "import javax.swing.JLabel;\n" +
    "import javax.swing.JPanel;\n" +
    "import javax.swing.JScrollPane;\n" +
    "import javax.swing.JTextArea;\n" +
    "import javax.swing.JTextField;\n" +
    "import javax.swing.SpringLayout;\n" +
    "import javax.swing.*;\n" +
    "import java.awt.*;\n" +
    "import java.util.ArrayList;\n" +
    "\n" +
    "/**\n" +
    " *\n" +
    " * @author brandon\n" +
    " */\n";
    //private String class = "public class " + titles + " extends JFrame{       \n";

    private String setUp = "    \n" +
    "    public static final int WIDTH = 430;\n" +
    "    public static final int HEIGHT = 550;\n" +
    "    //Fields pane and components (hard coded for example)\n" +
    "    private javax.swing.JPanel fieldsPanel = new JPanel();\n" +
    "    private javax.swing.JPanel labelsPanel = new JPanel(new SpringLayout());\n" +
    "    private FlowLayout flow = new FlowLayout();\n" +
    "    //private javax.swing.JPanel buttonsPanel = new JPanel(new FlowLayout(5, 5, 60));    \n" +
    "    private javax.swing.JPanel buttonsPanel = new JPanel(flow);\n" +
    "    private javax.swing.JButton btn;\n" +
    "    private String titleGet;  \n" +
    "    private String fieldsGet;  \n" +
    "    private String buttonsGet;  \n" +
    "    private javax.swing.JTextField fields = new JTextField(); \n" +
    "    private String[] labels;   \n" +
    "    private int numPairs;     \n" + 
    "    private String[] buttNames;    \n" +
    "    private int length;   \n"  +
    "    private ArrayList<JTextField> fInfo = new ArrayList<>();   \n" +
    "    private String[] holder;   \n" +
    "    \n" +
    "    \n" +
    "    //Status pane and components\n" +
    "    private javax.swing.JPanel mainPanel = new JPanel();    \n" +
    "    private javax.swing.JPanel textPanel = new JPanel();    \n" +
    "    private javax.swing.JTextArea statusTxtArea = new JTextArea();    \n" +
    "    private javax.swing.JLabel statusLabel = new JLabel(\"                                           STATUS\");\n" +
    "    private javax.swing.JScrollPane scroller;\n" +
    "    \n";



    private String generateTwo = "\n        setSize(WIDTH, HEIGHT);\n" +
    "        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);\n" +
    "        //mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));\n" +
    "        mainPanel.setLayout(new GridLayout(2,1));\n" +
    "        //mainPanel.add(Box.createRigidArea(new Dimension(10, 100)));\n" +
    "        mainPanel.setBackground(Color.red);\n" +
    "        \n" +
    "        add(mainPanel);        \n" +
    "        \n" +
    "        initFieldsPane();\n" +
    "        initLabelsPanel();\n" +
    "        initButtonsPanel();\n" +
    "        initStatusPane();\n" +
    "    }\n" +
    "    /**\n" +
    "     * This method will initialize and handle the labels and fields of the application\n" +
    "     */\n" +
    "    private void initFieldsPane(){\n" +
    "        //labelsPanel.setBackground(Color.GRAY);\n" +
    "        //buttonsPanel.setBackground(Color.ORANGE);\n" +
    "        //fieldsPanel.setBackground(Color.blue);\n" +
    "        mainPanel.add(fieldsPanel);                \n" +
    "        \n" +
    "        fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.Y_AXIS));\n" +
    "        fieldsPanel.add(labelsPanel);\n" +
    "        fieldsPanel.add(buttonsPanel);\n" +
    "    }\n" +
    "    \n" +
    "    private void initLabelsPanel(){        ";
    //get String[] labels = {};
    //for(i=0; i<labels.length(); i++){
        //write("\"+labels[i]+"\", "); }
    private String generateThr = 
    "\n        numPairs = labels.length;\n" +
    "        \n" +
    "        for(int i=0; i<numPairs; i++){\n" +
    "            javax.swing.JLabel ls = new JLabel(labels[i], JLabel.TRAILING);\n" +
    "            labelsPanel.add(ls);\n" +
    "            fields = new JTextField();\n" +
    "            fInfo.add(fields);     \n" +
    "            ls.setLabelFor(fields);\n" +
    "            labelsPanel.add(fields);\n" +
    "        }\n" +
    "        \n" +
    "        makeCompactGrid(labelsPanel, numPairs, 2, 7, 7, 7, 7);\n" +
    "    }\n" +
    "    \n" +
    "    private void initButtonsPanel(){";
    //get String[] buttNames = {}; 
    //for(i=0; i<buttNames.length(); i++){
        //write("\"+buttNames[i]+"\", "); }
    
                                        //NOTE: THIS IS WHERE YOU WILL MAKE THE FOR LOOP 
    private String generateFour = "\n        " +  "\n "+"\n        length = buttNames.length;\n" +
    "        \n" +
    "        for(int i=0; i<length; i++){\n" +
    "           btn = new JButton(buttNames[i]);\n" +
    "           btn.addActionListener(new ButtonPress());\n" +
    "           buttonsPanel.add(btn);\n" +
    "        }\n" +
    "    }\n" +
    "    \n" +
    "    private class ButtonPress implements ActionListener{\n" +
    "        public void actionPerformed(ActionEvent e) {\n" +
    "            //for every button that is pressed...\n" +
    "            //JTextArea1.append(getDCButton() + \"button pushed\"); \n" +
    "            holder = new String[fInfo.size()]; \n" +
    "            for(int i=0; i<numPairs; i++){ \n" +
    "               holder[i] = fInfo.get(i).getText();    \n" +
    "               System.out.println(holder[i]);     \n" +
    "            }  \n" + 
    "            \n";

    private String buttonEnd = "            //statusTxtArea.append(\"Button 1 pushed\" + \"\\n\");\n" +
    "        }\n" +
    "        \n" +
    "    }\n" +
    

    "    /**\n" +
    "     * This method will initialize and status text area of the application\n" +
    "     */\n";


    private String generateFive = "    private void initStatusPane() {        \n" +
    "        //textPanel.setBackground(Color.green);        \n" +
    "        mainPanel.add(textPanel);\n" +
    "                     \n" +
    "        statusTxtArea.setEditable(true);               \n" +
    "        \n" +
    "        scroller = new JScrollPane(statusTxtArea);\n" +
    "        textPanel.setLayout(new BorderLayout());\n" +
    "        textPanel.add(statusLabel, BorderLayout.NORTH);        \n" +
    "        textPanel.add(scroller, BorderLayout.CENTER);                        \n" +
    "    }\n" +
    "\n" +
    "    /**\n" +
    "         * A debugging utility that prints to stdout the component's\n" +
    "         * minimum, preferred, and maximum sizes.\n" +
    "         */\n" +
    "    public static void printSizes(Component c) {\n" +
    "        System.out.println(\"minimumSize = \" + c.getMinimumSize());\n" +
    "        System.out.println(\"preferredSize = \" + c.getPreferredSize());\n" +
    "        System.out.println(\"maximumSize = \" + c.getMaximumSize());\n" +
    "    }\n" +
    "\n" +
    "    /**\n" +
    "     * Aligns the first <code>rows</code> * <code>cols</code>\n" +
    "     * components of <code>parent</code> in\n" +
    "     * a grid. Each component is as big as the maximum\n" +
    "     * preferred width and height of the components.\n" +
    "     * The parent is made just big enough to fit them all.\n" +
    "     *\n" +
    "     * @param rows number of rows\n" +
    "     * @param cols number of columns\n" +
    "     * @param initialX x location to start the grid at\n" +
    "     * @param initialY y location to start the grid at\n" +
    "     * @param xPad x padding between cells\n" +
    "     * @param yPad y padding between cells\n" +
    "     */\n" +
    "    public static void makeGrid(Container parent,\n" +
    "                                int rows, int cols,\n" +
    "                                int initialX, int initialY,\n" +
    "                                int xPad, int yPad) {\n" +
    "        SpringLayout layout;\n" +
    "        try {\n" +
    "            layout = (SpringLayout)parent.getLayout();\n" +
    "        } catch (ClassCastException exc) {\n" +
    "            System.err.println(\"The first argument to makeGrid must use SpringLayout.\");\n" +
    "            return;\n" +
    "        }\n" +
    "\n" +
    "        Spring xPadSpring = Spring.constant(xPad);\n" +
    "        Spring yPadSpring = Spring.constant(yPad);\n" +
    "        Spring initialXSpring = Spring.constant(initialX);\n" +
    "        Spring initialYSpring = Spring.constant(initialY);\n" +
    "        int max = rows * cols;\n" +
    "\n" +
    "        //Calculate Springs that are the max of the width/height so that all\n" +
    "        //cells have the same size.\n" +
    "        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).\n" +
    "                                    getWidth();\n" +
    "        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).\n" +
    "                                    getHeight();\n" +
    "        for (int i = 1; i < max; i++) {\n" +
    "            SpringLayout.Constraints cons = layout.getConstraints(\n" +
    "                                            parent.getComponent(i));\n" +
    "\n" +
    "            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());\n" +
    "            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());\n" +
    "        }\n" +
    "\n" +
    "        //Apply the new width/height Spring. This forces all the\n" +
    "        //components to have the same size.\n" +
    "        for (int i = 0; i < max; i++) {\n" +
    "            SpringLayout.Constraints cons = layout.getConstraints(\n" +
    "                                            parent.getComponent(i));\n" +
    "\n" +
    "            cons.setWidth(maxWidthSpring);\n" +
    "            cons.setHeight(maxHeightSpring);\n" +
    "        }\n" +
    "\n" +
    "        //Then adjust the x/y constraints of all the cells so that they\n" +
    "        //are aligned in a grid.\n" +
    "        SpringLayout.Constraints lastCons = null;\n" +
    "        SpringLayout.Constraints lastRowCons = null;\n" +
    "        for (int i = 0; i < max; i++) {\n" +
    "            SpringLayout.Constraints cons = layout.getConstraints(\n" +
    "                                                 parent.getComponent(i));\n" +
    "            if (i % cols == 0) { //start of new row\n" +
    "                lastRowCons = lastCons;\n" +
    "                cons.setX(initialXSpring);\n" +
    "            } else { //x position depends on previous component\n" +
    "                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),\n" +
    "                                     xPadSpring));\n" +
    "            }\n" +
    "\n" +
    "            if (i / cols == 0) { //first row\n" +
    "                cons.setY(initialYSpring);\n" +
    "            } else { //y position depends on previous row\n" +
    "                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),\n" +
    "                                     yPadSpring));\n" +
    "            }\n" +
    "            lastCons = cons;\n" +
    "        }\n" +
    "\n" +
    "        //Set the parent's size.\n" +
    "        SpringLayout.Constraints pCons = layout.getConstraints(parent);\n" +
    "        pCons.setConstraint(SpringLayout.SOUTH,\n" +
    "                            Spring.sum(\n" +
    "                                Spring.constant(yPad),\n" +
    "                                lastCons.getConstraint(SpringLayout.SOUTH)));\n" +
    "        pCons.setConstraint(SpringLayout.EAST,\n" +
    "                            Spring.sum(\n" +
    "                                Spring.constant(xPad),\n" +
    "                                lastCons.getConstraint(SpringLayout.EAST)));\n" +
    "    }\n" +
    "\n" +
    "    /* Used by makeCompactGrid. */\n" +
    "    private static SpringLayout.Constraints getConstraintsForCell(\n" +
    "                                                int row, int col,\n" +
    "                                                Container parent,\n" +
    "                                                int cols) {\n" +
    "        SpringLayout layout = (SpringLayout) parent.getLayout();\n" +
    "        Component c = parent.getComponent(row * cols + col);\n" +
    "        return layout.getConstraints(c);\n" +
    "    }\n" +
    "\n" +
    "    /**\n" +
    "     * Aligns the first <code>rows</code> * <code>cols</code>\n" +
    "     * components of <code>parent</code> in\n" +
    "     * a grid. Each component in a column is as wide as the maximum\n" +
    "     * preferred width of the components in that column;\n" +
    "     * height is similarly determined for each row.\n" +
    "     * The parent is made just big enough to fit them all.\n" +
    "     *\n" +
    "     * @param rows number of rows\n" +
    "     * @param cols number of columns\n" +
    "     * @param initialX x location to start the grid at\n" +
    "     * @param initialY y location to start the grid at\n" +
    "     * @param xPad x padding between cells\n" +
    "     * @param yPad y padding between cells\n" +
    "     */\n" +
    "    public static void makeCompactGrid(Container parent,\n" +
    "                                       int rows, int cols,\n" +
    "                                       int initialX, int initialY,\n" +
    "                                       int xPad, int yPad) {\n" +
    "        SpringLayout layout;\n" +
    "        try {\n" +
    "            layout = (SpringLayout)parent.getLayout();\n" +
    "        } catch (ClassCastException exc) {\n" +
    "            System.err.println(\"The first argument to makeCompactGrid must use SpringLayout.\");\n" +
    "            return;\n" +
    "        }\n" +
    "\n" +
    "        //Align all cells in each column and make them the same width.\n" +
    "        Spring x = Spring.constant(initialX);\n" +
    "        for (int c = 0; c < cols; c++) {\n" +
    "            Spring width = Spring.constant(0);\n" +
    "            for (int r = 0; r < rows; r++) {\n" +
    "                width = Spring.max(width,\n" +
    "                                   getConstraintsForCell(r, c, parent, cols).\n" +
    "                                       getWidth());\n" +
    "            }\n" +
    "            for (int r = 0; r < rows; r++) {\n" +
    "                SpringLayout.Constraints constraints =\n" +
    "                        getConstraintsForCell(r, c, parent, cols);\n" +
    "                constraints.setX(x);\n" +
    "                constraints.setWidth(width);\n" +
    "            }\n" +
    "            x = Spring.sum(x, Spring.sum(width, Spring.constant(xPad)));\n" +
    "        }\n" +
    "\n" +
    "        //Align all cells in each row and make them the same height.\n" +
    "        Spring y = Spring.constant(initialY);\n" +
    "        for (int r = 0; r < rows; r++) {\n" +
    "            Spring height = Spring.constant(0);\n" +
    "            for (int c = 0; c < cols; c++) {\n" +
    "                height = Spring.max(height,\n" +
    "                                    getConstraintsForCell(r, c, parent, cols).\n" +
    "                                        getHeight());\n" +
    "            }\n" +
    "            for (int c = 0; c < cols; c++) {\n" +
    "                SpringLayout.Constraints constraints =\n" +
    "                        getConstraintsForCell(r, c, parent, cols);\n" +
    "                constraints.setY(y);\n" +
    "                constraints.setHeight(height);\n" +
    "            }\n" +
    "            y = Spring.sum(y, Spring.sum(height, Spring.constant(yPad)));\n" +
    "        }\n" +
    "\n" +
    "        //Set the parent's size.\n" +
    "        SpringLayout.Constraints pCons = layout.getConstraints(parent);\n" +
    "        pCons.setConstraint(SpringLayout.SOUTH, y);\n" +
    "        pCons.setConstraint(SpringLayout.EAST, x);\n" +
    "    }\n" +
    "\n" +
    "\n" +
    "\n" +
    "\n" +
    "\n" +
    "    \n" +
    "    public static void main(String[] args){\n" +
    "        //myGui obj = new myGui();\n" +
    "        //obj.setVisible(true);\n" +
    "        java.awt.EventQueue.invokeLater(new Runnable() {\n" +
    "            public void run() {                \n";
    //private String setVis = "                new myGui().setVisible(true)";                    \n"; 

    private String end = "            }\n" +
    "        });\n" +
    "    }        \n" +
    "\n" +
    "    /**\n" +
    "     * A 1.4 file that provides utility methods for\n" +
    "     * creating form- or grid-style layouts with SpringLayout.\n" +
    "     * These utilities are used by several programs, such as\n" +
    "     * SpringBox and SpringCompactGrid.\n" +
    "     */\n" +
    "}";
}