/// **
/*
 * Copyright (C) 2002 Bas Peters
 * This file is part of MARC4J
 * MARC4J is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * MARC4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with MARC4J; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.marc4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Category;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * <p>
 * <code>CodeTable</code> defines a data structure to facilitate <code>AnselToUnicode</code> character conversion.
 * </p>
 *
 * @author <a href="mailto:ckeith@loc.gov">Corey Keith</a>
 * @version $Revision: 1.2 $
 *
 */
public class CodeTable
{
    private static Category log = Category.getInstance(CodeTable.class.getName());
    protected static Hashtable charsets = null;
    protected static Hashtable combining = null;

    public static boolean isCombining(int i, int g0, int g1)
    {
        if (i <= 0x7E) {
            Vector v = (Vector)combining.get(new Integer(g0));
            return v.contains(new Integer(i));
        } else {
            Vector v = (Vector)combining.get(new Integer(g1));
            return v.contains(new Integer(i));
        }
    }

    public static char getChar(int c, int mode)
    {
        if (c == 0x20)
            return (char)c;
        else {
            Hashtable charset = (Hashtable)charsets.get(new Integer(mode));

            if (charset == null) {
                log.error("Hashtable not found: " + Integer.toHexString(mode));
                return (char)c;
            } else {
                Character ch = (Character)charset.get(new Integer(c));
                if (ch == null) {

                    int newc;
                    if (c < 0x80)
                        newc = c + 0x80;
                    else
                        newc = c - 0x80;
                    ch = (Character)charset.get(new Integer(newc));
                    if (ch == null) {
                        log.error("Character not found: " + Integer.toHexString(c) + " in Code Table: "
                            + Integer.toHexString(mode));
                        return (char)c;
                    } else
                        return ch.charValue();
                } else
                    return ch.charValue();
            }
        }
    }

    public CodeTable(InputStream byteStream)
    {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            InputSource src = new InputSource(byteStream);

            CodeTableHandler saxUms = new CodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();
        } catch (Exception exc) {
            log.error("Exception: " + exc, exc);
        }
    }

    public CodeTable(String filename)
    {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            File file = new File(filename);
            InputSource src = new InputSource(new FileInputStream(file));

            CodeTableHandler saxUms = new CodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();
        } catch (Exception exc) {
            log.error("Exception: " + exc, exc);
        }
    }

    public CodeTable(URI uri)
    {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            InputSource src = new InputSource(uri.toURL().openStream());

            CodeTableHandler saxUms = new CodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();
        } catch (Exception exc) {
            log.error("Exception: " + exc, exc);
        }
    }

}
