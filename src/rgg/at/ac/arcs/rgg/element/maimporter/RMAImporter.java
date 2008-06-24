package at.ac.arcs.rgg.element.maimporter;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.maimporter.ui.MAImporterPanel;

/**
 *
 * @author ilhami
 */
public class RMAImporter extends RElement {

    private String var;
    private VMAImporter vMAImporter;
    private VisualComponent[][] visualcomponents;
    private String targetfilevar;

    public RMAImporter() {
    }

    public String getRCode() {
        //Target file creation
        MAImporterPanel mapanel = vMAImporter.getMAImporterPanel();
        StringBuffer rbuf = new StringBuffer();
        if (StringUtils.isNotBlank(targetfilevar)) {
            rbuf.append(targetfilevar);
            rbuf.append(" = ");
        }else{
            rbuf.append("_matargetfile = ");
        }
        rbuf.append(mapanel.getMAModel().getTargetFile().toRCode());
        rbuf.append("\n");

        if (StringUtils.isNotBlank(var)) {
            rbuf.append(var + "=");
        }

        rbuf.append("read.maimages(files=");
        if (StringUtils.isNotBlank(targetfilevar)) {            
            rbuf.append(targetfilevar);            
        }else{
            rbuf.append("_matargetfile");
        }
        
        rbuf.append("$"+mapanel.getMAModel().getTargetFile().getFileNameHeader()+", source=\"");
        rbuf.append(mapanel.getArraySource()+"\",");
        rbuf.append(" path=\""+StringUtils.replace(mapanel.getMAModel().getTargetFile().getPath().getAbsolutePath(), "\\", "/")+"\"");
        rbuf.append(", columns = list(");
        rbuf.append("G=\"" + mapanel.getGHeader() + "\"");
        rbuf.append(", Gb=\"" + mapanel.getGbHeader() + "\"");
        rbuf.append(", R=\"" + mapanel.getRHeader() + "\"");
        rbuf.append(", Rb=\"" + mapanel.getRbHeader() + "\"");
        rbuf.append(")");

        if (mapanel.getAnnotationHeaders().size() > 0) {
            rbuf.append(", annotation=list(");
            for (String header : mapanel.getAnnotationHeaders()) {
                rbuf.append("\"" + header + "\",");
            }
            rbuf.deleteCharAt(rbuf.length() - 1);
            rbuf.append(")");
        }
        
        if (mapanel.getOtherColumnHeaders().size() > 0) {
            rbuf.append(", other.columns=list(");
            for (String header : mapanel.getOtherColumnHeaders()) {
                rbuf.append("\"" + header + "\",");
            }
            rbuf.deleteCharAt(rbuf.length() - 1);
            rbuf.append(")");
        }
        
        rbuf.append(")");

        return rbuf.toString();
    }

    public void setVMAImporter(VMAImporter vMAImporter) {
        this.vMAImporter = vMAImporter;
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vMAImporter}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vMAImporter.getSwingComponents();
    }

    void setTargetfilevar(String targetfilevar) {
        this.targetfilevar = targetfilevar;
    }
}
