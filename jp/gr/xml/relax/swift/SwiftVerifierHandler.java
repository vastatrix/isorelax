package jp.gr.xml.relax.swift;

import jp.co.swiftinc.relax.schema.Grammar;
import jp.co.swiftinc.relax.verifier.NotValidException;
import jp.co.swiftinc.relax.verifier.RELAXErrorHandler;
import jp.co.swiftinc.relax.verifier.RELAXNormalHandler;

import org.iso_relax.verifier.VerifierHandler;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * SwiftVerifierHandler
 *
 * @since   Feb. 23, 2001
 * @version Feb. 24, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftVerifierHandler implements VerifierHandler {
    private Boolean isValid_ = null;
    private boolean isActive_ = true;
    private RELAXNormalHandler handler_;

    public SwiftVerifierHandler(Grammar grammar,RELAXErrorHandler errorHandler) {
	handler_ = new RELAXNormalHandler(grammar);
	handler_.setErrorHandler(errorHandler);
    }

    public boolean isValid() throws IllegalStateException {
	if (isValid_ == null) {
	    throw (new IllegalStateException());
	}
	return (isValid_.booleanValue());
    }

    public void setDocumentLocator(Locator locator) {
	handler_.setDocumentLocator(locator);
    }

    public void startDocument() {
	handler_.startDocument();
    }

    public void endDocument() throws SAXException {
	if (!isActive_) {
	    isValid_ = Boolean.FALSE;
	    return;
	}
	try {
	    handler_.endDocument();
	    isValid_ = Boolean.TRUE;
	} catch (NotValidException e) {
	    isActive_ = false;
	    isValid_ = Boolean.FALSE;
	}
    }

    public void startPrefixMapping(String prefix, String uri) {
	handler_.startPrefixMapping(prefix, uri);
    }

    public void endPrefixMapping(String prefix) {
	handler_.endPrefixMapping(prefix);
    }

    public void startElement(
	String namespaceURI,
	String localName,
	String qName,
	Attributes atts
    ) throws SAXException {
	if (!isActive_) {
	    return;
	}
	try {
	    handler_.startElement(
		namespaceURI,
		localName,
		qName,
		atts
	    );
	} catch (NotValidException e) {
	    isActive_ = false;
	}
    }

    public void endElement(
	String namespaceURI,
	String localName,
	String qName
    ) throws SAXException {
	if (!isActive_) {
	    return;
	}
	try {
	    handler_.endElement(
		namespaceURI,
		localName,
		qName
	    );
	} catch (NotValidException e) {
	    isActive_ = false;
	}
    }

    public void characters(char ch[], int start, int length) {
	if (!isActive_) {
	    return;
	}
	handler_.characters(ch, start, length);
    }

    public void ignorableWhitespace(char ch[], int start, int length) {
	if (!isActive_) {
	    return;
	}
	handler_.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
	throws SAXException {

	if (!isActive_) {
	    return;
	}
	try {
	    handler_.processingInstruction(target, data);
	} catch (NotValidException e) {
	    isActive_ = false;
	}
    }

    public void skippedEntity(String name) throws SAXException {
	if (!isActive_) {
	    return;
	}
	handler_.skippedEntity(name);
    }
}
