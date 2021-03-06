// $Id: TaggedWriter.java,v 1.3 2002/08/03 15:14:39 bpeters Exp $
/**
 * Copyright (C) 2002 Bas Peters (mail@bpeters.com)
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
package org.marc4j.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Category;
import org.marc4j.MarcHandler;
import org.marc4j.marc.Leader;

/**
 * <p>
 * Implements the <code>MarcHandler</code> interface
 * to write MARC data in tagged display format.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.3 $
 *
 * @see MarcHandler
 */
public class TaggedWriter
    implements MarcHandler
{

    private static Category log = Category.getInstance(TaggedWriter.class.getName());
    /** The Writer object */
    private Writer out;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    public TaggedWriter()
        throws IOException
    {
        this(System.out);
    }

    /**
     * <p>
     * Creates a new instance.
     * </p>
     *
     * @param out the {@link OutputStream} object
     *
     */
    public TaggedWriter(OutputStream out)
        throws IOException
    {
        this(new OutputStreamWriter(out));
    }

    /**
     * <p>
     * Creates a new instance.
     * </p>
     *
     * @param out the {@link OutputStream} object
     * @param encoding the encoding
     *
     */
    public TaggedWriter(OutputStream out, String encoding)
        throws IOException
    {
        this(new OutputStreamWriter(out, encoding));
    }

    /**
     * <p>
     * Creates a new instance and registers the Writer object.
     * </p>
     *
     * @param out the {@link Writer} object
     */
    public TaggedWriter(Writer out)
    {
        setWriter(out);
    }

    /**
     * <p>
     * Registers the Writer object.
     * </p>
     *
     * @param out the {@link Writer} object
     */
    public void setWriter(Writer out)
    {
        this.out = out;
    }

    /**
     * <p>
     * System exits when the Writer object is null.
     * </p>
     *
     */
    @Override
    public void startCollection()
    {
        if (out == null) System.exit(0);
    }

    @Override
    public void startRecord(Leader leader)
    {
        rawWrite("Leader ");
        rawWrite(leader.marshal());
        rawWrite('\n');
    }

    @Override
    public void controlField(String tag, char[] data, Long id)
    {
        rawWrite(tag);
        rawWrite(' ');
        rawWrite(new String(data));
        rawWrite('\n');
    }

    @Override
    public void startDataField(String tag, char ind1, char ind2, Long id)
    {
        rawWrite(tag);
        rawWrite(' ');
        rawWrite(ind1);
        rawWrite(ind2);
    }

    @Override
    public void subfield(char code, char[] data, String linkCode)
    {
        rawWrite('$');
        rawWrite(code);
        rawWrite(new String(data));
    }

    @Override
    public void endDataField(String tag)
    {
        rawWrite('\n');
    }

    @Override
    public void endRecord()
    {
        rawWrite('\n');
    }

    @Override
    public void endCollection()
    {
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("Error al finalizar la colección", e);
        }
    }

    private void rawWrite(char c)
    {
        try {
            out.write(c);
        } catch (IOException e) {
            log.error("Se ha producido un error al escribir en la salida", e);
        }
    }

    private void rawWrite(String s)
    {
        try {
            out.write(s);
        } catch (IOException e) {
            log.error("Se ha producido un error al escribir en la salida", e);
        }
    }

}
