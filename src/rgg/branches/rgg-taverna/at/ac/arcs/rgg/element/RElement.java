package at.ac.arcs.rgg.element;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import at.ac.arcs.rgg.component.VisualComponent;

public abstract class RElement {

    protected ArrayList<RElement> childElements;
    protected ArrayList<InputPort> inputPorts;
    protected PropertyChangeSupport changeSupport;

    public RElement() {
        childElements = new ArrayList<RElement>();
        changeSupport = new PropertyChangeSupport(this);
    }

    public abstract String getRCode();

    public abstract boolean hasVisualComponents();

    public abstract VisualComponent[][] getVisualComponents();

    public abstract JComponent[][] getSwingComponentMatrix();

    public abstract boolean isChildAddable();
    
    public  ArrayList<InputPort> getInputPorts(){
        return inputPorts;
    }
    
    public abstract void addInputPort(String portName,String bindTo);

    public void addChild(RElement elem) {
        if (!isChildAddable()) {
            throw new UnsupportedOperationException("This element doesn't accept any child elements.");
        } else {
            childElements.add(elem);
            return;
        }
    }

    public RElement getChild(int i) {
        return childElements.get(i);
    }

    public List<RElement> getChilds() {
        return childElements;
    }

    public boolean hasChild() {
        return childElements.size() > 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        } else {
            changeSupport.addPropertyChangeListener(listener);
            return;
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        } else {
            changeSupport.removePropertyChangeListener(listener);
            return;
        }
    }
    
    public abstract class InputPort{
        private String portName;
        private String bindTo;

        public InputPort(String portName,String bindTo){
            this.portName=portName;
            this.bindTo=bindTo;
        }
        
        public String getBindTo() {
            return bindTo;
        }

        public void setBindTo(String bindTo) {
            this.bindTo = bindTo;
        }

        public String getPortName() {
            return portName;
        }

        public void setPortName(String portName) {
            this.portName = portName;
        }
        
        public abstract void setValue(Object obj)
                throws IllegalArgumentException, PortValueSetOperationException;
    }
    
    public class PortValueSetOperationException extends Exception{
        public PortValueSetOperationException(String msg){
            super(msg);
        }
    }
}