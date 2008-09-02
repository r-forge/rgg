/*
 * RGGModel.java
 *
 * Created on 02. Oktober 2006, 12:57
 */

package at.ac.arcs.rgg;

import java.util.ArrayList;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RGGModel {
    private ArrayList<RElement> elementSequence = new ArrayList<RElement>();
    /** Creates a new instance of RGGModel */
    public RGGModel() {
    }
    
    public void add(RElement rggelement){
        elementSequence.add(rggelement);
    }
    
    //TODO find a proper name!!!
    public ArrayList<RElement> getElementSequence(){
        return elementSequence;
    }
    
    public String generateRScript(){
        StringBuffer rbuf = new StringBuffer();
        for(RElement elem:elementSequence){
            rbuf.append(elem.getRCode());
        }
        return rbuf.toString();
    }
}
