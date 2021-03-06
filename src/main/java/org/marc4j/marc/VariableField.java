// $Id: VariableField.java,v 1.6 2003/03/31 19:55:26 ceyates Exp $
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

import java.io.Serializable;

/**
 * <p>
 * <code>VariableField</code> defines general behaviour for
 * variable fields.
 * </p>
 *
 * <p>
 * According to the MARC standard the variable fields follow the
 * leader and the directory in the record and consist of control fields
 * and data fields. Control fields precede data fields in the record and
 * are arranged in the same sequence as the corresponding entries in
 * the directory.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.6 $
 *
 * @see ControlField
 * @see DataField
 */
public abstract class VariableField
    implements Serializable, Cloneable
{

    private static final long serialVersionUID = -5303416788186473947L;

    /** The field terminator */
    public static final char FT = MarcConstants.FT;

    /** Empty value for the field id */
    public static final Long EMPTY_ID = null;

    /** Field id */
    private Long id;

    /** The tag name. */
    private String tag;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public VariableField()
    {
    }

    /**
     * <p>
     * Creates a new <code>VariableField</code> for the supplied tag.
     * </p>
     *
     * @param tag the tag name
     */
    public VariableField(String tag)
    {
        setTag(tag);
    }

    /**
     * <p>
     * Registers the tag name.
     * </p>
     *
     * @param tag the tag name
     */
    public void setTag(String tag)
    {
        if (!Tag.isValid(tag)) throw new IllegalTagException(tag);
        this.tag = tag;
    }

    /**
     * <p>
     * Returns the tag name.
     * </p>
     *
     * @return <code>String</code> - the tag name
     */
    public String getTag()
    {
        return tag;
    }

    /**
     * @return Devuelve el valor de id.
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id Nuevo valor para id.
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    public abstract Object clone();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

}

// End of VariableField.java
