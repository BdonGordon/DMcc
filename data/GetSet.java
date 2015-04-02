/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.TextArea;

/**
 *
 * @author brandon
 */
public class GetSet {
    private String title;
    private String fields;
    private String buttons;
    private float number;
    
    public GetSet(){
        this.title = null;
        this.fields = null;
        this.buttons = null;
        this.number = 0;
    }
    /**
     * Accessor for the title
     * @return 
     */
    public String getDCTitle(){
        return title;
    }
    /**
     * Setter method for the title
     * @param title 
     */
    public void setDCTitle(String title){
        this.title = title;
    }
    /**
     * Setter method for the buttons
     * @param buttons 
     */
    public void setDCButtons(String buttons){
        this.buttons = buttons;
    }
    /**
     * Setter method for the fields
     * @param fields 
     */
    public void setDCFields(String fields){
        this.fields = fields;
    }
    /**
     * Setter method for the number
     * @param number 
     */
    public void setDCNumber(float number){
        this.number = number;
    }
    
    /**
     * Accessor for the buttons
     * @return 
     */
    public String getDCButtons(){
        return buttons;
    }
    /**
     * Accessor for the fields
     * @return 
     */
    public String getDCFields(){
        return fields;
    }
    /**
     * Accessor for the number
     * @return 
     */
    public float getDCNumber(){
        return number;
    }
    
}
