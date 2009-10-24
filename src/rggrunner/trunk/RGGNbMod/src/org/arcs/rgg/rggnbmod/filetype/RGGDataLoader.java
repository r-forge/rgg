/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.filetype;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

public class RGGDataLoader extends UniFileLoader {

    public static final String REQUIRED_MIME = "text/x-rgg+xml";
    private static final long serialVersionUID = 1L;

    public RGGDataLoader() {
        super("org.arcs.rgg.rggnbmod.filetype.RGGDataObject");
    }

    @Override
    protected String defaultDisplayName() {
        return NbBundle.getMessage(RGGDataLoader.class, "LBL_RGG_loader_name");
    }

    @Override
    protected void initialize() {
        super.initialize();
        getExtensions().addMimeType(REQUIRED_MIME);
    }

    protected MultiDataObject createMultiObject(FileObject primaryFile) throws DataObjectExistsException, IOException {
        return new RGGDataObject(primaryFile, this);
    }
   

    @Override
    protected String actionsContext() {
        return "Loaders/" + REQUIRED_MIME + "/Actions";
    }
}
