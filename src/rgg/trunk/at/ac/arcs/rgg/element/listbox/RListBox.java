/*
 * RList.java
 *
 * Created on 12. November 2006, 01:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.listbox;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RListBox extends RElement{
    
    private String var;
    private String label;
    private boolean numeric = false;
    private VListBox vList;
    private VisualComponent[][] visualcomponents;
    /**
     * Creates a new instance of RList
     */
    public RListBox() {
    }
    
    public String getRCode(){
        StringBuffer sbuf = new StringBuffer();
        if(StringUtils.isNotBlank(var))
            sbuf.append(var+"<-");
        Object[] selectedValues = vList.getSelectedValues();
        if(isNumeric())
            for(Object obj:selectedValues)
                sbuf.append(obj.toString()+",");
        else
            for(Object obj:selectedValues)
                sbuf.append("\""+obj.toString()+"\",");
            return sbuf.toString();
    }
    
    public boolean hasVisualComponents() {
        return true;
    }
    
    public VisualComponent[][] getVisualComponents() {
        if(visualcomponents == null)
            setVisualcomponents(new VisualComponent[][]{{vList}});
        return visualcomponents;
    }
    
    public boolean isChildAddable() {
        return false;
    }
    
    public String getVar() {
        return var;
    }
    
    public void setVar(String var) {
        this.var = var;
        if(StringUtils.isBlank(getLabel()))
            setLabel(var);
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setLabel(String label) {
        this.label = label;
        vList.setLabelText(label);
    }
    
    public VListBox getVComboBox() {
        return vList;
    }
    
    public void setVList(VListBox vList) {
        this.vList = vList;
    }
    
    
    void setColumnSpan(int colspan) {
        vList.setColumnSpan(colspan);
    }
    
    public void setVisualcomponents(VisualComponent[][] visualcomponents) {
        this.visualcomponents = visualcomponents;
    }
    
    public boolean isNumeric() {
        return numeric;
    }
    
    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }
    
    public JComponent[][] getSwingComponentMatrix() {
        return vList.getSwingComponents();
    }

}
