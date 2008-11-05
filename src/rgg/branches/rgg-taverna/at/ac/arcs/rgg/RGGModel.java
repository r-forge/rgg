/*
 * RGGModel.java
 *
 * Created on 02. Oktober 2006, 12:57
 */
package at.ac.arcs.rgg;

import java.io.IOException;
import java.util.ArrayList;
import at.ac.arcs.rgg.element.RElement;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ilhami
 */
public class RGGModel {

    private ArrayList<RElement> elementSequence = new ArrayList<RElement>();

    /** Creates a new instance of RGGModel */
    public RGGModel() {
    }

    public void add(RElement rggelement) {
        elementSequence.add(rggelement);
    }
    //TODO find a proper name!!!
    public ArrayList<RElement> getElementSequence() {
        return elementSequence;
    }

    public void setElementSequence(ArrayList<RElement> elementSequence) {
        this.elementSequence = elementSequence;
    }

    public String generateRScript() {
        StringBuilder rbuilder = new StringBuilder();
        for (RElement elem : elementSequence) {
            rbuilder.append(elem.getRCode());
        }

        BufferedReader reader = new BufferedReader(new StringReader(rbuilder.toString()));

        rbuilder = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                line = StringUtils.strip(line);
                if (StringUtils.isNotBlank(line)) {
                    rbuilder.append(line);
                    rbuilder.append("\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RGGModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rbuilder.toString();
    }
}
