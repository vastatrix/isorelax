package org.iso_relax.verifier;

import org.xml.sax.XMLFilter;

/**
 * VerifierFilter
 *
 * @since   Feb. 23, 2001
 * @version Feb. 24, 2001
 * @author  ASAMI, Tomoharu (asami@zeomtech.com)
 */
public interface VerifierFilter extends XMLFilter {
    boolean isValid() throws IllegalStateException;
}
