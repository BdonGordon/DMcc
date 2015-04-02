/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author brandon
 */
public class HelpListeners {
     public static String noExt(String str){
        if (str == null){
            return null;
        }
        int post = str.lastIndexOf(".");
        
        if(post == -1){
            return str;
        }
        
        return str.substring(0,post);
    }
    
    public static String newDir(String str){
        if(str == null){
            return null;
        }
        int post = str.lastIndexOf("/");
        
        if(post == -1){
            return str;
        }
        
        return str.substring(0, post);
    }
}
