/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import at.ac.arcs.rgg.RGG;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewFactory;
import org.openide.cookies.EditCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.cookies.SaveCookie;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.text.CloneableEditorSupport;
import org.openide.text.DataEditorSupport;
import org.openide.util.Exceptions;
import org.openide.util.Utilities;
import org.xml.sax.SAXException;

/**
 *
 * @author ahmet
 */
public class RGGEditorSupport extends DataEditorSupport implements OpenCookie, EditorCookie, EditCookie {

    RGGVisualView visual = new RGGVisualView(this);
    final MultiViewDescription[] descriptions = {
        visual,
        new RGGTextView(this),
        new RGGScriptView(this),
    };

    @Override
    protected CloneableEditorSupport.Pane createPane() {
        return (CloneableEditorSupport.Pane) MultiViewFactory.createCloneableMultiView(descriptions, descriptions[0]);
    }

    public static RGGEditorSupport create(RGGDataObject aThis) {
        return new RGGEditorSupport(aThis);
    }

    public RGGEditorSupport(RGGDataObject arg0) {
        super(arg0, new RGGEnv(arg0));
        visual.setRGG(createRGG());
        arg0.getPrimaryFile().addFileChangeListener(new FileChangeAdapter() {

            @Override
            public void fileChanged(FileEvent arg0) {
                visual.setRGG(createRGG());
            }
        });
    }
    
    private RGG createRGG() {
        try {

            if (Utilities.isWindows()) {
                return RGG.newInstance(new File(getDataObject().getPrimaryFile().getPath()));
            } else {
                if (getDataObject().getPrimaryFile().getPath().startsWith("/")) {
                    return RGG.newInstance(new File(getDataObject().getPrimaryFile().getPath()));
                } else {
                    return RGG.newInstance(new File("/" + getDataObject().getPrimaryFile().getPath()));
                }
            }
        } catch (ParserConfigurationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (SAXException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (ClassNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (InstantiationException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public RGG getEditingRGG() {
        return visual.getRGG();
    }

    protected boolean notifyModified() {
        boolean retValue;
        retValue = super.notifyModified();
        if (retValue) {
            RGGDataObject obj =
                    (RGGDataObject) getDataObject();
            obj.ic.add(env);
        }

        return retValue;
    }

    protected void notifyUnmodified() {
        super.notifyUnmodified();
        RGGDataObject obj =
                (RGGDataObject) getDataObject();
        obj.ic.remove(env);
    }

    private static final class RGGEnv extends DataEditorSupport.Env implements SaveCookie {

        public RGGEnv(RGGDataObject obj) {
            super(obj);
        }

        public void save() throws IOException {
            RGGEditorSupport ed = (RGGEditorSupport) this.findCloneableOpenSupport();
            ed.saveDocument();
        }

        protected FileObject getFile() {
            return super.getDataObject().getPrimaryFile();
        }

        protected FileLock takeLock() throws IOException {
            return ((RGGDataObject) super.getDataObject()).getPrimaryEntry().takeLock();
        }
    }
}
