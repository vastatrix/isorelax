
package org.iso_relax.dispatcher;

import java.util.Set;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Interface for verifier that validates one island.
 * 
 * @author
 *		<a href="mailto:mura034@attglobal.net">MURATA Makoto (FAMILY Given)</a>,
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 * 
 * @version 1.1
 */
public interface IslandVerifier extends ContentHandler
{
	/**
	 * Dispatcher passes itself to IslandVerifier by calling this method
	 * from Dispatcher.switchVerifier method.
	 */
	void setDispatcher( Dispatcher disp );
	
	/**
	 * substitute for endDocument event.
	 * 
	 * This method is called after endElement method is called
	 * for the top element in the island.
	 * endDocument method is never called for IslandVerifier.
	 * 
	 * @return
	 *		the callee must return all validated rules.
	 *		If every candidate fails, return an empty array.
	 *		
	 *		It is the callee's responsibility
	 *		to report an error. The callee may also recover from error.
	 * 
	 *		Never return null.
	 */
	public Rule[] endIsland() throws SAXException;
	
	/**
	 * this method is called after verification of the child island
	 * is completed, instead of endElement method.
	 * 
	 * @param uri
	 *		namespace URI of the child island.
	 * @param assignedLabel
	 *		set of labels that were successfully assigned to this child island.
	 *		when every label was failed, then an empty array is passed.
	 */
	public void endChildIsland( String uri, Rule assignedLabels[] ) throws SAXException;
}