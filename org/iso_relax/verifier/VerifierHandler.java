package org.iso_relax.verifier;

import org.xml.sax.ContentHandler;

/**
 * VerifierHandler
 *
 * @since   Feb. 23, 2001
 * @version Feb. 24, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public interface VerifierHandler extends ContentHandler {
    boolean isValid() throws IllegalStateException;
}
