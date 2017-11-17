/*
 * This file is generated by jOOQ.
*/
package org.jooq.no.skotbuvel.portal.tables.records;


import java.time.OffsetDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record8;
import org.jooq.Row8;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.no.skotbuvel.portal.tables.Member;


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
public class MemberRecord extends UpdatableRecordImpl<MemberRecord> implements Record8<Integer, Integer, Boolean, Integer, OffsetDateTime, Integer, String, OffsetDateTime> {

    private static final long serialVersionUID = -1721344631;

    /**
     * Setter for <code>member.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>member.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>member.original_id</code>.
     */
    public void setOriginalId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>member.original_id</code>.
     */
    public Integer getOriginalId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>member.active</code>.
     */
    public void setActive(Boolean value) {
        set(2, value);
    }

    /**
     * Getter for <code>member.active</code>.
     */
    public Boolean getActive() {
        return (Boolean) get(2);
    }

    /**
     * Setter for <code>member.person_id</code>.
     */
    public void setPersonId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>member.person_id</code>.
     */
    public Integer getPersonId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>member.payment_date</code>.
     */
    public void setPaymentDate(OffsetDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>member.payment_date</code>.
     */
    public OffsetDateTime getPaymentDate() {
        return (OffsetDateTime) get(4);
    }

    /**
     * Setter for <code>member.membership_id</code>.
     */
    public void setMembershipId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>member.membership_id</code>.
     */
    public Integer getMembershipId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>member.created_by</code>.
     */
    public void setCreatedBy(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>member.created_by</code>.
     */
    public String getCreatedBy() {
        return (String) get(6);
    }

    /**
     * Setter for <code>member.created_date</code>.
     */
    public void setCreatedDate(OffsetDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>member.created_date</code>.
     */
    public OffsetDateTime getCreatedDate() {
        return (OffsetDateTime) get(7);
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
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Boolean, Integer, OffsetDateTime, Integer, String, OffsetDateTime> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Integer, Integer, Boolean, Integer, OffsetDateTime, Integer, String, OffsetDateTime> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Member.MEMBER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return Member.MEMBER.ORIGINAL_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field3() {
        return Member.MEMBER.ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Member.MEMBER.PERSON_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field5() {
        return Member.MEMBER.PAYMENT_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field6() {
        return Member.MEMBER.MEMBERSHIP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Member.MEMBER.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field8() {
        return Member.MEMBER.CREATED_DATE;
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
    public OffsetDateTime component5() {
        return getPaymentDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component6() {
        return getMembershipId();
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
    public OffsetDateTime value5() {
        return getPaymentDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value6() {
        return getMembershipId();
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
    public MemberRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value2(Integer value) {
        setOriginalId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value3(Boolean value) {
        setActive(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value4(Integer value) {
        setPersonId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value5(OffsetDateTime value) {
        setPaymentDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value6(Integer value) {
        setMembershipId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value7(String value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord value8(OffsetDateTime value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberRecord values(Integer value1, Integer value2, Boolean value3, Integer value4, OffsetDateTime value5, Integer value6, String value7, OffsetDateTime value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MemberRecord
     */
    public MemberRecord() {
        super(Member.MEMBER);
    }

    /**
     * Create a detached, initialised MemberRecord
     */
    public MemberRecord(Integer id, Integer originalId, Boolean active, Integer personId, OffsetDateTime paymentDate, Integer membershipId, String createdBy, OffsetDateTime createdDate) {
        super(Member.MEMBER);

        set(0, id);
        set(1, originalId);
        set(2, active);
        set(3, personId);
        set(4, paymentDate);
        set(5, membershipId);
        set(6, createdBy);
        set(7, createdDate);
    }
}