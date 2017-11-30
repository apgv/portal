/*
 * This file is generated by jOOQ.
*/
package org.jooq.no.skotbuvel.portal.tables.records;


import java.time.OffsetDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.no.skotbuvel.portal.tables.Person;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PersonRecord extends UpdatableRecordImpl<PersonRecord> implements Record11<Integer, Integer, Boolean, String, String, String, String, String, OffsetDateTime, String, OffsetDateTime> {

    private static final long serialVersionUID = 1332304854;

    /**
     * Setter for <code>person.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>person.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>person.original_id</code>.
     */
    public void setOriginalId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>person.original_id</code>.
     */
    public Integer getOriginalId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>person.active</code>.
     */
    public void setActive(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>person.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>person.full_name</code>.
     */
    public void setFullName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>person.full_name</code>.
     */
    public String getFullName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>person.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>person.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>person.phone</code>.
     */
    public void setPhone(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>person.phone</code>.
     */
    public String getPhone() {
        return (String) get(5);
    }

    /**
     * Setter for <code>person.address</code>.
     */
    public void setAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>person.address</code>.
     */
    public String getAddress() {
        return (String) get(6);
    }

    /**
     * Setter for <code>person.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>person.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(7);
    }

    /**
     * Setter for <code>person.created_date</code>.
     */
    public void setCreatedDate(OffsetDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>person.created_date</code>.
     */
    public OffsetDateTime getCreatedDate() {
        return (OffsetDateTime) get(8);
    }

    /**
     * Setter for <code>person.deleted_by</code>.
     */
    public void setDeletedBy(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>person.deleted_by</code>.
     */
    public String getDeletedBy() {
        return (String) get(9);
    }

    /**
     * Setter for <code>person.deleted_date</code>.
     */
    public void setDeletedDate(OffsetDateTime value) {
        set(10, value);
    }

    /**
     * Getter for <code>person.deleted_date</code>.
     */
    public OffsetDateTime getDeletedDate() {
        return (OffsetDateTime) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Boolean, String, String, String, String, String, OffsetDateTime, String, OffsetDateTime> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, Integer, Boolean, String, String, String, String, String, OffsetDateTime, String, OffsetDateTime> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Person.PERSON.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Person.PERSON.ORIGINAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field3() {
        return Person.PERSON.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Person.PERSON.FULL_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Person.PERSON.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Person.PERSON.PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Person.PERSON.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Person.PERSON.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field9() {
        return Person.PERSON.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return Person.PERSON.DELETED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field11() {
        return Person.PERSON.DELETED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component2() {
        return getOriginalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean component3() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getFullName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component9() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component10() {
        return getDeletedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component11() {
        return getDeletedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getOriginalId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value3() {
        return getActive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getFullName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value9() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getDeletedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value11() {
        return getDeletedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value2(Integer value) {
        setOriginalId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value3(Boolean value) {
        setActive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value4(String value) {
        setFullName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value5(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value6(String value) {
        setPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value7(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value8(String value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value9(OffsetDateTime value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value10(String value) {
        setDeletedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord value11(OffsetDateTime value) {
        setDeletedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonRecord values(Integer value1, Integer value2, Boolean value3, String value4, String value5, String value6, String value7, String value8, OffsetDateTime value9, String value10, OffsetDateTime value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PersonRecord
     */
    public PersonRecord() {
        super(Person.PERSON);
    }

    /**
     * Create a detached, initialised PersonRecord
     */
    public PersonRecord(Integer id, Integer originalId, Boolean active, String fullName, String email, String phone, String address, String createdBy, OffsetDateTime createdDate, String deletedBy, OffsetDateTime deletedDate) {
        super(Person.PERSON);

        set(0, id);
        set(1, originalId);
        set(2, active);
        set(3, fullName);
        set(4, email);
        set(5, phone);
        set(6, address);
        set(7, createdBy);
        set(8, createdDate);
        set(9, deletedBy);
        set(10, deletedDate);
    }
}
