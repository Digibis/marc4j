// $Id: Leader.java,v 1.10 2003/03/31 19:55:26 ceyates Exp $
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
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.marc4j.marc;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;

import com.digibis.commons.exceptions.ConfigException;

/**
 * <p><code>Leader</code> defines behaviour for the record label
 * (record position 00-23).  </p>
 *
 * <p>The leader is a fixed field that occurs at the beginning of a
 * MARC record and provides information for the processing of the record.
 * The structure of the leader according to the MARC standard is as
 * follows:</p>
 * <pre>
 * RECORD_LENGTH RECORD_STATUS TYPE_OF_RECORD IMPLEMENTATION-DEFINED
 * 00-04         05            06             07-08
 *   CHARACTER_CODING_SCHEME  INDICATOR_COUNT SUBFIELD_CODE_LENGTH
 *   09                       10              11
 *     BASE_ADDRESS_OF_DATA  IMPLEMENTATION-DEFINED  ENTRY_MAP
 *     12-16                 17-19                   20-23
 * </pre>
 * <p>This structure is returned by the {@link #marshal()} method.</p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a> 
 * @version $Revision: 1.10 $
 *
 */
public class Leader implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    /** The logical record length. */
    private int recordLength;

    /** The record status. */
    private char recordStatus;

    /** Type of record. */
    private char typeOfRecord;

    /** Implementation defined. */
    private char[] implDefined1;

    /** Character coding scheme. */
    private char charCodingScheme;

    /** The indicator count. */
    private int indicatorCount;

    /** The subfield code length. */
    private int subfieldCodeLength;

    /** The base address of data. */
    private int baseAddressOfData;

    /** Implementation defined. */
    private char[] implDefined2;

    /** Entry map. */
    private char[] entryMap;

    /** number format for both record length and base address of data */
    DecimalFormat df = new DecimalFormat("00000");

    public Leader() {}

    public Leader(String ldr) throws MarcException {
	unmarshal(ldr);
    }

    /**
     * <p>Registers the logical record length (positions 00-04).  </p>
     *
     * @param recordLength integer representing the
     *                     record length
     */
    public void setRecordLength(int recordLength) {
	this.recordLength = recordLength;
    }

    /**
     * <p>Registers the record status (position 05).</p>
     *
     * @param recordStatus character representing the
     *                     record status
     */
    public void setRecordStatus(char recordStatus) {
	Verifier.checkDataElement(recordStatus);
	this.recordStatus = recordStatus;
    }

    /**
     * <p>Registers the type of record (position 06).</p>
     *
     * @param typeOfRecord character representing the
     *                     type of record
     */
    public void setTypeOfRecord(char typeOfRecord) {
	Verifier.checkDataElement(typeOfRecord);
	this.typeOfRecord = typeOfRecord;
    }

    /**
     * <p>Registers implementation defined values (position 07-08).</p>
     *
     * @param implDefined1 character array representing the
     *                     implementation defined data
     */
    public void setImplDefined1(char[] implDefined1) {
	Verifier.checkDataElement(implDefined1);
	this.implDefined1 = implDefined1;
    }

    /**
     * <p>Registers the character encoding scheme
     * (position 09).</p>
     *
     * @param charCodingScheme character representing the
     *                         character encoding
     */
    public void setCharCodingScheme(char charCodingScheme) {
	Verifier.checkDataElement(charCodingScheme);
	this.charCodingScheme = charCodingScheme;
    }

    /**
     * <p>Registers the indicator count (position 10).</p>
     *
     * @param indicatorCount integer representing the
     *                       number of indicators present
     *                       in a data field
     */
    public void setIndicatorCount(int indicatorCount) {
	this.indicatorCount = indicatorCount;
    }

    /**
     * <p>Registers the subfield code length (position 11).</p>
     *
     * @param subfieldCodeLength integer representing the
     *                           subfield code length
     */
    public void setSubfieldCodeLength(int subfieldCodeLength) {
	this.subfieldCodeLength = subfieldCodeLength;
    }

    /**
     * <p>Registers the base address of data (positions 12-16).</p>
     *
     * @param baseAddressOfData integer representing the
     *                          base address of data
     */
    public void setBaseAddressOfData(int baseAddressOfData) {
	this.baseAddressOfData = baseAddressOfData;
    }

    /**
     * <p>Registers implementation defined values (positions 17-19).</p>
     *
     * @param implDefined2 character array representing the
     *                     implementation defined data
     */
    public void setImplDefined2(char[] implDefined2) {
	Verifier.checkDataElement(implDefined2);
    	this.implDefined2 = implDefined2;
    }

    /**
     * <p>Registers the entry map (positions 20-23).</p>
     *
     * @param entryMap character array representing the
     *                 entry map
     */
    public void setEntryMap(char[] entryMap) {
	Verifier.checkDataElement(entryMap);
	this.entryMap = entryMap;
    }

    /**
     * <p>Returns the logical record length (positions 00-04).</p>
     *
     * @return <code>int</code> - the record length
     */
    public int getRecordLength() {
	return recordLength;
    }

    /**
     * <p>Returns the record status (positions 05).</p>
     *
     * @return <code>char</code> - the record status
     */
    public char getRecordStatus() {
	return recordStatus;
    }

    /**
     * <p>Returns the record type (position 06).</p>
     *
     * @return <code>char</code> - the record type
     */
    public char getTypeOfRecord() {
	return typeOfRecord;
    }

    /**
     * <p>Returns implementation defined values
     * (positions 07-08).</p>
     *
     * @return <code>char[]</code> - implementation defined values
     */
    public char[] getImplDefined1() {
    	return implDefined1;
    }

    /**
     * <p>Returns the character coding scheme (position 09).</p>
     *
     * @return <code>char</code> - the character coding scheme
     */
    public char getCharCodingScheme() {
    	return charCodingScheme;
    }

    /**
     * <p>Returns the indicator count (positions 10).</p>
     *
     * @return <code>int</code> - the indicator count
     */
    public int getIndicatorCount() {
    	return indicatorCount;
    }

    /**
     * <p>Returns the subfield code length (position 11).</p>
     *
     * @return <code>int</code> - the subfield code length
     */
    public int getSubfieldCodeLength() {
    	return subfieldCodeLength;
    }

    /**
     * <p>Returns the base address of data (positions 12-16).</p>
     *
     * @return <code>int</code> - the base address of data
     */
    public int getBaseAddressOfData() {
	return baseAddressOfData;
    }

    /**
     * <p>Returns implementation defined values
     * (positions 17-19).</p>
     *
     * @return <code>char</code> - implementation defined values
     */
    public char[] getImplDefined2() {
	return implDefined2;
    }

    /**
     * <p>Returns the entry map (positions 20-23).</p>
     *
     * @return <code>char[]</code> - the entry map
     */
    public char[] getEntryMap() {
	return entryMap;
    }

    /**
     * <p>Creates a leader object from a string object.</p>
     *
     * <p>Indicator count and subfield code length are defaulted to 2 
     * if they are not integer values.</p>
     * @param ldr the leader
     */
    public void unmarshal(final String ldr) throws MarcException {
	try {
	    String s;
	    s = ldr.substring(0, 5);
	    if (isInteger(s))
		setRecordLength(Integer.parseInt(s));
	    else
		setRecordLength(0);
	    setRecordStatus(ldr.charAt(5));
	    setTypeOfRecord(ldr.charAt(6));
	    setImplDefined1(ldr.substring(7, 9).toCharArray());
	    setCharCodingScheme(ldr.charAt(9));
	    s = String.valueOf(ldr.charAt(10));
	    if (isInteger(s))
		setIndicatorCount(Integer.parseInt(s));
	    else
		setIndicatorCount(2);
	    s = String.valueOf(ldr.charAt(10));
	    if (isInteger(s))
		setSubfieldCodeLength(Integer.parseInt(s));
	    else
		setSubfieldCodeLength(2);
	    s = ldr.substring(12, 17);
	    if (isInteger(s))
		setBaseAddressOfData(Integer.parseInt(s));
	    else
		setBaseAddressOfData(0);
	    setImplDefined2(ldr.substring(17, 20).toCharArray());
	    setEntryMap(ldr.substring(20, 24).toCharArray());
	} catch (NumberFormatException e) {
	    throw new MarcException("Unable to parse leader", e);
	}
    }

    /**
     * <p>Returns a String representation of the record label
     * following the MARC structure.</p>
     *
     * @return <code>String</code> - the record label
     */
    public String marshal() {
        return new StringBuffer()
            .append(df.format(recordLength).toString())
            .append(recordStatus)
            .append(typeOfRecord)
            .append(implDefined1)
            .append(charCodingScheme)
            .append(indicatorCount)
            .append(subfieldCodeLength)
            .append(df.format(baseAddressOfData).toString())
            .append(implDefined2)
            .append(entryMap)
            .toString();
    }

    private boolean isInteger(String value) {
	int len = value.length();
	if (len == 0)
	    return false;
	int i = 0;
	do {
	    switch (value.charAt(i)) {
	    case '0':
	    case '1':
	    case '2':
	    case '3':
	    case '4':
	    case '5':
	    case '6':
	    case '7':
	    case '8':
	    case '9':
		break;
	    default:
		return false;
	    }
	} while (++i < len);
	return true;
    }

    /*
     * @see java.lang.Object#clone()
     */
    public Object clone ()
    {
        try
        {
            // Creamos nueva instancia
            Leader instance = (Leader)super.clone ();
            
            // Rellenamos la informaci�n
            instance.recordLength = this.recordLength;
            instance.recordStatus = this.recordStatus;
            instance.typeOfRecord = this.typeOfRecord;
            instance.implDefined1 = (char[])this.implDefined1.clone ();
            instance.charCodingScheme = this.charCodingScheme;
            instance.indicatorCount = this.indicatorCount;
            instance.subfieldCodeLength = this.subfieldCodeLength;
            instance.baseAddressOfData = this.baseAddressOfData;
            instance.implDefined2 = (char[])this.implDefined2.clone ();
            instance.entryMap = (char[])this.entryMap.clone ();
            
            return instance;
        } 
        catch (CloneNotSupportedException e) {
            throw new ConfigException (e);
        }
    }

    @Override
    public String toString ()
    {
        StringBuilder builder = new StringBuilder ();
        builder.append ("Leader [recordLength=").append (recordLength);
        builder.append (",  recordStatus=").append (recordStatus);
        builder.append (",  typeOfRecord=").append (typeOfRecord);
        builder.append (",  implDefined1=").append (Arrays.toString (implDefined1));
        builder.append (",  charCodingScheme=").append (charCodingScheme);
        builder.append (",  indicatorCount=").append (indicatorCount);
        builder.append (",  subfieldCodeLength=").append (subfieldCodeLength);
        builder.append (",  baseAddressOfData=").append (baseAddressOfData);
        builder.append (",  implDefined2=").append (Arrays.toString (implDefined2));
        builder.append (",  entryMap=").append (Arrays.toString (entryMap));
        builder.append (",  df=").append (df);
        builder.append ("]");
        return builder.toString ();
    }
    
    
    
}