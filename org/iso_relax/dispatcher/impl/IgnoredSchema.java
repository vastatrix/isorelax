package org.iso_relax.dispatcher.impl;

import org.iso_relax.dispatcher.IslandSchema;
import org.iso_relax.dispatcher.IslandVerifier;
import org.iso_relax.dispatcher.Rule;
import org.iso_relax.dispatcher.SchemaProvider;
import org.xml.sax.ErrorHandler;
import java.util.Iterator;
import java.util.Vector;

public class IgnoredSchema implements IslandSchema
{
	private static final Rule[] theRule = new Rule[]{ new Rule(){
		public String getName() { return "$$any$$"; }
			} };
		
	public Rule getRuleByName( String name )	{ return theRule[0]; }
	public Rule[] getRules() { return theRule; }
	public void bind( SchemaProvider provider, ErrorHandler handler ) {}
	
	public Iterator iterateRules() {
		Vector vec = new Vector();
		vec.add(theRule[0]);
		return vec.iterator();
	}
	
	public IslandVerifier createNewVerifier( String namespaceURI, Rule[] rules ) {
		return new IgnoreVerifier(namespaceURI,rules);
	}
}
