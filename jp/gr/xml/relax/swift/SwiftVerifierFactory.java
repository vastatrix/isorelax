package jp.gr.xml.relax.swift;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.VerifierException;
import org.iso_relax.verifier.Schema;
import org.w3c.dom.Document;
import jp.co.swiftinc.relax.schema.Grammar;
import jp.co.swiftinc.relax.schema.SchemaLoader;
import jp.co.swiftinc.relax.schema.SchemaSyntaxErrorException;
import jp.gr.xml.relax.sax.RELAXEntityResolver;


/**
 * SwiftVerifierFactory
 *
 * @since   Feb. 23, 2001
 * @version Mar.  4, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftVerifierFactory extends VerifierFactory {
    public boolean isFeature(String feature)
        throws SAXNotRecognizedException, SAXNotSupportedException {

	throw (new SAXNotRecognizedException(feature));
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

    public Verifier newVerifier(
	String uri
    ) throws VerifierConfigurationException, SAXException, IOException {
	return (new SwiftVerifier(loadSchema(uri)));
    }

    public Verifier newVerifier(
	InputSource source
    ) throws VerifierConfigurationException, SAXException, IOException {
	return (new SwiftVerifier(loadSchema(source)));
    }
	
	public Schema compileSchema( InputSource source )
			throws VerifierConfigurationException, SAXException {
		return new SwiftSchema(loadSchema(source));
	}
	
	public Schema compileSchema( String uri )
			throws VerifierConfigurationException, SAXException, IOException {
		return new SwiftSchema(loadSchema(uri));
	}
	
	
		

    private DocumentBuilder _getDocumentBuilder()
	throws VerifierConfigurationException {

	try {
	    DocumentBuilderFactory factory
		= DocumentBuilderFactory.newInstance();
	    factory.setNamespaceAware(true);
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    builder.setEntityResolver(new RELAXEntityResolver());
	    return (builder);
	} catch (ParserConfigurationException e) {
	    throw (new VerifierConfigurationException(e));
	} 
    }
	
    private Grammar loadSchema(String uri)
		throws VerifierConfigurationException, SAXException, IOException {

		try {
			DocumentBuilder builder = _getDocumentBuilder();
			Document doc = builder.parse(uri);
			return SchemaLoader.load(doc, uri);
		} catch (SchemaSyntaxErrorException e) {
			throw (new VerifierException(e));
		} catch (SAXException e) {
			throw (new VerifierException(e));
		}
    }

    private Grammar loadSchema(InputSource source)
		throws VerifierConfigurationException, SAXException {

	try {
	    DocumentBuilder builder = _getDocumentBuilder();
	    Document doc = builder.parse(source);
	    return SchemaLoader.load(doc, source.getSystemId());
	} catch (SchemaSyntaxErrorException e) {
	    throw (new VerifierException(e));
	} catch (SAXException e) {
	    throw (new VerifierException(e));
	} catch (IOException e) {
	    throw (new VerifierException(e));
	}
    }


}
