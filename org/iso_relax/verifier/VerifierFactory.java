package org.iso_relax.verifier;

import java.io.File;
import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * VerifierFactory
 *
 * @since   Feb. 23, 2001
 * @version Apr. 17, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public abstract class VerifierFactory {
    /**
     * Creates a new instance of a VerifierFactory.
     */
    public static VerifierFactory newInstance()
	throws VerifierConfigurationException {

	return (new jp.gr.xml.relax.swift.SwiftVerifierFactory());
    }

    /**
     * Creates a new instance of a VerifierFactory by schema type.
     */
    public static VerifierFactory newInstance(String schemaType)
	throws VerifierConfigurationException {

	if ("relax".equals(schemaType)) {
	    return (new jp.gr.xml.relax.swift.SwiftVerifierFactory());
	} else if ("dtd".equals(schemaType)) {
	    throw (new VerifierConfigurationException(schemaType));
	} else {
	    throw (new VerifierConfigurationException(schemaType));
	}
    }

    /**
     * Creates a new instance of a Verifier from URI.
     *
     * @param uri URI of a schema file
     */
    public abstract Verifier newVerifier(String uri)
	throws VerifierConfigurationException,
	       SAXException,
	       IOException;

    /**
     * Creates a new instance of a Verifier from File.
     *
     * @param uri File of a schema file
     */
    public Verifier newVerifier(File file)
	throws VerifierConfigurationException,
	       SAXException,
	       IOException {

	return (newVerifier(file.toURL().toExternalForm()));
    }

    /**
     * Creates a new instance of a Verifier from InputSource.
     *
     * @param source InputSource of a schema file
     */
    public abstract Verifier newVerifier(InputSource source)
	throws VerifierConfigurationException,
	       SAXException,
	       IOException;

    /**
     * Indicates whether if the feature is supported, or not.
     *
     * @param feature feature name
     */
    public abstract boolean isFeature(String feature)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Sets feature value
     *
     * @param feature feature name
     * @param value feature value
     */
    public abstract void setFeature(String feature, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Gets property value
     *
     * @param property property name
     */
    public abstract Object getProperty(String property)
        throws SAXNotRecognizedException, SAXNotSupportedException;

    /**
     * Sets property value
     *
     * @param property property name
     * @param value property value
     */
    public abstract void setProperty(String property, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException;
}
