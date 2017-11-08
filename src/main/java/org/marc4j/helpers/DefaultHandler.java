// $Id: DefaultHandler.java,v 1.6 2002/08/03 15:14:39 bpeters Exp $
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

import org.marc4j.MarcHandler;
import org.marc4j.ErrorHandler;
import org.marc4j.MarcReaderException;
import org.marc4j.marc.Leader;

/**
 * <p>
 * Provides default implementations for the callbacks
 * in the <code>MarcHandler</code> and <code>ErrorHandler</code>
 * interface.
 * </p>
 *
 * <p>
 * Application writers can extend this class when they need to
 * implement only part of an interface.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.6 $
 *
 * @see MarcHandler
 * @see ErrorHandler
 */
public class DefaultHandler
    implements MarcHandler, ErrorHandler
{

    public void startCollection()
    {
        // Do nothing.
    }

    public void endCollection()
    {
        // Do nothing.
    }

    public void startRecord(Leader leader)
    {
        // Do nothing.
    }

    public void endRecord()
    {
        // Do nothing.
    }

    @Override
    public void controlField(String tag, char[] data, Long id)
    {
        // Do nothing.
    }

    @Override
    public void startDataField(String tag, char ind1, char ind2, Long id)
    {
        // Do nothing.
    }

    public void endDataField(String tag)
    {
        // Do nothing.
    }

    @Override
    public void subfield(char identifier, char[] data, String linkCode)
    {
        // Do nothing.
    }

    public void warning(MarcReaderException exception)
    {
        // Do nothing.
    }

    public void error(MarcReaderException exception)
    {
        // Do nothing.
    }

    public void fatalError(MarcReaderException exception)
    {
        // Do nothing.
    }

}
