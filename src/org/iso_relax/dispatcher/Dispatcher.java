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

import java.util.Enumeration;
import java.util.Set;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.NamespaceSupport;

/**
 * splits incoming SAX events to "islands", and feed events to IslandVerifier.
 * 
 * @author
 *		<a href="mailto:mura034@attglobal.net">MURATA Makoto (FAMILY Given)</a>,
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 * 
 * @version 1.1
 */
public class Dispatcher implements ContentHandler
{
	private int depth =0;
	protected Locator documentLocator =null;
	
	/** current validating processor which processes this island. */
	private IslandVerifier currentHandler =null;
	
	protected String currentNamespace = null;
	
	/** Dispatcher will consult this object about schema information */
	protected final SchemaProvider schema;
	
	public Dispatcher( SchemaProvider schema )
	{
		this.schema = schema;
	}
	
	protected static final class Context
	{
		public final IslandVerifier		handler;
		public final int				depth;
		public final String				namespace;
		public final Context			previous;
		public Context( IslandVerifier handler, int depth, String namespace, Context previous )
		{
			this.handler = handler;
			this.depth = depth;
			this.namespace = namespace;
			this.previous = previous;
		}
	}
	protected Context contextStack = null;

	public void setDocumentLocator( Locator locator )
	{
		documentLocator = locator;
	}


	public void startElement( String uri, String localName, String qName, Attributes attributes )
		throws SAXException
	{
		if( !uri.equals(currentNamespace) )
		{
			Set candidates;
			
			// gets candidates of labels for this element
			if( currentHandler==null )
				candidates = schema.getTopLevelCandidate(uri);
			else
				candidates = currentHandler.startChildIsland(uri);

			// push context
			contextStack = new Context( currentHandler, depth, currentNamespace, contextStack );
		
			if( candidates==null )
				currentHandler = new IgnoreVerifier();
			else
			{
				currentHandler = schema.createHandler(uri);
				if( currentHandler==null )
					currentHandler = new IgnoreVerifier();
			}			
			
			
			currentHandler.setDocumentLocator(documentLocator);
			depth = 0;
			currentNamespace = uri;
			
			// inform new IslandHandler about all prefix mappings
			Enumeration e = nsMap.getDeclaredPrefixes();
			while( e.hasMoreElements() )
			{
				String prefix = (String)e.nextElement();
				currentHandler.startPrefixMapping( prefix, nsMap.getURI(prefix) );
			}
			
			currentHandler.startIsland(candidates,uri);
		}

		currentHandler.startElement(uri,localName,qName,attributes);
		depth++;
		nsMap.pushContext();
	}

	public void endElement (String uri, String localName, String qName)
		throws SAXException
	{
		nsMap.popContext();
		currentHandler.endElement(uri,localName,qName);

		if( --depth == 0 )
		{
			// call endPrefixMapping for all pre-declared prefixes.
			Enumeration e = nsMap.getDeclaredPrefixes();
			while( e.hasMoreElements() )
				currentHandler.endPrefixMapping( (String)e.nextElement() );
				
			// gets labels which are actually verified.
			Set results = currentHandler.endIsland();

			// pop context
			depth = contextStack.depth;
			currentHandler = contextStack.handler;
			currentNamespace = contextStack.namespace;
			contextStack = contextStack.previous;
			
			// report assigned label to the parent
			if( currentHandler!=null )
				currentHandler.endChildIsland(results);
		}
	}

	public void characters (char ch[], int start, int length)
		throws SAXException
	{
		if( currentHandler!=null )
			currentHandler.characters(ch, start, length);
	}

	public void ignorableWhitespace (char ch[], int start, int length)
		throws SAXException
	{
		if( currentHandler!=null )
			currentHandler.ignorableWhitespace(ch, start, length);
	}

	public void processingInstruction (String target, String data)
		throws SAXException
	{
		if( currentHandler!=null )
			currentHandler.processingInstruction (target, data);
	}
	
	public void skippedEntity( String name )
		throws SAXException
	{
		if( currentHandler!=null )
			currentHandler.skippedEntity(name);
	}
	
	// those events should not be reported to island verifier.
	public void startDocument() {}
	public void endDocument() {}
	
	protected final NamespaceSupport nsMap = new NamespaceSupport();
	
	public void startPrefixMapping(String prefix,String uri)
		throws SAXException
	{
		nsMap.declarePrefix(prefix,uri);
		if( currentHandler!=null )
			currentHandler.startPrefixMapping(prefix,uri);
	}
	public void endPrefixMapping(String prefix)
		throws SAXException
	{
		if( currentHandler!=null )
			currentHandler.endPrefixMapping(prefix);
	}
}
