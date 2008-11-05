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
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author ilhami
 */
public class RFileChooser extends RElement {

    private static final String bindingpoint = "file";
    private String var;
    private String label;
    private String description;
    private String[] extensions;
    private VisualComponent[][] visualcomponents;
    private VFileChooser vFilechooser;

    /** Creates a new instance of RFileChooser */
    /** Creates a new instance of RFileChooser */
    public RFileChooser() {
    }

    public String getRCode() {
        StringBuilder rcodebuilder = new StringBuilder();
        if (StringUtils.isNotBlank(var)) {
            rcodebuilder.append(var + "<-");
        }
        
        
        if (vFilechooser.isFilesSelected()) {
            if (vFilechooser.isMultiSelectionEnabled()) {
                rcodebuilder.append("c(");
                File[] files = vFilechooser.getSelectedFiles();
                for (int i = 0; i < files.length; i++) {
                    rcodebuilder.append("\"" + files[i].getPath() + "\"");
                    if (i != (files.length - 1)) {
                        rcodebuilder.append(",");
                    }
                }
                rcodebuilder.append(")");
            } else {
                rcodebuilder.append("\"" + vFilechooser.getFilePath() + "\"");
            }
        }else{
            rcodebuilder.append("NA");
        }
        return StringUtils.replace(rcodebuilder.toString(), "\\", "/");
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vFilechooser}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public VFileChooser getFileChooser() {
        return vFilechooser;
    }

    public void setFileChooser(VFileChooser filechooser) {
        this.vFilechooser = filechooser;
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
        return vFilechooser;
    }

    public void setVFileChooser(VFileChooser filechooser) {
        this.vFilechooser = filechooser;
    }

    public String getVariable() {
        return var;
    }

    public void setVariable(String var) {
        this.var = var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vFilechooser.getSwingComponents();
    }

    @Override
    public void addInputPort(String portName, String bindTo) {
        if (bindTo.equalsIgnoreCase(bindingpoint)) {
            InputPort iport = new InputPort(portName, bindingpoint,-1) {

                @Override
                public void setValue(Object obj) throws IllegalArgumentException, PortValueSetOperationException {
                    if (obj instanceof File) {
                        setFile((File) obj);
                    } else if (obj instanceof File[]) {
                        setFiles((File[]) obj);
                    } else if (obj instanceof String) {
                        File file = new File(obj.toString());
                        if (file.exists()) {
                            setFile(file);
                        } else {
                            throw new IllegalArgumentException(obj.toString() + "is not found!");
                        }
                    } else if (obj instanceof String[]) {
                        String[] paths = (String[]) obj;
                        File[] files = new File[paths.length];
                        for (int i = 0; i < paths.length; i++) {
                            files[i] = new File(paths[i]);
                            if (files[i].exists()) {
                                throw new IllegalArgumentException(obj.toString() + "is not found!");
                            }
                        }
                        setFiles(files);
                    }
                }

                private void setFile(File file) {
                    if (vFilechooser.getFileSelectionMode() == JFileChooser.FILES_ONLY) {
                        if (file.isFile()) {
                            vFilechooser.getFileChooser().setSelectedFile(file);
                        } else {
                            throw new IllegalArgumentException("Only a file accepted, not a directory");
                        }
                    } else if (vFilechooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY) {
                        if (file.isDirectory()) {
                            vFilechooser.getFileChooser().setSelectedFile(file);
                        } else {
                            throw new IllegalArgumentException("Only a directory accepted, not a file");
                        }
                    } else {
                        vFilechooser.getFileChooser().setSelectedFile(file);
                    }
                }

                private void setFiles(File[] files) {
                    if (vFilechooser.getFileSelectionMode() == JFileChooser.FILES_ONLY) {
                        boolean flag = true;
                        for (File f : files) {
                            flag &= f.isFile();
                        }
                        if (flag) {
                            vFilechooser.getFileChooser().setSelectedFiles(files);
                        } else {
                            throw new IllegalArgumentException("Only a file accepted, not a directory");
                        }
                    } else if (vFilechooser.getFileSelectionMode() == JFileChooser.DIRECTORIES_ONLY) {
                        boolean flag = true;
                        for (File f : files) {
                            flag &= f.isDirectory();
                        }
                        if (flag) {
                            vFilechooser.getFileChooser().setSelectedFiles(files);
                        } else {
                            throw new IllegalArgumentException("Only a directory accepted, not a file");
                        }
                    } else {
                        vFilechooser.getFileChooser().setSelectedFiles(files);
                    }
                }
            };

            inputPorts = new ArrayList<InputPort>();
            inputPorts.add(iport);
        }
    }
}
