package org.iso_relax.dispatcher;

import java.util.Set;

/**
 * provides necessary schema information for Dispatcher.
 * 
 * This interface should be implemented by applications.
 * 
 * @author <a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
public interface SchemaProvider
{
	/** creates IslandVerifier that will be used to verify the child island. */
	IslandVerifier createHandler(String uri);
	
	/**
	 * this method is called when the document element is found.
	 * 
	 * @return
	 *		the callee must return all labels that are assignable
	 *		for the document element of this URI.
	 *		
	 *		If there is no such label (or possibly unacceptable URI),
	 *		then the callee is responsible
	 *		to issue an error. Also the callee may recover from error.
	 * 
	 *		If there is no label after error recovery, the callee can
	 *		return an empty set to delegate error recovery to Dispatcher.
	 * 
	 *		Never return null.
	 */
	Set getTopLevelCandidate( String uri );
}
