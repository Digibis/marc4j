// $Id: MarcXmlWriter.java,v 1.18 2003/04/11 20:42:01 bpeters Exp $
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
package org.marc4j.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Category;
import org.marc4j.helpers.ErrorHandlerImpl;
import org.marc4j.marcxml.Converter;
import org.marc4j.marcxml.MarcXmlReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * <p>
 * Provides a driver for <code>MarcXmlReader</code>
 * to convert MARC records to MARCXML or to a different format using
 * an XSLT stylesheet.
 * </p>
 *
 * <p>
 * For usage, run from the command-line with the following command:
 * </p>
 * <p>
 * <code>java org.marc4j.util.MarcXmlWriter -usage</code>
 * </p>
 *
 * <p>
 * <b>A note about character encodings:</b><br/>
 * If no input or output encoding is specified on the command-line the
 * default charset is used. To specify input or output encodings use
 * charset names supported by your Java virtual machine. The following
 * command-line example convert ANSEL to UTF-8:<br/>
 * 
 * <pre>
 * java org.marc4j.util.MarcXmlWriter -ie ISO8859_1 -oe UTF8 -convert ANSEL -out output.xml input.mrc
 * </pre>
 * 
 * <b>Note:</b> the Latin-1 encoding (ISO8859_1) is used since ANSEL is not a
 * supported character encoding.
 * </p>
 *
 * <p>
 * Check the home page for <a href="http://www.loc.gov/standards/marcxml/">
 * MARCXML</a> for more information about the MARCXML format.
 * </p>
 *
 * <p>
 * <b>Note:</b> this class requires a JAXP compliant XSLT processor.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.18 $
 *
 * @see MarcXmlReader
 * @see Converter
 */
public class MarcXmlWriter
{

    private static Category log = Category.getInstance(MarcXmlWriter.class.getName());

    /**
     * <p>
     * Provides a static entry point.
     * </p>
     *
     */
    public static void main(String args[])
    {
        String input = null;
        String inputEncoding = null;
        String output = null;
        String outputEncoding = null;
        String stylesheet = null;
        boolean dtd = false;
        boolean xsd = false;
        String convert = null;
        long start = System.currentTimeMillis();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-xsl")) {
                if (i == args.length - 1) {
                    usage();
                }
                stylesheet = args[++i].trim();
            } else if (args[i].equals("-out")) {
                if (i == args.length - 1) {
                    usage();
                }
                output = args[++i].trim();
            } else if (args[i].equals("-ie")) {
                if (i == args.length - 1) {
                    usage();
                }
                inputEncoding = args[++i].trim();
            } else if (args[i].equals("-oe")) {
                if (i == args.length - 1) {
                    usage();
                }
                outputEncoding = args[++i].trim();
            } else if (args[i].equals("-dtd")) {
                dtd = true;
            } else if (args[i].equals("-xsd")) {
                xsd = true;
            } else if (args[i].equals("-convert")) {
                if (i == args.length - 1) {
                    usage();
                }
                convert = args[++i].trim();
            } else if (args[i].equals("-usage")) {
                usage();
            } else if (args[i].equals("-help")) {
                usage();
            } else {
                input = args[i].trim();

                // Must be last arg
                if (i != args.length - 1) {
                    usage();
                }
            }
        }
        if (input == null) {
            usage();
        }

        try {
            MarcXmlReader producer = new MarcXmlReader();
            producer.setProperty("http://marc4j.org/properties/error-handler", new ErrorHandlerImpl());
            if (xsd) producer.setProperty("http://marc4j.org/properties/schema-location",
                "http://www.loc.gov/MARC21/slim " + "http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd");
            if (convert != null) {
                CharacterConverter charconv = null;
                if ("ANSEL".equals(convert))
                    charconv = new AnselToUnicode();
                else if ("ISO5426".equals(convert))
                    charconv = new Iso5426ToUnicode();
                else if ("ISO6937".equals(convert))
                    charconv = new Iso6937ToUnicode();
                else {
                    System.err.println("Unknown character set");
                    System.exit(1);
                }
                producer.setProperty("http://marc4j.org/properties/character-conversion", charconv);
            }

            // If convert is true ISO8859_1 is used to read the incoming stream.
            BufferedReader reader;
            // if (convert != null)
            // reader = new BufferedReader(new InputStreamReader(
            // new FileInputStream(input), "ISO8859_1"));
            // else
            // reader = new BufferedReader(new InputStreamReader(
            // new FileInputStream(input), "UTF8"));

            if (inputEncoding != null)
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(input), inputEncoding));
            else
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(input)));

            InputSource in = new InputSource(reader);
            Source source = new SAXSource(producer, in);
            Writer writer;

            // if (output == null)
            // writer = new BufferedWriter(new OutputStreamWriter(System.out, "UTF8"));
            // else
            // writer = new BufferedWriter(new OutputStreamWriter(
            // new FileOutputStream(output), "UTF8"));

            if (output == null && outputEncoding != null)
                writer = new BufferedWriter(new OutputStreamWriter(System.out, outputEncoding));
            else if (output != null && outputEncoding == null)
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
            else if (output != null && outputEncoding != null)
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), outputEncoding));
            else
                writer = new BufferedWriter(new OutputStreamWriter(System.out));

            Result result = new StreamResult(writer);

            Converter converter = new Converter();
            if (stylesheet != null) {
                Source style = new StreamSource(new File(stylesheet).toURL().toString());
                converter.convert(style, source, result);
            } else {
                converter.convert(source, result);
            }

        } catch (SAXNotSupportedException e) {
            log.error("No se soporta la operaci�n indicada", e);
        } catch (SAXNotRecognizedException e) {
            log.error("Identificador no reconocido", e);
        } catch (SAXException e) {
            log.error("Se ha producido un error al convertir los registros MARC", e);
        } catch (TransformerException e) {
            log.error("Se ha producido un error durante la transformación de los registros", e);
        } catch (IOException e) {
            log.error("Se ha producido un error al escribir los registros en MARCXML", e);
        }
        System.err.println("Total time: " + (System.currentTimeMillis() - start) + " miliseconds");
    }

    private static void usage()
    {
        System.err.println("MARC4J version beta 7, Copyright (C) 2002-2003 Bas Peters");
        System.err.println("Usage: org.marc4j.util.MarcXmlWriter [-options] <file.xml>");
        System.err.println("       -xsd = Add W3C XML Schema Location to root element");
        System.err.println("       -xsl <file> = Postprocess MARCXML using XSLT stylesheet <file>");
        System.err.println("       -out <file> = Output using <file>");
        System.err.println("       -ie <encoding> = Input using charset <encoding>");
        System.err.println("       -oe <encoding> = Output using charset <encoding>");
        System.err.println("       -convert [ANSEL | ISO5426 | ISO6937] = convert to UTF-8 using");
        System.err.println("          specified character set");
        System.err.println("       -usage or -help = this message");
        System.err.println("Without a stylesheet the program outputs well-formed MARCXML");
        System.err.println("See http://marc4j.tigris.org for more information.");
        System.exit(1);
    }
}
