package org.iso_relax.verifier;

import java.io.IOException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.w3c.dom.Node;

/**
 * Verifier
 *
 * @since   Feb. 23, 2001
 * @version Mar.  4, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public interface Verifier {
    String FEATURE_HANDLER = "http://www.iso-relax.org/verifier/handler";
    String FEATURE_FILTER = "http://www.iso-relax.org/verifier/filter";

    /**
     * Indicates whether if the feature is supported, or not.
     *
     * @param feature feature name
     */
    boolean isFeature(String feature)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Sets feature value
     *
     * @param feature feature name
     * @param value feature value
     */
    void setFeature(String feature, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Gets property value
     *
     * @param property property name
     */
    Object getProperty(String property)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Sets property value
     *
     * @param property property name
     * @param value property value
     */
    void setProperty(String property, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Sets a ErrorHandler to capture verification information.
     *
     * @param handler ErrorHandler
     */
    void setErrorHandler(ErrorHandler handler);

    /**
     * Sets a EntityResolver to resolve external entity locations.
     *
     * @param handler EntityResolver
     */
    void setEntityResolver(EntityResolver handler);

    /**
     * Verifies against a XML document specified by a URI.
     *
     * @param uri URI of a XML document to verify.
     */
    boolean verify(String uri) throws SAXException, IOException;

    /**
     * Verifies against a XML document specified by a InputSource.
     *
     * @param source InputSource of a XML document to verify.
     */
    boolean verify(InputSource source) throws SAXException, IOException;

    /**
     * Verifies against a XML document represented by DOM nodes.
     *
     * @param node root DOM node of a XML document to verify.
     */
    boolean verify(Node node) throws SAXException;

    /**
     * Gets a VerifierHandler.
     * VerifierHandler is org.xml.sax.ContentHandler
     * to verify against SAX events.
     */
    VerifierHandler getVerifierHandler() throws SAXException;

    /**
     * Gets a VerifierFilter.
     * VerifierFilter is org.xml.sax.XMLFilter
     * to verify against SAX events.
     */
    VerifierFilter getVerifierFilter() throws SAXException;
}
