/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.text.CloneableEditor;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.NbDocument;
import org.openide.util.Lookup;
import org.openide.util.Mutex;
import org.openide.windows.TopComponent;

/**
 *
 * @author ahmet
 */
public class RGGSourceEditor extends CloneableEditor
        implements MultiViewElement, Runnable {

    private JComponent toolbar;
    private MultiViewElementCallback callback;
    private RGGEditorSupport support;


    RGGSourceEditor(RGGEditorSupport ed) {
        super(ed);
         this.support=ed;
        EditorKit kit= CloneableEditorSupport.getEditorKit("text/x-xml");
        if(kit!=null && pane!=null)
            this.pane.setEditorKit(kit);
    }

    public JComponent getVisualRepresentation() {
        return this;
    }
// Return the editor's custom toolbar,
// so our toggle button could integrate
// with it:
    public JComponent getToolbarRepresentation() {
        if (toolbar == null) {
            JEditorPane pane = this.pane;
            if (pane != null) {
                Document doc = pane.getDocument();
                if (doc instanceof NbDocument.CustomToolbar) {
                    toolbar = ((NbDocument.CustomToolbar) doc).createToolbar(pane);
                }
            }
            if (toolbar == null) {
// attempt to create own toolbar?
                toolbar = new JPanel();
            }
        }
        return toolbar;
    }

    public void setMultiViewCallback(
            MultiViewElementCallback callback) {
        this.callback = callback;
        updateName();
    }

    public void componentOpened() {
        super.componentOpened();
    }

    public void componentClosed() {
        super.componentClosed();
    }

    public void componentShowing() {
        super.componentShowing();
    }

    public void componentHidden() {
        super.componentHidden();
    }

    public void componentActivated() {
        super.componentActivated();
    }

    public void componentDeactivated() {
        super.componentDeactivated();
    }

    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    public void updateName() {
        Mutex.EVENT.readAccess(this);
    }

    public void run() {
        MultiViewElementCallback c = callback;
        if (c == null) {
            return;
        }
        TopComponent tc = c.getTopComponent();
        if (tc == null) {
            return;
        }
//        super.updateName();
        tc.setName(support.getEditingRGG().getRGGName());
        tc.setDisplayName(support.getEditingRGG().getRGGName());
        tc.setHtmlDisplayName(this.getHtmlDisplayName());
    }

    public Lookup getLookup() {
        return ((RGGDataObject) ((RGGEditorSupport) cloneableEditorSupport()).getDataObject()).getNodeDelegate().getLookup();
    }
}