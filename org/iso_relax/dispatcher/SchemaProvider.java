package org.iso_relax.dispatcher;

import java.util.Set;
import java.util.Iterator;

/**
 * provides necessary schema information for Dispatcher.
 * 
 * This interface can be implemented by applications.
 * 
 * @author <a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
public interface SchemaProvider
{
	/**
	 * creates IslandVerifier that validates document element.
	 */
	IslandVerifier createTopLevelVerifier();
	
	/**
	 * gets IslandSchema whose primary namespace URI is the given value.
	 * 
	 * @return null
	 *		if no such IslandSchema exists.
	 */
	IslandSchema getSchemaByNamespace( String uri );
	
	/**
	 * iterates all namespaces that are registered in this object.
	 */
	Iterator iterateNamespace();
	
	/**
	 * returns all IslandSchemata at once.
	 */
	IslandSchema[] getSchemata();
}
