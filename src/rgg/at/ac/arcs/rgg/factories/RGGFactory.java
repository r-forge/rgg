package at.ac.arcs.rgg.factories;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.RGGModel;
import at.ac.arcs.rgg.element.rcode.RGGRCodeFactory;
import org.w3c.dom.*;

public class RGGFactory {

    public RGGFactory() {
    }

    public static RGGModel createRGGModel(Element rgg, RGG rggInstance)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        RGGModel model = new RGGModel();
        for (int i = 0; i < rgg.getChildNodes().getLength(); i++) {
            if (rgg.getChildNodes().item(i).getNodeType() == Element.ELEMENT_NODE) {
                Element elem = (Element) rgg.getChildNodes().item(i);
                model.add(RElementFactory.getElementFactoryForName(elem.getNodeName()).createRGGElement(elem, rggInstance));
                continue;
            }
            if (rgg.getChildNodes().item(i).getNodeType() == Element.TEXT_NODE) {
                model.add(RGGRCodeFactory.createRCode((Text) rgg.getChildNodes().item(i)));
            }
        }

        return model;
    }
}