/*
 * RFileChooser.java
 *
 * Created on 23. Oktober 2006, 23:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.filechooser;

import java.io.File;
import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RFileChooser extends RElement{
    private String var;
    private String label;
    private String description;
    private String[] extensions;
    
    private VisualComponent[][] visualcomponents;
    private VFileChooser filechooser;
    /** Creates a new instance of RFileChooser */
        /** Creates a new instance of RFileChooser */
    public RFileChooser() {
    }

    public String getRCode() {
        StringBuffer sbuf = new StringBuffer();
        if (StringUtils.isNotBlank(var)) {
            sbuf.append(var + "<-");
        }
        if (filechooser.isFilesSelected()) {
            if (filechooser.isMultiSelectionEnabled()) {
                sbuf.append("c(");
                File[] files = filechooser.getSelectedFiles();
                for (int i = 0; i < files.length; i++) {
                    sbuf.append("\"" + files[i].getPath() + "\"");
                    if (i != (files.length - 1)) {
                        sbuf.append(",");
                    }
                }
                sbuf.append(")");
            } else {
                sbuf.append("\"" + filechooser.getFilePath() + "\"");
            }
        }
        return StringUtils.replace(sbuf.toString(), "\\", "/");
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{filechooser}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public VFileChooser getFileChooser() {
        return filechooser;
    }

    public void setFileChooser(VFileChooser filechooser) {
        this.filechooser = filechooser;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VFileChooser getVFileChooser() {
        return filechooser;
    }

    public void setVFileChooser(VFileChooser filechooser) {
        this.filechooser = filechooser;
    }

    public String getVariable() {
        return var;
    }

    public void setVariable(String var) {
        this.var = var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return filechooser.getSwingComponents();
    }
}
