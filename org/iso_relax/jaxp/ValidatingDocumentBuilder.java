package org.iso_relax.jaxp;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;

/**
 * Adds features to DocumentBuilder about validation through iso_relax API.
 * @author Daisuke OKAJIMA
 */
class ValidatingDocumentBuilder extends DocumentBuilder
{
    protected DocumentBuilder _WrappedBuilder;
    protected Schema _Schema;
    protected Verifier _Verifier;
    
    /**
     * creates a new instance with an internal DocumentBuilder and Schema.
     * @param wrapped internal DOM parser
     * @param schema  compiled schema. 
     */
    protected ValidatingDocumentBuilder(DocumentBuilder wrapped, Schema schema) throws ParserConfigurationException
    {
        _WrappedBuilder = wrapped;
        _Schema = schema;
        try
        {
            _Verifier = _Schema.newVerifier();
        }
        catch(VerifierConfigurationException ex)
        {
            throw new ParserConfigurationException("a VerifierConfigurationException is raised at the creation of Verifier: " + ex.getMessage());
        }
    }
    
    /**
     * Parses the given InputSource and validates the resulting DOM.
     */
    public Document parse(InputSource inputsource) throws SAXException, IOException
    {
        return verify(_WrappedBuilder.parse(inputsource));
    }
    
    /**
     * Parses the given File and validates the resulting DOM.
     */
    public Document parse(File file) throws SAXException, IOException
    {
        return verify(_WrappedBuilder.parse(file));
    }
    
    /**
     * Parses the given InputStream and validates the resulting DOM.
     */
    public Document parse(InputStream strm) throws SAXException, IOException
    {
        return verify(_WrappedBuilder.parse(strm));
    }
    
    /**
     * Parses the given InputStream using the specified systemId and validates the resulting DOM.
     */
    public Document parse(InputStream strm, String systemId) throws SAXException, IOException
    {
        return verify(_WrappedBuilder.parse(strm, systemId));
    }
    
    /**
     * Parses the given url and validates the resulting DOM.
     */
    public Document parse(String url) throws SAXException, IOException
    {
        return verify(_WrappedBuilder.parse(url));
    }

    /**
     * @see DocumentBuilder#isNamespaceAware()
     */
    public boolean isNamespaceAware()
    {
        return _WrappedBuilder.isNamespaceAware();
    }

    /**
     * returns true always.
     */
    public boolean isValidating()
    {
        return true;
    }

    /**
     * @see DocumentBuilder#setEntityResolver(EntityResolver)
     */
    public void setEntityResolver(EntityResolver entityresolver)
    {
        _WrappedBuilder.setEntityResolver(entityresolver);
        _Verifier.setEntityResolver(entityresolver);
    }

    /**
     * @see DocumentBuilder#setErrorHandler(ErrorHandler)
     */
    public void setErrorHandler(ErrorHandler errorhandler)
    {
        _WrappedBuilder.setErrorHandler(errorhandler);
        _Verifier.setErrorHandler(errorhandler);
    }

    /**
     * @see DocumentBuilder#newDocument()
     */
    public Document newDocument()
    {
        return _WrappedBuilder.newDocument();
    }

    /**
     * @see DocumentBuilder#getDOMImplementation()
     */
    public DOMImplementation getDOMImplementation()
    {
        return _WrappedBuilder.getDOMImplementation();
    }

    private Document verify(Document dom) throws SAXException, IOException
    {
        if(_Verifier.verify(dom))
            return dom;
        else
            throw new SAXException("the document is invalid, but the ErrorHandler does not throw any Exception.");
    }
}
