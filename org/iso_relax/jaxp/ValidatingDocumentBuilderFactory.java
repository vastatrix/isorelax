package org.iso_relax.jaxp;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.iso_relax.verifier.Schema;

/**
 * Adds features to DocumentBuilderFactory about validation through iso_relax API.
 * @author Daisuke OKAJIMA
 */
public class ValidatingDocumentBuilderFactory extends DocumentBuilderFactory
{
    protected Schema _Schema;
    protected DocumentBuilderFactory _WrappedFactory;
    
    /**
     * creates a new instance with an internal DocumentBuilderFactory and Schema.
     * @param wrapped internal DocumentBuilderFactory
     * @param schema  compiled schema. 
     */
    protected ValidatingDocumentBuilderFactory(DocumentBuilderFactory wrapped, Schema schema)
    {
        _WrappedFactory = wrapped;
        _Schema = schema;
        _WrappedFactory.setValidating(true); //validation is enabled initially
    }

    /**
     * returns a new DOM parser.
     * If setValidating(false) is called previously, this method simply returns the implementation of wrapped DocumentBuilder.
     */
    public DocumentBuilder newDocumentBuilder() throws ParserConfigurationException
    {
        if(_WrappedFactory.isValidating())
            return new ValidatingDocumentBuilder(_WrappedFactory.newDocumentBuilder(), _Schema);
        else //if validation is disabled, we simply return the implementation of wrapped DocumentBuilder
            return _WrappedFactory.newDocumentBuilder();
    }

    /**
     * @see DocumentBuilderFactory#setAttribute(String, Object)
     */
    public void setAttribute(String name, Object value)
    {
        _WrappedFactory.setAttribute(name, value);
    }

    /**
     * @see DocumentBuilderFactory#getAttribute(String)
     */
    public Object getAttribute(String name)
    {
        return _WrappedFactory.getAttribute(name);
    }

    public boolean isCoalescing()
    { return _WrappedFactory.isCoalescing(); }
    public boolean isExpandEntityReference()
    { return _WrappedFactory.isExpandEntityReferences(); }
    public boolean isIgnoringComments()
    { return _WrappedFactory.isIgnoringComments(); }
    public boolean isIgnoringElementContentWhitespace()
    { return _WrappedFactory.isIgnoringElementContentWhitespace(); }
    public boolean isNamespaceAware()
    { return _WrappedFactory.isNamespaceAware(); }
    public boolean isValidating()
    { return _WrappedFactory.isValidating(); }
    public void setCoalescing(boolean coalescing)
    { _WrappedFactory.setCoalescing(coalescing); }
    public void setExpandEntityReference(boolean expandEntityRef)
    { _WrappedFactory.setExpandEntityReferences(expandEntityRef); }
    public void setIgnoringComments(boolean ignoreComments)
    { _WrappedFactory.setIgnoringComments(ignoreComments); }
    public void setIgnoringElementContentWhitespace(boolean whitespace)
    { _WrappedFactory.setIgnoringElementContentWhitespace(whitespace); }
    public void setNamespaceAware(boolean awareness)
    { _WrappedFactory.setNamespaceAware(awareness); }
    public void setValidating(boolean validating)
    { _WrappedFactory.setValidating(validating); }
    
    /**
     * creates a new instance that wraps the installed DocumentBuilderFactory
     * @param schema the compiled Schema object. It can not be null.
     */
    public static ValidatingDocumentBuilderFactory newInstance(Schema schema)
    {
        return new ValidatingDocumentBuilderFactory(DocumentBuilderFactory.newInstance(), schema);
    }    
}
