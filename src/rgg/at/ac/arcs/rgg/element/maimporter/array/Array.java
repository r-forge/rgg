
package at.ac.arcs.rgg.element.maimporter.array;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilhami
 */
public class Array {
    
    private File file;
    private String source;
    private ArrayInfo arrayInfo;
        
    private InputInfo G = new InputInfo("G", InputInfo.OptionType.ONE_TO_ONE);         
    private InputInfo Gb = new InputInfo("Gb", InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo R = new InputInfo("R", InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo Rb = new InputInfo("Rb", InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo annotations = new InputInfo("Annotation", InputInfo.OptionType.MANY_TO_ONE);
    private ArrayList<String> allHeaders = new ArrayList<String>();
    

    public Array(String source){
        this.source = source;
    }
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputInfo getG() {
        return G;
    }

    public void setGHeaderIndex(int index) {        
        this.G.setColumns(index);
    }

    public InputInfo getGb() {
        return Gb;
    }

    public void setGb(int index) {
        this.Gb.setColumns(index);
    }

    public InputInfo getR() {
        return R;
    }

    public void setR(int index) {
        this.R.setColumns(index);
    }

    public InputInfo getRb() {
        return Rb;
    }

    public void setRb(int index) {
        this.Rb.setColumns(index);
    }
    
    public InputInfo getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ArrayList<Integer> columns) {
        this.annotations.setColumns(columns);
    }
    
    public void addAnnotations(Integer column){
        annotations.addColumn(column);
    }
    
    public void removeAnnotations(Integer column){
        annotations.removeColumn(column);
    }

    public List<String> getHeaders() {
        return allHeaders;
    }

    public void setHeaders(ArrayList<String> allHeaders) {
        this.allHeaders = allHeaders;
    }

    public ArrayInfo getArrayInfo() {
        return arrayInfo;
    }

    public void setArrayInfo(ArrayInfo arrayInfo) {
        this.arrayInfo = arrayInfo;
    }

    public List<List<String>> readAssayData(int rownumber) throws IOException{
        return arrayInfo.readAssayData(rownumber);
    }
}
