package jp.gr.xml.relax.swift;

import jp.co.swiftinc.relax.schema.Grammar;

import org.iso_relax.verifier.VerifierConfigurationException;
import org.iso_relax.verifier.VerifierHandler;
import org.iso_relax.verifier.impl.VerifierImpl;
import org.xml.sax.ErrorHandler;

/**
 * SwiftVerifier
 *
 * @since   Feb. 23, 2001
 * @version May. 28, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftVerifier extends VerifierImpl {
    private VerifierHandler handler;

	public SwiftVerifier(Grammar grammar) throws VerifierConfigurationException {
		handler = new SwiftVerifierHandler(
			grammar,
			new SwiftErrorHandler(this));
    }

    public VerifierHandler getVerifierHandler() {
		return handler;
    }
	
	public ErrorHandler getErrorHandler() {
		return super.errorHandler;
	}
	
	protected void prepareXMLReader() throws VerifierConfigurationException {
		// ???: maybe this is a workaround for a bug in RELAX Verifier for Java
		reader = new org.apache.xerces.parsers.SAXParser();
	}

}
