package jp.gr.xml.relax.swift;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.XMLReader;
import org.xml.sax.ErrorHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.w3c.dom.Node;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierHandler;
import org.iso_relax.verifier.VerifierFilter;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.VerifierException;
import jp.gr.xml.relax.dom.DOMVisitorException;
import jp.gr.xml.relax.sax.DOMSAXProducer;
import jp.co.swiftinc.relax.verifier.RELAXNormalHandler;
import jp.co.swiftinc.relax.verifier.NotValidException;
import jp.co.swiftinc.relax.schema.Grammar;
import jp.co.swiftinc.relax.schema.SchemaLoader;
import jp.co.swiftinc.relax.schema.SchemaSyntaxErrorException;

/**
 * SwiftVerifier
 *
 * @since   Feb. 23, 2001
 * @version Mar.  4, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftVerifier implements Verifier {
    private Grammar grammar_;
    private XMLReader reader_;

    public SwiftVerifier(String uri)
	throws VerifierConfigurationException, SAXException,
	       IOException {

	try {
	    _init(SchemaLoader.load(uri));
	} catch (SchemaSyntaxErrorException e) {
	    throw (new VerifierException(e));
	} catch (ClassNotFoundException e) {
	    throw (new VerifierException(e));
	} catch (IllegalAccessException e) {
	    throw (new VerifierException(e));
	} catch (InstantiationException e) {
	    throw (new VerifierException(e));
	} catch (SAXException e) {
	    throw (new VerifierException(e));
	}
    }

    public SwiftVerifier(InputSource source)
	throws VerifierConfigurationException, SAXException {

	try {
	    _init(SchemaLoader.load(source));
	} catch (SchemaSyntaxErrorException e) {
	    throw (new VerifierException(e));
	} catch (ClassNotFoundException e) {
	    throw (new VerifierException(e));
	} catch (IllegalAccessException e) {
	    throw (new VerifierException(e));
	} catch (InstantiationException e) {
	    throw (new VerifierException(e));
	} catch (SAXException e) {
	    throw (new VerifierException(e));
	} catch (IOException e) {
	    throw (new VerifierException(e));
	}
    }

    private void _init(Grammar grammar)
	throws VerifierConfigurationException {

	grammar_ = grammar;
	reader_ = _getXMLReader();
    }

    private XMLReader _getXMLReader() throws VerifierConfigurationException {
	try {
	    SAXParserFactory factory = SAXParserFactory.newInstance();
	    SAXParser saxParser = factory.newSAXParser();
//	    return (saxParser.getXMLReader());
	    return (new org.apache.xerces.parsers.SAXParser());// XXX
	} catch (ParserConfigurationException e) {
	    throw (new VerifierConfigurationException(e));
	} catch (SAXException e) {
	    throw (new VerifierConfigurationException(e));
	} 
    }

    public boolean isFeature(String feature)
        throws SAXNotRecognizedException, SAXNotSupportedException {

	if (FEATURE_HANDLER.equals(feature) ||
	    FEATURE_FILTER.equals(feature)) {

	    return (true);
	} else {
	    throw (new SAXNotRecognizedException(feature));
	}
    }

    public void setFeature(String feature, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException {

	throw (new SAXNotRecognizedException(feature));
    }

    public Object getProperty(String property)
        throws SAXNotRecognizedException, SAXNotSupportedException {

	throw (new SAXNotRecognizedException(property));
    }

    public void setProperty(String property, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException {

	throw (new SAXNotRecognizedException(property));
    }

    public void setErrorHandler(ErrorHandler handler) {
	reader_.setErrorHandler(handler);
    }

    public void setEntityResolver(EntityResolver handler) {
	reader_.setEntityResolver(handler);
    }

    public boolean verify(String uri) throws SAXException, IOException {
	RELAXNormalHandler verifier = new RELAXNormalHandler(grammar_);
	reader_.setContentHandler(verifier);
	try {
	    reader_.parse(uri);
	    return (true);
	} catch (NotValidException e) {
	    return (false);
	} catch (SAXException e) {
	    throw (new SAXException(e));
	}
    }

    public boolean verify(InputSource source)
	throws SAXException, IOException {

	RELAXNormalHandler verifier = new RELAXNormalHandler(grammar_);
	reader_.setContentHandler(verifier);
	try {
	    reader_.parse(source);
	    return (true);
	} catch (NotValidException e) {
	    return (false);
	} catch (SAXException e) {
	    throw (new SAXException(e));
	}
    }

    public boolean verify(Node node) throws SAXException {
	DOMSAXProducer producer = new DOMSAXProducer(node);
	RELAXNormalHandler verifier = new RELAXNormalHandler(grammar_);
	try {
	    producer.makeEvent(verifier);
	    return (true);
	} catch (NotValidException e) {
	    return (false);
	} catch (SAXException e) {
	    throw (new VerifierException(e));
	}
    }

    public VerifierHandler getVerifierHandler() {
	return (new SwiftVerifierHandler(grammar_));
    }

    public VerifierFilter getVerifierFilter() {
	return (new SwiftVerifierFilter(grammar_));
    }
}
