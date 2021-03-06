// $Id: DataField.java,v 1.6 2003/03/31 19:55:26 ceyates Exp $
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>
 * <code>DataField</code> defines behaviour for a data field
 * (tag 010-999).
 * </p>
 *
 * <p>
 * Data fields are variable fields identified by tags beginning
 * with ASCII numeric values other than two zero's. Data fields
 * contain indicators, subfield codes, data and a field terminator.
 * The structure of a data field according to the MARC standard is
 * as follows:
 * </p>
 * 
 * <pre>
 * INDICATOR_1  INDICATOR_2  DELIMITER  DATA_ELEMENT_IDENTIFIER_1
 *   DATA_ELEMENT_1  ...  DELIMITER  DATA_ELEMENT_IDENTIFIER_n
 *     DATA_ELEMENT_n  FT
 * </pre>
 * <p>
 * This structure is returned by the {@link #marshal()}
 * method.
 * </p>
 *
 * @author <a href="mailto:mail@bpeters.com">Bas Peters</a>
 * @version $Revision: 1.6 $
 *
 */
public class DataField
    extends VariableField
    implements Serializable, Cloneable
{

    private static final long serialVersionUID = 1L;

    /** The first indicator value. */
    private char ind1;

    /** The second indicator value. */
    private char ind2;

    /** A collection of data elements. */
    private ArrayList<Subfield> list;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public DataField()
    {
        super();
        this.list = new ArrayList<>();
    }

    /**
     * <p>
     * Creates a new <code>DataField</code> instance and
     * registers the tag name.
     * </p>
     *
     * @param tag the tag name
     */
    public DataField(String tag)
    {
        super(tag);
        this.list = new ArrayList<>();
    }

    /**
     * <p>
     * Creates a new <code>DataField</code> instance and
     * registers the tag name and the indicator values.
     * </p>
     *
     * @param tag the tag name
     * @param ind1 the first indicator
     * @param ind2 the second indicator
     */
    public DataField(String tag, char ind1, char ind2)
    {
        super(tag);
        setIndicator1(ind1);
        setIndicator2(ind2);
        this.list = new ArrayList<>();
    }

    /**
     * <p>
     * Creates a new <code>DataField</code> instance and
     * registers the id, the tag name and the indicator values.
     * </p>
     *
     * @param tag the tag name
     * @param ind1 the first indicator
     * @param ind2 the second indicator
     * @param id the id for the field
     */
    public DataField(String tag, char ind1, char ind2, Long id)
    {
        this(tag, ind1, ind2);
        this.setId(id);
    }

    /**
     * <p>
     * Registers the tag.
     * </p>
     *
     * @param tag the tag name
     * @throws IllegalTagException when the tag is not a valid
     *         data field identifier
     */
    @Override
    public void setTag(String tag)
    {
        if (Tag.isDataField(tag)) {
            super.setTag(tag);
        } else {
            throw new IllegalTagException(tag, "not a data field identifier");
        }
    }

    /**
     * <p>
     * Returns the tag name.
     * </p>
     *
     * @return {@link String} - the tag name
     */
    @Override
    public String getTag()
    {
        return super.getTag();
    }

    /**
     * <p>
     * Registers the first indicator value.
     * </p>
     *
     * @param ind1 the first indicator
     * @throws IllegalIndicatorException when the indicator value is invalid
     */
    public void setIndicator1(char ind1)
    {
        Verifier.checkDataElement(ind1);
        this.ind1 = ind1;
    }

    /**
     * <p>
     * Registers the second indicator value.
     * </p>
     *
     * @param ind2 the second indicator
     * @throws IllegalIndicatorException when the indicator value is invalid
     */
    public void setIndicator2(char ind2)
    {
        Verifier.checkDataElement(ind2);
        this.ind2 = ind2;
    }

    /**
     * <p>
     * Adds a new <code>subfield</code> instance to
     * the collection of data elements.
     * </p>
     *
     * @param subfield the data element
     * @see Subfield
     */
    public void add(Subfield subfield)
    {
        list.add(subfield);
    }

    /**
     * <p>
     * Returns the first indicator.
     * </p>
     *
     * @return <code>char</code> - the first indicator
     */
    public char getIndicator1()
    {
        return ind1;
    }

    /**
     * <p>
     * Returns the second indicator.
     * </p>
     *
     * @return <code>char</code> - the second indicator
     */
    public char getIndicator2()
    {
        return ind2;
    }

    /**
     * <p>
     * Returns the collection of data elements.
     * </p>
     *
     * @return {@link List} - the data element collection
     * @see Subfield
     */
    public List<Subfield> getSubfieldList()
    {
        return list;
    }

    /**
     * <p>
     * Returns the subfield for a given data element identifier.
     * </p>
     *
     * @param code the data element identifier
     * @return Subfield the data element
     * @see Subfield
     */
    public Subfield getSubfield(char code)
    {
        for (Iterator<Subfield> i = list.iterator(); i.hasNext();) {
            Subfield sf = i.next();
            if (sf.getCode() == code) return sf;
        }
        return null;
    }

    /**
     * <p>
     * Returns true if there is a subfield with the given identifier.
     * </p>
     *
     * @param code the data element identifier
     * @return true if the data element exists, false if not
     */
    public boolean hasSubfield(char code)
    {
        for (Iterator<Subfield> i = list.iterator(); i.hasNext();) {
            Subfield sf = i.next();
            if (sf.getCode() == code) return true;
        }
        return false;
    }

    /**
     * <p>
     * Sets the collection of data elements.
     * </p>
     *
     * <p>
     * A collection of data elements is a {@link List} object
     * with null or more {@link Subfield} objects.
     * </p>
     *
     * <p>
     * <b>Note:</b> this method replaces the current {@link List}
     * of subfields with the subfields in the new {@link List}.
     * </p>
     *
     * @param newList the new data element collection
     */
    public void setSubfieldList(List<Subfield> newList)
    {
        if (newList == null) {
            list = new ArrayList<Subfield>();
            return;
        }
        list = new ArrayList<Subfield>();
        for (Iterator<Subfield> i = newList.iterator(); i.hasNext();) {
            Object obj = i.next();
            if (obj instanceof Subfield) {
                add((Subfield)obj);
            } else {
                throw new IllegalAddException(obj.getClass().getName(),
                    "a collection of subfields can only contain " + "Subfield objects.");
            }
        }
    }

    /**
     * <p>
     * Returns a <code>String</code> representation for a data field
     * following the structure of a MARC data field.
     * </p>
     *
     * @return <code>String</code> - the data field
     */
    public String marshal()
    {
        StringBuffer dataField = new StringBuffer().append(ind1).append(ind2);
        Iterator<Subfield> iterator = list.iterator();
        while (iterator.hasNext()) {
            Subfield subfield = (Subfield)iterator.next();
            dataField.append(subfield.marshal());
        }
        dataField.append(FT);
        return dataField.toString();
    }

    /**
     * <p>
     * Returns the length of the serialized form of
     * the current data field.
     * </p>
     *
     * @return <code>int</code> - the data field length
     */
    public int getLength()
    {
        return this.marshal().length();
    }

    /*
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        // Creamos una nueva instancia
        DataField instance = new DataField(this.getTag(), this.ind1, this.ind2, DataField.EMPTY_ID);

        // Recorremos la lista de subcampos y clonamos cada uno de ellos
        if (this.list != null) {
            ArrayList<Subfield> newList = new ArrayList<>();
            for (Iterator<Subfield> it = this.list.iterator(); it.hasNext();)
                newList.add((Subfield)it.next().clone());
            instance.setSubfieldList(newList);
        }

        // Devolvemos la nueva instancia
        return instance;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        DataField that = (DataField)obj;
        return new EqualsBuilder()
            .append(this.getTag(), that.getTag())
            .append(this.getId(), that.getId())
            .append(this.ind1, that.ind1)
            .append(this.ind2, that.ind2)
            .append(this.list, that.list) // ArrayList.equals hace un deep equals
            .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
            .append(this.getTag())
            .append(this.getId())
            .append(this.ind1)
            .append(this.ind2)
            .append(this.list)
            .toHashCode();
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        sb
            .append("\n    DATAFIELD    [  tag: ")
            .append(getTag())
            .append(", Ind1:")
            .append(getIndicator1())
            .append(", Ind2:")
            .append(getIndicator2())
            .append(this.getId() != null ? (", id: ") + this.getId() : "")
            .append(",\n        Elements: ")
            .append(Arrays.toString(list.toArray()))
            .append(" ] ");
        return sb.toString();
    }

}
