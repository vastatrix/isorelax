package org.iso_relax.verifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Enumeration;
import java.net.URL;
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
 * @author	<a href="mailto:kohsukekawaguchi@yahoo.com">Kohsuke KAWAGUCHI</a>
 */
public abstract class VerifierFactory {

    /**
     * parses a schema at the specified location and returns a Verifier object
     * that validates documents by using that schema.
     * 
     * <p>
     * If you need to 
     * 
     * @param uri URI of a schema file
     */
    public abstract Verifier newVerifier(String uri)
	throws VerifierConfigurationException,
	       SAXException,
	       IOException;

    /**
     * parses a schema from the specified file and returns a Verifier object
     * that validates documents by using that schema.
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
     * parses a schema from the specified InputStream and returns a Verifier object
     * that validates documents by using that schema.
	 */
	public Verifier newVerifier(InputStream stream)
		throws VerifierConfigurationException, SAXException, IOException {
		
		return newVerifier( stream, null );
	}
	
	/**
     * parses a schema from the specified InputStream and returns a Verifier object
     * that validates documents by using that schema.
     * 
     * @param	systemId
     *		System ID of this stream.
	 */
	public Verifier newVerifier(InputStream stream, String systemId )
		throws VerifierConfigurationException, SAXException, IOException {
		
		InputSource is = new InputSource(stream);
		is.setSystemId(systemId);
		return newVerifier(is);
	}
	
    /**
     * parses a schema from the specified InputSource and returns a Verifier object
     * that validates documents by using that schema.
     *
     * @param source InputSource of a schema file
     */
    public abstract Verifier newVerifier(InputSource source)
	throws VerifierConfigurationException,
	       SAXException,
	       IOException;

	
	/**
	 * processes a schema into a Schema object, which is a compiled representation
	 * of a schema.
	 * 
	 * The obtained schema object can then be used concurrently across multiple
	 * threads.
	 */
	public abstract Schema compileSchema( InputSource is )
		throws VerifierConfigurationException, SAXException, IOException;

	/**
	 * processes a schema into a Schema object, which is a compiled representation
	 * of a schema.
	 * 
	 * The obtained schema object can then be used concurrently across multiple
	 * threads.
	 * 
	 * @param	url
	 *		A source url of a schema file to be compiled.
	 */
	public abstract Schema compileSchema( String url )
		throws VerifierConfigurationException, SAXException, IOException;

	/**
	 * processes a schema into a Schema object, which is a compiled representation
	 * of a schema.
	 * 
	 * The obtained schema object can then be used concurrently across multiple
	 * threads.
	 * 
	 * @param	stream
	 *		A stream object that holds a schema.
	 */
	public Schema compileSchema( InputStream stream )
		throws VerifierConfigurationException, SAXException, IOException {
		
		return compileSchema(stream,null);
	}

	/**
	 * processes a schema into a Schema object, which is a compiled representation
	 * of a schema.
	 * 
	 * The obtained schema object can then be used concurrently across multiple
	 * threads.
	 * 
	 * @param	systemId
	 *		The system Id of this input stream.
	 */
	public Schema compileSchema( InputStream stream, String systemId )
		throws VerifierConfigurationException, SAXException, IOException {
		
		InputSource is = new InputSource(stream);
		is.setSystemId(systemId);
		return compileSchema(is);
	}
	

	/**
	 * processes a schema into a Schema object, which is a compiled representation
	 * of a schema.
	 * 
	 * The obtained schema object can then be used concurrently across multiple
	 * threads.
	 * 
	 * @param	file
	 *		A schema file to be compiled
	 */
	public Schema compileSchema( File file )
		throws VerifierConfigurationException, SAXException, IOException {
	
		return compileSchema(file.toURL().toExternalForm());
	}
	
	
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

	
	
	
    /**
     * creates a new instance of a VerifierFactory.
     * 
     * @deprecated
     */
    public static VerifierFactory newInstance()
		throws VerifierConfigurationException {
		
		return newInstance("http://www.xml.gr.jp/xmlns/relaxNamespace");
    }

    /**
     * Creates a new instance of a VerifierFactory for the specified schema language.
     * 
     * @param	language
     *		URI that specifies the schema language.
     * 
     *		<p>
     *		It is preferable to use the namespace URI of the schema language
     *		to designate the schema language. For example,
     *		
     *		<table><thead>
     *			<tr>
     *				<td>URI</td>
     *				<td>language</td>
     *			</tr>
     *		</thead><tbody>
     *			<tr>
     *				<td><tt>http://relaxng.org/ns/structure/0.9</tt></td>
     *				<td><a href="http://www.oasis-open.org/committees/relax-ng/">
     *					RELAX NG
     *				</a></td>
     *			</tr><tr>
     *				<td><tt>http://www.xml.gr.jp/xmlns/relaxCore</tt></td>
     *				<td><a href="http://www.xml.gr.jp/relax">
     *					RELAX Core
     *				</a></td>
     *			</tr><tr>
     *				<td><tt>http://www.xml.gr.jp/xmlns/relaxNamespace</tt></td>
     *				<td><a href="http://www.xml.gr.jp/relax">
     *					RELAX Namespace
     *				</a></td>
     *			</tr><tr>
     *				<td><tt>http://www.thaiopensource.com/trex</tt></td>
     *				<td><a href="http://www.thaiopensource.com/trex">
     *					TREX
     *				</a></td>
     *			</tr><tr>
     *				<td><tt>http://www.w3.org/2001/XMLSchema</tt></td>
     *				<td><a href="http://www.w3.org/TR/xmlschema-1">
     *					W3C XML Schema
     *				</a></td>
     *			</tr>
     *		</tbody></table>
     *
     */
    public static VerifierFactory newInstance(String language) {

		Iterator itr = providers( VerifierFactoryLoader.class );
		while(itr.hasNext()) {
			VerifierFactoryLoader loader = (VerifierFactoryLoader)itr.next();
			try {
				VerifierFactory factory = loader.createFactory(language);
				if(factory!=null)	return factory;
			} catch (Throwable t) {}	// ignore any error
		}
		return null;
	}

    private static HashMap providerMap = new HashMap();

	
	
	// K.K: the following providers method is copied from Apache Batik project.
	
	/*****************************************************************************
	 * Copyright (C) The Apache Software Foundation. All rights reserved.        *
	 * ------------------------------------------------------------------------- *
	 * This software is published under the terms of the Apache Software License *
	 * version 1.1, a copy of which has been included with this distribution in  *
	 * the LICENSE file.                                                         *
	 *****************************************************************************/
	/*
	 * version  Service.java,v 1.1 2001/04/27 19:55:44 deweese Exp
	 */
    private static synchronized Iterator providers(Class cls) {
        ClassLoader cl = cls.getClassLoader();
        String serviceFile = "META-INF/services/"+cls.getName();

        // System.out.println("File: " + serviceFile);

        Vector v = (Vector)providerMap.get(serviceFile);
        if (v != null)
            return v.iterator();

        v = new Vector();
        providerMap.put(serviceFile, v);

        Enumeration e;
        try {
            e = cl.getResources(serviceFile);
        } catch (IOException ioe) {
            return v.iterator();
        }

        while (e.hasMoreElements()) {
            try {
                URL u = (URL)e.nextElement();
                // System.out.println("URL: " + u);

                InputStream    is = u.openStream();
                Reader         r  = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(r);

                String line = br.readLine();
                while (line != null) {
                    try {
                        // First strip any comment...
                        int idx = line.indexOf('#');
                        if (idx != -1)
                            line = line.substring(0, idx);

                        // Trim whitespace.
                        line = line.trim();

                        // If nothing left then loop around...
                        if (line.length() == 0) {
                            line = br.readLine();
                            continue;
                        }
                        // System.out.println("Line: " + line);

                        // Try and load the class 
                        Object obj = cl.loadClass(line).newInstance();
                        // stick it into our vector...
                        v.add(obj);
                    } catch (Exception ex) {
                        // Just try the next line
                    }
                    line = br.readLine();
                }
            } catch (Exception ex) {
                // Just try the next file...
            }
        }
        return v.iterator();
    }

	
}
