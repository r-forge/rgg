package at.ac.arcs.rgg.element.maimporter;

import javax.swing.JComponent;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.maimporter.ui.archive.MAImporterPanelI;
import at.ac.arcs.rgg.element.maimporter.ui.MAImporterPanel;
import at.ac.arcs.rgg.layout.LayoutInfo;

/**
 *
 * @author ilhami
 */
public class VMAImporter extends VisualComponent {

    private MAImporterPanel mapanel;
    private RGG rggInstance;
    private JComponent[][] swingComponents;

    /**
     * Creates a new instance of VTextField
     */
    public VMAImporter(RGG rggInstance) {
        this.rggInstance = rggInstance;
        initializeComponents();
    }

    private void initializeComponents() {
        mapanel = new MAImporterPanel();
    }

    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            swingComponents = new JComponent[][]{{mapanel}};
        }
        return swingComponents;
    }

    public MAImporterPanel getMAImporterPanel(){
        return mapanel;
    }
    
    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(mapanel, colspan);
        }
    }
}
