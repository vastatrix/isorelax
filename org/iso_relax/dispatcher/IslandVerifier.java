
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
	 * substitute for startDocument event.
	 * 
	 * This method is called before startElement method
	 * is called for the top element in the island.
	 * 
	 * @param candidateLabels
	 *		set of labels that the verifier of the parent island
	 *		is expected for this island.
	 *		
	 *		this verifier should try to validate the island by these
	 *		labels.
	 */
	public void startIsland( Set candidateLabels, String uri ) throws SAXException;
	
	/**
	 * substitute for endDocument event.
	 * 
	 * This method is called after endElement method is called
	 * for the top element in the island.
	 * 
	 * @return
	 *		the callee must return all validated labels.
	 *		If every candidate fails, return an empty set.
	 *		
	 *		It is the callee's responsibility
	 *		to report an error. The callee may also recover from error.
	 * 
	 *		Never return null.
	 */
	public Set endIsland() throws SAXException;
	
	/**
	 * this method is called when a child island is found.
	 * 
	 * @return
	 *		the callee must return all labels that are assignable
	 *		for the new child element.
	 *		
	 *		If there is no such label, then the callee is responsible
	 *		to issue an error. Also the callee may recover from error.
	 * 
	 *		If there is no label after error recovery, the callee can
	 *		return an empty set to delegate error recovery to Dispatcher.
	 * 
	 *		Never return null.
	 */
	public Set startChildIsland( String uri ) throws SAXException;
	
	/**
	 * this method is called after verification of the child island
	 * is completed.
	 * 
	 * @param assignedLabel
	 *		set of labels that were successfully assigned to this child island.
	 *		when every label was failed, then an empty set is passed.
	 */
	public void endChildIsland( Set assignedLabels ) throws SAXException;
}