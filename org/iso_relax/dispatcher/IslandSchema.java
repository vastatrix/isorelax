package org.iso_relax.dispatcher;

import java.util.Iterator;
import java.util.Set;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;

/**
 * represents a schema that validates one island.
 * 
 * @author
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
public interface IslandSchema
{
	/**
	 * creates a new IslandVerifier instance that is going to validate
	 * one island.
	 * 
	 * @param namespaceURI
	 *		namespace URI of the newly found element, which is going to be
	 *		validated by the newly created IslandVerifier.
	 * @param rules
	 *		set of Rule objects that newly created verifier shall validate.
	 */
	IslandVerifier createNewVerifier( String namespaceURI, Rule[] rules );
	
	/**
	 * gets exported rule object that has specified name.
	 * 
	 * @return null
	 *		if no rule is exported under the given name.
	 */
	Rule getRuleByName( String name );
	
	/**
	 * iterates all exported rule objects.
	 */
	Iterator iterateRules();
	
	/**
	 * returns all exported rule objects at once.
	 */
	Rule[] getRules();
	
	/**
	 * binds references to imported rules by using given provider.
	 * 
	 * this method is only called once before the first validation starts.
	 * 
	 * @exception SAXException
	 *		any error has to be reported to ErrorHandler first.
	 */
	void bind( SchemaProvider provider, ErrorHandler errorHandler )
		throws SAXException;
}
