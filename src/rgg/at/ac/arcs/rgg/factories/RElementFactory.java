package at.ac.arcs.rgg.factories;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import org.w3c.dom.Element;

public abstract class RElementFactory
{

    public RElementFactory()
    {
    }

    public abstract RElement createRGGElement(Element element, RGG rgg);

    public static RElementFactory getElementFactoryForName(String classname)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        if(StringUtils.isBlank(classname))
            throw new IllegalArgumentException("Class name cannot be null or empty");
        Class factoryClass = Class.forName(RGG.getConfiguration().getString(classname));
        if(!class_factory.containsKey(factoryClass))
            class_factory.put(factoryClass, (RElementFactory)factoryClass.newInstance());
        return (RElementFactory)class_factory.get(factoryClass);
    }

    public static HashMap class_factory = new HashMap();

}