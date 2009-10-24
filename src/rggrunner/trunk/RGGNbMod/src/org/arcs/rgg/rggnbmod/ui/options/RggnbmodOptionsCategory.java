/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.arcs.rgg.rggnbmod.ui.options;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.netbeans.spi.options.OptionsCategory;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

public final class RggnbmodOptionsCategory extends OptionsCategory {

    public Icon getIcon() {
        return new ImageIcon(Utilities.loadImage("org/arcs/rgg/rggnbmod/ui/options/rgg_32.png"));
    }

    public String getCategoryName() {
        return NbBundle.getMessage(RggnbmodOptionsCategory.class, "OptionsCategory_Name_Rggnbmod");
    }

    public String getTitle() {
        return NbBundle.getMessage(RggnbmodOptionsCategory.class, "OptionsCategory_Title_Rggnbmod");
    }

    public OptionsPanelController create() {
        return new RggnbmodOptionsPanelController();
    }
}
