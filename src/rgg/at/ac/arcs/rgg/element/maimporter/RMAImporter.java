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

    public RMAImporter() {
    }

    public String getRCode() {
        //Target file creation
        MAImporterPanel mapanel = vMAImporter.getMAImporterPanel();
        StringBuffer rbuf = new StringBuffer();
        rbuf.append("matargetfile = ");
//        rbuf.append(mapanel.getTargetFile().toRCode());
        rbuf.append("\n");

        if (StringUtils.isNotBlank(var)) {
            rbuf.append(var + "=");
        }

//        rbuf.append("read.maimages(files=matargetfile$FileName, " +
//                "source=\"" + mapanel.getArraySource()+"\"," +
//                " path=\""+mapanel.getTargetFile().getPath().getAbsolutePath());
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
}
