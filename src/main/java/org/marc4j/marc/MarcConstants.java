// $Id: MarcConstants.java,v 1.5 2002/08/03 15:14:39 bpeters Exp $
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
package org.marc4j.marc;

/**
 * <p>
 * <code>MarcConstants</code> defines control characters
 * as used in a record.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.5 $
 *
 */
public class MarcConstants
{

    /** RECORD TERMINATOR */
    public static final int RT = 0x001D;

    /** FIELD TERMINATOR */
    public static final int FT = 0x001E;

    /** SUBFIELD DELIMITER */
    public static final int US = 0x001F;

    /** BLANK */
    public static final int BLANK = 0x0020;

    private MarcConstants()
    {
    }

}
