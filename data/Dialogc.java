/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brandon
 */
public class Dialogc {
    public static native String parseConfig(String configTitle, String parName, int pType, int parseMode);


    static{
         System.loadLibrary("JavaPMManager");
    }
       
    public static void main(String[] args) {
        // TODO code application logic here
        

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainFrame().setVisible(true);
            }
        });
                            
    }
    
}
