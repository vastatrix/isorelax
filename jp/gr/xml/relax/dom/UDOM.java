package jp.gr.xml.relax.dom;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.MalformedURLException;
import org.w3c.dom.*;

/**
 * UDOM
 *
 * @since   Jan. 29, 2000
 * @version Feb. 24, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public final class UDOM {
    // text generation
    public static String getXMLText(Document doc) {
	return (getXMLText(doc, "UTF-8"));
    }

    public static String getXMLText(Document doc, String encoding) {
	XMLMaker maker = new XMLMaker();
	UDOMVisitor.traverse(doc, maker);
	return (maker.getText());
    }

    public static String getXMLText(Node node) {
	XMLMaker maker = new XMLMaker();
	UDOMVisitor.traverse(node, maker);
	return (maker.getText());
    }	
}
