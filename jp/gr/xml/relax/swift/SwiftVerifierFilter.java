package jp.gr.xml.relax.swift;

import org.iso_relax.verifier.VerifierFilter;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;
import jp.co.swiftinc.relax.schema.Grammar;


/**
 * SwiftVerifierFilter
 *
 * @since   Feb. 23, 2001
 * @version Feb. 24, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftVerifierFilter extends XMLFilterImpl
    implements VerifierFilter {

    private SwiftVerifierHandler handler_;

    public SwiftVerifierFilter(Grammar grammar) {
	handler_ = new SwiftVerifierHandler(grammar);
    }

    public boolean isValid() throws IllegalStateException {
	return (handler_.isValid());
    }

    public void setDocumentLocator(Locator locator) {
	handler_.setDocumentLocator(locator);
	super.setDocumentLocator(locator);
    }

    public void startDocument() throws SAXException {
	handler_.startDocument();
	super.startDocument();
    }

    public void endDocument() throws SAXException {
	handler_.endDocument();
	super.endDocument();
    }

    public void startPrefixMapping(
	String prefix,
	String uri
    ) throws SAXException {
	handler_.startPrefixMapping(prefix, uri);
	super.startPrefixMapping(prefix, uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
	handler_.endPrefixMapping(prefix);
	super.endPrefixMapping(prefix);
    }

    public void startElement(
	String namespaceURI,
	String localName,
	String qName,
	Attributes atts
    ) throws SAXException {
	handler_.startElement(namespaceURI, localName, qName, atts);
	super.startElement(namespaceURI, localName, qName, atts);
    }

    public void endElement(
	String namespaceURI,
	String localName,
	String qName
    ) throws SAXException {
	handler_.endElement(namespaceURI, localName, qName);
	super.endElement(namespaceURI, localName, qName);
    }

    public void characters(
	char ch[],
	int start,
	int length
    ) throws SAXException {
	handler_.characters(ch, start, length);
	super.characters(ch, start, length);
    }

    public void ignorableWhitespace(
	char ch[],
	int start,
	int length
    ) throws SAXException {
	handler_.ignorableWhitespace(ch, start, length);
	super.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
	throws SAXException {

	handler_.processingInstruction(target, data);
	super.processingInstruction(target, data);
    }

    public void skippedEntity(String name) throws SAXException {
	handler_.skippedEntity(name);
	super.skippedEntity(name);
    }
}
