package org.iso_relax.dispatcher.impl;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.iso_relax.dispatcher.IslandVerifier;
import org.iso_relax.dispatcher.IslandSchema;
import org.iso_relax.dispatcher.Dispatcher;
import org.iso_relax.dispatcher.Rule;

/**
 * ignores namespaces which have no associated grammar.
 * 
 * @author
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
public final class IgnoreVerifier
	extends DefaultHandler
	implements IslandVerifier
{
	private final Rule[] rules;
	
	/**
	 * 
	 * @param assignedRules
	 *		this Verifier is supposed to validate these rules.
	 *		since this IslandVerifier actually does nothing,
	 *		all these rules will be reported as satisfied
	 *		upon completion.
	 */
	public IgnoreVerifier( String namespaceToIgnore, Rule[] assignedRules )
	{
		this.namespaceToIgnore = namespaceToIgnore;
		this.rules = assignedRules;
	}
	
	/**
	 * elements in this namespace is validated by this IgnoreVerifier.
	 */
	private final String namespaceToIgnore;
	
	public Rule[] endIsland() { return rules; }
	public void endChildIsland( String uri, Rule[] assignedLabels ){}
	
	private Dispatcher dispatcher;
	public void setDispatcher( Dispatcher disp ) { this.dispatcher=disp; }
	
	public void startElement( String namespaceURI, String localName, String qName, Attributes attributes )
		throws SAXException
	{
		if( namespaceToIgnore.equals(namespaceURI) )
			return;		// this element is "validated".
		
		// try to locate a grammar of this namespace
		IslandSchema is = dispatcher.getSchemaProvider().getSchemaByNamespace(namespaceURI);
		if( is==null )
		{// no grammar is declared with this namespace URI.
			return;	// continue ignoring.
		}

		// a schema is found: revert to normal mode and validate them.
		IslandVerifier iv = is.createNewVerifier( namespaceURI, is.getRules() );
		dispatcher.switchVerifier(iv);
		
		// simulate this startElement method.
		iv.startElement(namespaceURI,localName,qName,attributes);
	}
}
