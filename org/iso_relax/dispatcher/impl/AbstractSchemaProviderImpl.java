package org.iso_relax.dispatcher.impl;

import org.iso_relax.dispatcher.SchemaProvider;
import org.iso_relax.dispatcher.IslandSchema;
import org.iso_relax.dispatcher.IslandVerifier;
import org.xml.sax.ErrorHandler;
import java.util.Map;
import java.util.Iterator;

/**
 * default implementation of SchemaProvider.
 * 
 * Applications can use this class as the base class of their own SchemaProvider.
 */
public abstract class AbstractSchemaProviderImpl implements SchemaProvider {
	
	/** a map from primary namespace to IslandSchema. */
	protected final Map schemata = new java.util.HashMap();
	
	/** adds a new IslandSchema.
	 *
	 * the caller should make sure that the given uri is not defined already.
	 */
	public void addSchema( String uri, IslandSchema s ) {
		if( schemata.containsKey(uri) )
			throw new IllegalArgumentException();
		schemata.put( uri, s );
	}
			 
	public IslandSchema getSchemaByNamespace( String uri ) {
		return (IslandSchema)schemata.get(uri);
	}
	
	public Iterator iterateNamespace() {
		return schemata.keySet().iterator();
	}
	
	public IslandSchema[] getSchemata() {
		IslandSchema[] r = new IslandSchema[schemata.size()];
		schemata.values().toArray(r);
		return r;
	}
}
