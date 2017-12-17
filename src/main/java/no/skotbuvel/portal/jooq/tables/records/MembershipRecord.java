/*
 * This file is generated by jOOQ.
*/
package no.skotbuvel.portal.jooq.tables.records;


import java.sql.Date;
import java.time.OffsetDateTime;

import javax.annotation.Generated;

import no.skotbuvel.portal.jooq.tables.Membership;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
import org.jooq.impl.UpdatableRecordImpl;


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
public class MembershipRecord extends UpdatableRecordImpl<MembershipRecord> implements Record10<Integer, Integer, Boolean, Integer, Integer, Date, String, OffsetDateTime, String, OffsetDateTime> {

    private static final long serialVersionUID = 1823824605;

    /**
     * Setter for <code>membership.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>membership.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>membership.original_id</code>.
     */
    public void setOriginalId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>membership.original_id</code>.
     */
    public Integer getOriginalId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>membership.active</code>.
     */
    public void setActive(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>membership.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>membership.person_id</code>.
     */
    public void setPersonId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>membership.person_id</code>.
     */
    public Integer getPersonId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>membership.membership_type_id</code>.
     */
    public void setMembershipTypeId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>membership.membership_type_id</code>.
     */
    public Integer getMembershipTypeId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>membership.payment_date</code>.
     */
    public void setPaymentDate(Date value) {
        set(5, value);
    }

    /**
     * Getter for <code>membership.payment_date</code>.
     */
    public Date getPaymentDate() {
        return (Date) get(5);
    }

    /**
     * Setter for <code>membership.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>membership.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(6);
    }

    /**
     * Setter for <code>membership.created_date</code>.
     */
    public void setCreatedDate(OffsetDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>membership.created_date</code>.
     */
    public OffsetDateTime getCreatedDate() {
        return (OffsetDateTime) get(7);
    }

    /**
     * Setter for <code>membership.changed_by</code>.
     */
    public void setChangedBy(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>membership.changed_by</code>.
     */
    public String getChangedBy() {
        return (String) get(8);
    }

    /**
     * Setter for <code>membership.changed_date</code>.
     */
    public void setChangedDate(OffsetDateTime value) {
        set(9, value);
    }

    /**
     * Getter for <code>membership.changed_date</code>.
     */
    public OffsetDateTime getChangedDate() {
        return (OffsetDateTime) get(9);
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
    // Record10 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Boolean, Integer, Integer, Date, String, OffsetDateTime, String, OffsetDateTime> fieldsRow() {
        return (Row10) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row10<Integer, Integer, Boolean, Integer, Integer, Date, String, OffsetDateTime, String, OffsetDateTime> valuesRow() {
        return (Row10) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Membership.MEMBERSHIP.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Membership.MEMBERSHIP.ORIGINAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field3() {
        return Membership.MEMBERSHIP.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Membership.MEMBERSHIP.PERSON_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return Membership.MEMBERSHIP.MEMBERSHIP_TYPE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Date> field6() {
        return Membership.MEMBERSHIP.PAYMENT_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Membership.MEMBERSHIP.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field8() {
        return Membership.MEMBERSHIP.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return Membership.MEMBERSHIP.CHANGED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field10() {
        return Membership.MEMBERSHIP.CHANGED_DATE;
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
    public Integer component4() {
        return getPersonId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component5() {
        return getMembershipTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date component6() {
        return getPaymentDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component8() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component9() {
        return getChangedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component10() {
        return getChangedDate();
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
    public Integer value4() {
        return getPersonId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getMembershipTypeId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date value6() {
        return getPaymentDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value8() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getChangedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value10() {
        return getChangedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value2(Integer value) {
        setOriginalId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value3(Boolean value) {
        setActive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value4(Integer value) {
        setPersonId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value5(Integer value) {
        setMembershipTypeId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value6(Date value) {
        setPaymentDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value7(String value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value8(OffsetDateTime value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value9(String value) {
        setChangedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord value10(OffsetDateTime value) {
        setChangedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MembershipRecord values(Integer value1, Integer value2, Boolean value3, Integer value4, Integer value5, Date value6, String value7, OffsetDateTime value8, String value9, OffsetDateTime value10) {
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
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MembershipRecord
     */
    public MembershipRecord() {
        super(Membership.MEMBERSHIP);
    }

    /**
     * Create a detached, initialised MembershipRecord
     */
    public MembershipRecord(Integer id, Integer originalId, Boolean active, Integer personId, Integer membershipTypeId, Date paymentDate, String createdBy, OffsetDateTime createdDate, String changedBy, OffsetDateTime changedDate) {
        super(Membership.MEMBERSHIP);

        set(0, id);
        set(1, originalId);
        set(2, active);
        set(3, personId);
        set(4, membershipTypeId);
        set(5, paymentDate);
        set(6, createdBy);
        set(7, createdDate);
        set(8, changedBy);
        set(9, changedDate);
    }
}
