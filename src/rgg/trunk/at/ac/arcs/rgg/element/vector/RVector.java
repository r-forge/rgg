/*
 * RVector.java
 *
 * Created on 17.04.2007, 10:55:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.vector;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RVector extends RElement{
    private String label;
    private String var;
    private boolean numeric = false;
    
    private VisualComponent[][] visualcomponents;
    
    private VVector vvector;
    
    public RVector(VVector vvector) {
        this.vvector = vvector;
    }
    
    public String getRCode() {
        StringBuffer sbuf = new StringBuffer("c(");
        if(vvector.getVectortype() == VectorType.NUMERIC){
            for(JComponent textfield:vvector.getVectorlist()){
                sbuf.append(((JFormattedTextField)textfield).getText()+",");
            }
        }else if(vvector.getVectortype() == VectorType.CHARACTER){
            for(JComponent textfield:vvector.getVectorlist()){
                sbuf.append("\""+((JFormattedTextField)textfield).getText()+"\",");
            }
        }else{
           for(JComponent component:vvector.getVectorlist()){
                sbuf.append(new Boolean(((JCheckBox)component).isSelected()).toString().toUpperCase()+",");
            } 
        }
        sbuf.append(")");
        return sbuf.toString();
    }
   
    public boolean hasVisualComponents() {
        return true;
    }
    
    public VisualComponent[][] getVisualComponents() {
        if(visualcomponents == null)
            visualcomponents = new VisualComponent[][]{{vvector}};
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
        vvector.setLabelText(label);
    }
    
    public JComponent[][] getSwingComponentMatrix() {
        return vvector.getSwingComponents();
    }
}
