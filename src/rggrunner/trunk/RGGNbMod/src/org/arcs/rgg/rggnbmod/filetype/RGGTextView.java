/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import java.awt.EventQueue;
import java.awt.Image;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.openide.util.HelpCtx;
import org.openide.windows.TopComponent;

/**
 *
 * @author ahmet
 */
public class RGGTextView implements
        MultiViewDescription {

    private RGGSourceEditor editor;
    private RGGEditorSupport support;

    public RGGTextView(RGGEditorSupport editorSupport) {
        this.support = editorSupport;
    }

    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ONLY_OPENED;
    }

    public String getDisplayName() {
        return "Source";
    }
// We are not showing an icon:
    public Image getIcon() {
        return null;
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    public String preferredID() {
        return "source";
    }
// Here we call a method returning
// an editor, which we will create in
// the next section:
    public MultiViewElement createElement() {
        return getEd();
    }

    private RGGSourceEditor getEd() {
        assert EventQueue.isDispatchThread();
        if (editor == null) {
            editor = new RGGSourceEditor(support);
        }
        return editor;
    }
}