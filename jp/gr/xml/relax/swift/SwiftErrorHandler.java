package jp.gr.xml.relax.swift;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import jp.co.swiftinc.relax.verifier.RELAXErrorHandler;
import jp.co.swiftinc.relax.verifier.NotValidException;
import jp.co.swiftinc.relax.verifier.WarningException;

/**
 * SwiftErrorHandler
 *
 * @since   May. 28, 2001
 * @version May. 28, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public class SwiftErrorHandler implements RELAXErrorHandler {
    private SwiftVerifier verifier_;

    public SwiftErrorHandler(SwiftVerifier verifier) {
	verifier_ = verifier;
    }

    public void error(NotValidException e) {
	try {
	    verifier_.getErrorHandler().			error(new SAXParseException(e.getMessage(), e.loc, e));
	} catch (SAXException ee) {
	}
    }

    public void warning(WarningException e) {
	try {
	    verifier_.getErrorHandler().			warning(new SAXParseException(e.getMessage(), e.loc, e));
	} catch (SAXException ee) {
	}
    }
}
