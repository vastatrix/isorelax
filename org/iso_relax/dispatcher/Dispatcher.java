/**
 * DefaultDispatcher.java
 *
 * $Id$
 *
 * Created: Sun Feb 25 20:59:57 2001
 *
 * @author <a href="mailto:mura034@attglobal.net">MURATA Makoto (FAMILY Given)</a>
 * @version
 */

package org.iso_relax.dispatcher;

import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;
import org.xml.sax.ErrorHandler;

/**
 * splits incoming SAX events to "islands", and feed events to IslandVerifier.
 * 
 * @author
 *		<a href="mailto:mura034@attglobal.net">MURATA Makoto (FAMILY Given)</a>,
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 * 
 * @version 1.1
 */
public interface Dispatcher
{
	/**
	 * configure XMLReader to use this Dispatcher as a ContentHandler.
	 */
	void attachXMLReader( XMLReader reader );
	
	/**
	 * switches to the child IslandVerifier.
	 * this method can only be called during startElement method.
	 */
	void switchVerifier( IslandVerifier newVerifier ) throws SAXException;
	
	/**
	 * sets application-implemented ErrorHandler, which will receive all validation
	 * errors.
	 */
	void setErrorHandler( ErrorHandler handler );
	
	/**
	 * gets ErrorHandler to which IslandVerifier reports validation errors.
	 * 
	 * the caller may not assume that this method returns the same object
	 * that was passed to setErrorHandler method.
	 * 
	 * this method cannot return null.
	 */
	ErrorHandler getErrorHandler();
	
	/** get ShcmeaProvider object which is attached to this Dispatcher. */
	SchemaProvider getSchemaProvider();

	
	
	
	
	public static class NotationDecl {
		
		public final String name;
		public final String publicId;
		public final String systemId;
		public NotationDecl( String name, String publicId, String systemId ) {
			this.name=name; this.publicId=publicId; this.systemId=systemId;
		}
	}
	
	int countNotationDecls();
	NotationDecl getNotationDecl( int index );
	
	public static class UnparsedEntityDecl {
		
		public final String name;
		public final String publicId;
		public final String systemId;
		public final String notation;
		public UnparsedEntityDecl( String name, String publicId, String systemId, String notation ) {
			this.name=name; this.publicId=publicId; this.systemId=systemId; this.notation=notation;
		}
	}
	
	int countUnparsedEntityDecls();
	UnparsedEntityDecl getUnparsedEntityDecl( int index );
	
	
}
