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
    private ErrorHandler handler_;

    public SwiftErrorHandler(ErrorHandler handler) {
	handler_ = handler;
    }

    public void error(NotValidException e) {
	try {
	    handler_.error(new SAXParseException(e.getMessage(), e.loc, e));
	} catch (SAXException ee) {
	}
    }

    public void warning(WarningException e) {
	try {
	    handler_.warning(new SAXParseException(e.getMessage(), e.loc, e));
	} catch (SAXException ee) {
	}
    }
}
