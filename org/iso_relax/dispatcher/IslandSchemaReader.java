package org.iso_relax.dispatcher;

/**
 * IslandSchemaReader is responsible for parsing IslandSchema.
 * 
 * @author
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
public interface IslandSchemaReader extends org.xml.sax.ContentHandler
{
	/**
	 * gets parsed schema.
	 * this method is called after parsing is finished.
	 * 
	 * @return
	 *		return null if parsing was failed
	 *		(for example by an error in the schema file).
	 */
	IslandSchema getSchema();
}
