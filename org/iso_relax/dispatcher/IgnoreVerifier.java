package org.iso_relax.dispatcher;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Set;

/**
 * ignores the entire subtree.
 * 
 * this class is used for error recovery by Dispatcher.
 * 
 * @author
 *		<a href="mailto:k-kawa@bigfoot.com">Kohsuke KAWAGUCHI</a>
 */
final class IgnoreVerifier
	extends DefaultHandler
	implements IslandVerifier
{
	private Set candidates;
	private static final Set emptySet = new java.util.HashSet();
	
	public void startIsland( Set candidateLabels, String uri )
	{
		this.candidates = candidateLabels;
	}
	public Set endIsland()
	{
		return candidates;
	}
	public Set startChildIsland( String uri )	{ return emptySet; }
	public void endChildIsland( Set assignedLabels ){}
}
