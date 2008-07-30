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
    private String targetfilevar = "targetfile";
    private String othercolumnsvar = "other.columns";
    private String columnsvar = "columns";
    private String annotationvar = "annotation";
    private String pathvar = "path";
    private String sourcevar = "source";

    public RMAImporter() {
    }

    @Override
    public String getRCode() {
        //Target file creation
        MAImporterPanel mapanel = vMAImporter.getMAImporterPanel();
        StringBuilder rbuilder = new StringBuilder();
        if (StringUtils.isNotBlank(targetfilevar)) {
            rbuilder.append(targetfilevar);
            rbuilder.append(" = ");
        } else {
            rbuilder.append("_matargetfile = ");
        }
        rbuilder.append(mapanel.getMAModel().getTargetFile().toRCode());
        rbuilder.append("\n");

        rbuilder.append(pathvar + " = \"" +
                StringUtils.replace(mapanel.getMAModel().getTargetFile().getPath().getAbsolutePath(), "\\", "/") + "\"\n");
        rbuilder.append(sourcevar + " = \"" + mapanel.getArraySource() + "\"\n");

        if (!vMAImporter.isAffymetrix()) {
            rbuilder.append(columnsvar + " = list(");
            rbuilder.append("G=\"" + mapanel.getGHeader() + "\"");
            rbuilder.append(", Gb=\"" + mapanel.getGbHeader() + "\"");
            rbuilder.append(", R=\"" + mapanel.getRHeader() + "\"");
            rbuilder.append(", Rb=\"" + mapanel.getRbHeader() + "\"");
            rbuilder.append(")\n");

            if (mapanel.getAnnotationHeaders().size() > 0) {
                rbuilder.append(annotationvar + " = list(");
                for (String header : mapanel.getAnnotationHeaders()) {
                    rbuilder.append("\"" + header + "\",");
                }
                rbuilder.deleteCharAt(rbuilder.length() - 1);
                rbuilder.append(")\n");
            }else{
                rbuilder.append(annotationvar +" = character(0)\n");
            }

            if (mapanel.getOtherColumnHeaders().size() > 0) {
                rbuilder.append(othercolumnsvar + " = list(");
                for(int i=0;i<mapanel.getOtherColumnHeaders().size();i++){
                    rbuilder.append(vMAImporter.othercolumns[i]+"=\"" + mapanel.getOtherColumnHeaders().get(i) + "\",");
                }
//                for (String header : mapanel.getOtherColumnHeaders()) {
//                    rbuilder.append("\"" + header + "\",");
//                }
                rbuilder.deleteCharAt(rbuilder.length() - 1);
                rbuilder.append(")");
            }else{
                rbuilder.append(othercolumnsvar +" = character(0)\n");
            }
        }

        return rbuilder.toString();
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

    public String getAnnotationvar() {
        return annotationvar;
    }

    public void setAnnotationvar(String annotationvar) {
        this.annotationvar = annotationvar;
    }

    public String getSourcevar() {
        return sourcevar;
    }

    public void setSourcevar(String sourcevar) {
        this.sourcevar = sourcevar;
    }

    public String getColumnsvar() {
        return columnsvar;
    }

    public void setColumnsvar(String columnsvar) {
        this.columnsvar = columnsvar;
    }

    public String getOthercolumnsvar() {
        return othercolumnsvar;
    }

    public void setOthercolumnsvar(String othercolumnsvar) {
        this.othercolumnsvar = othercolumnsvar;
    }

    public String getPathvar() {
        return pathvar;
    }

    public void setPathvar(String pathvar) {
        this.pathvar = pathvar;
    }
}
