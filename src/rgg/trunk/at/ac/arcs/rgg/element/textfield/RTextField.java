/*
 * VTextField.java
 *
 * Created on 09. Oktober 2006, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.textfield;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class RTextField extends RElement {

    private String label;
    private String var;
    private VTextField textfield;
    private VisualComponent[][] visualcomponents;

    /**
     * Creates a new instance of VTextField
     */
    public RTextField() {
    }

    public String getRCode() {
        StringBuffer sbuf = new StringBuffer();

        if (StringUtils.isNotBlank(var)) {
            sbuf.append(var);
            sbuf.append("<-");
            return var + "<-" + textfield.getTextFieldValue();
        }
        if (textfield.isNumeric()) {
            sbuf.append(textfield.getTextFieldValue());
        } else {
            sbuf.append("\"");
            sbuf.append(textfield.getTextFieldValue());
            sbuf.append("\"");
        }
        return sbuf.toString();
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{textfield}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public VTextField getTextfield() {
        return textfield;
    }

    public void setTextfield(VTextField textfield) {
        this.textfield = textfield;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return textfield.getSwingComponents();
    }
}
