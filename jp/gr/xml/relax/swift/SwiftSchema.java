package jp.gr.xml.relax.swift;

import jp.co.swiftinc.relax.schema.Grammar;

import org.iso_relax.verifier.Schema;
import org.iso_relax.verifier.Verifier;
import org.iso_relax.verifier.VerifierConfigurationException;

public class SwiftSchema implements Schema {
	
	private final Grammar grammar;
	
	public SwiftSchema( Grammar _grammar ) {
		this.grammar = _grammar;
	}
	
	public Verifier newVerifier() throws VerifierConfigurationException {
		return new SwiftVerifier(grammar);
	}
}
