/*
 * RLabel.java
 *
 * Created on 16. November 2006, 20:20
 */
package at.ac.arcs.rgg.element.label;

import javax.swing.JComponent;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import java.util.ArrayList;

/**
 *
 * @author ilhami
 */
public class RLabel extends RElement {

    private static final String bindingpoint = "text";
    
    private VisualComponent[][] visualcomponents;
    private VLabel vlabel;

    /** Creates a new instance of RLabel */
    public RLabel(VLabel vlabel) {
        this.vlabel = vlabel;
        visualcomponents = new VisualComponent[][]{{vlabel}};
    }

    public String getRCode() {
        return "";
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vlabel.getSwingComponents();
    }

    @Override
    public void addInputPort(String portName, String bindTo) {
        if(bindTo.equalsIgnoreCase(bindingpoint)){
            InputPort iport = new InputPort(portName, bindingpoint,-1) {

                @Override
                public void setValue(Object obj) throws IllegalArgumentException, PortValueSetOperationException {
                    vlabel.setText(obj.toString());
                }
            };
            
            inputPorts = new ArrayList<InputPort>();
            inputPorts.add(iport);
        }
    }
}
