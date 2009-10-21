/*
 * RCheckBox.java
 *
 * Created on 19. Oktober 2006, 08:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.checkbox;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RCheckBox extends RElement {

    private String var;
    private String label;
    private String returnValueBySelected = "TRUE";
    private String returnValueByNotSelected = "FALSE";
    private VCheckBox vcheckbox;
    private VisualComponent[][] visualcomponents;

    /** Creates a new instance of RCheckBox */
    public RCheckBox() {
    }

    public String getRCode() {
        String value;
        if (vcheckbox.isSelected() && vcheckbox.isEnabled()) {
            value = returnValueBySelected;
        } else {
            value = returnValueByNotSelected;
        }

        if (StringUtils.isBlank(var)) {
            return value;
        } else {
            return var + "<-" + value;
        }
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vcheckbox}};
        }
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
        if (StringUtils.isBlank(label)) {
            label = var;
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public VCheckBox getCheckBox() {
        return vcheckbox;
    }

    public void setCheckBox(VCheckBox vcheckbox) {
        this.vcheckbox = vcheckbox;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vcheckbox.getSwingComponents();
    }

    public String getReturnValueBySelected() {
        return returnValueBySelected;
    }

    public void setReturnValueBySelected(String returnValueBySelected) {
        this.returnValueBySelected = returnValueBySelected;
    }

    public String getReturnValueByNotSelected() {
        return returnValueByNotSelected;
    }

    public void setReturnValueByNotSelected(String returnValuebyNotSelected) {
        this.returnValueByNotSelected = returnValuebyNotSelected;
    }

}
