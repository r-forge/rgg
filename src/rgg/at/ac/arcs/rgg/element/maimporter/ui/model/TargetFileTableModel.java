/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.model;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import at.ac.arcs.rgg.element.maimporter.array.TargetFile;

/**
 *
 * @author ilhami
 */
public class TargetFileTableModel extends DefaultTableModel {
    private TargetFile targetFile;
    
    public TargetFileTableModel(TargetFile targetFile){
        this.targetFile = targetFile;
        columnIdentifiers.addAll(targetFile.getHeader());
        for(ArrayList<String> dataLine:targetFile.getTargetFileData()){
            dataVector.add(new Vector(dataLine));
        }
    }
}
