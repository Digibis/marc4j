// $Id: ErrorHandlerImpl.java,v 1.5 2002/08/03 15:14:39 bpeters Exp $
/**
 * Copyright (C) 2002 Bas Peters
 *
 * This file is part of MARC4J
 *
 * MARC4J is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * MARC4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MARC4J; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.marc4j.helpers;

import org.apache.log4j.Category;
import org.marc4j.ErrorHandler;
import org.marc4j.MarcReaderException;

/**
 * <p>
 * Implements the <code>ErrorHandler</code> interface to report about
 * warnings and errors that occur during the parsing of a MARC record.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.5 $
 *
 * @see ErrorHandler
 */
public class ErrorHandlerImpl
    implements ErrorHandler
{

    private static Category log = Category.getInstance(ErrorHandlerImpl.class.getName());

    public void warning(MarcReaderException exception)
    {
        log.warn(printMarcException("Warning", exception), exception);
    }

    public void error(MarcReaderException exception)
    {
        log.error(printMarcException("Error", exception), exception);
    }

    public void fatalError(MarcReaderException exception)
    {
        log.fatal(printMarcException("FATAL", exception), exception);
    }

    public static String printMarcException(String label, MarcReaderException e)
    {
        StringBuffer buf = new StringBuffer();
        buf.append("** ");
        buf.append(label);
        buf.append(": ");
        buf.append(e.getMessage());
        buf.append('\n');
        if (e.getControlNumber() != null) {
            buf.append("   Record Number: ");
            buf.append(e.getControlNumber());
            buf.append('\n');
        }
        buf.append("   Character: ");
        buf.append(e.getPosition());
        buf.append('\n');
        return buf.toString();
    }

}
