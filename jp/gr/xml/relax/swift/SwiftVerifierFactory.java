package jp.gr.xml.relax.swift;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.iso_relax.verifier.VerifierFactory;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.VerifierException;

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
	return (new SwiftVerifier(uri));
    }

    public Verifier newVerifier(
	InputSource source
    ) throws VerifierConfigurationException, SAXException, IOException {
	return (new SwiftVerifier(source));
    }
}
