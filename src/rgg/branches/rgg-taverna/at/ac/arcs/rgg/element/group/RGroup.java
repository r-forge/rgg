/*
 * RGroup.java
 *
 * Created on 18. Oktober 2006, 10:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.group;

import javax.swing.JComponent;
import at.ac.arcs.rgg.RGGModel;
import at.ac.arcs.rgg.component.EmptyPlaceHolder;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RGroup extends RElement{
    private static EmptyPlaceHolder eph = new EmptyPlaceHolder();
    private VisualComponent[][] visualComponentMatrix;
    private VGroup vgroup;
    private RGGModel rggmodel;
    /** Creates a new instance of RGroup */
    public RGroup(VGroup vgroup,RGGModel rggmodel) {
        this.vgroup = vgroup;
        this.rggmodel = rggmodel;
        visualComponentMatrix = new VisualComponent[][]{{vgroup}};
    }
    
    public String getRCode() {
        return rggmodel.generateRScript();
    }
    
    public boolean hasVisualComponents() {
        return true;
    }
    
    public VisualComponent[][] getVisualComponents(){
        return visualComponentMatrix;
    }
    
    public boolean isChildAddable() {
        return true;
    }

    public JComponent[][] getSwingComponentMatrix() {
       return vgroup.getSwingComponents();
    }
}
