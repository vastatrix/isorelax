package org.iso_relax.jaxp;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.VerifierConfigurationException;

/**
 * Adds features to SAXParserFactory about validation through iso_relax API.
 * @author Daisuke OKAJIMA
 */
public class ValidatingSAXParserFactory extends SAXParserFactory
{
    protected SAXParserFactory _WrappedFactory;
    protected Schema _Schema;
    
    /**
     * creates a new instance with an internal SAXParserFactory and Schema.
     * @param wrapped internal SAXParser
     * @param schema  compiled schema. 
     */
    protected ValidatingSAXParserFactory(SAXParserFactory wrapped, Schema schema)
    {
        _WrappedFactory = wrapped;
        _Schema = schema;
        _WrappedFactory.setValidating(true); //validation is enabled initially
    }

    /**
     * returns a new SAX parser.
     * If setValidating(false) is called previously, this method simply returns the implementation of wrapped SAXParser.
     */
    public SAXParser newSAXParser() throws ParserConfigurationException, SAXException
    {
        if(_WrappedFactory.isValidating())
            return new RelaxSAXParser(_WrappedFactory.newSAXParser(), _Schema);
        else
            return _WrappedFactory.newSAXParser();
    }

    /**
     * @see SAXParserFactory#setFeature(String, boolean)
     */
    public void setFeature(String name, boolean value) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
    {
        _WrappedFactory.setFeature(name, value);
    }

    /**
     * @see SAXParserFactory#getFeature(String)
     */
    public boolean getFeature(String name) throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
    {
        return _WrappedFactory.getFeature(name);
    }

    public boolean isNamespaceAware()
    { return _WrappedFactory.isNamespaceAware(); }
    public boolean isValidating()
    { return _WrappedFactory.isValidating(); }
    public void setNamespaceAware(boolean awareness)
    { _WrappedFactory.setNamespaceAware(awareness); }
    public void setValidating(boolean validating)
    { _WrappedFactory.setValidating(validating); }

    /**
     * creates a new instance that wraps the installed DocumentBuilderFactory
     * @param schema the compiled Schema object. It can not be null.
     */
    public static RelaxSAXParserFactory newInstance(Schema schema)
    {
        return new RelaxSAXParserFactory(SAXParserFactory.newInstance(), schema);
    }    
}
