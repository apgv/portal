/*
 * This file is generated by jOOQ.
*/
package no.skotbuvel.portal.jooq.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import no.skotbuvel.portal.jooq.DefaultSchema;
import no.skotbuvel.portal.jooq.Indexes;
import no.skotbuvel.portal.jooq.Keys;
import no.skotbuvel.portal.jooq.tables.records.SubjectRoleRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class SubjectRole extends TableImpl<SubjectRoleRecord> {

    private static final long serialVersionUID = -342539419;

    /**
     * The reference instance of <code>subject_role</code>
     */
    public static final SubjectRole SUBJECT_ROLE = new SubjectRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubjectRoleRecord> getRecordType() {
        return SubjectRoleRecord.class;
    }

    /**
     * The column <code>subject_role.user_id</code>.
     */
    public final TableField<SubjectRoleRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>subject_role.role_id</code>.
     */
    public final TableField<SubjectRoleRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>subject_role</code> table reference
     */
    public SubjectRole() {
        this(DSL.name("subject_role"), null);
    }

    /**
     * Create an aliased <code>subject_role</code> table reference
     */
    public SubjectRole(String alias) {
        this(DSL.name(alias), SUBJECT_ROLE);
    }

    /**
     * Create an aliased <code>subject_role</code> table reference
     */
    public SubjectRole(Name alias) {
        this(alias, SUBJECT_ROLE);
    }

    private SubjectRole(Name alias, Table<SubjectRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private SubjectRole(Name alias, Table<SubjectRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SUBJECT_ROLE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SubjectRoleRecord> getPrimaryKey() {
        return Keys.SUBJECT_ROLE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SubjectRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<SubjectRoleRecord>>asList(Keys.SUBJECT_ROLE_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<SubjectRoleRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<SubjectRoleRecord, ?>>asList(Keys.SUBJECT_ROLE__SUBJECT_ROLE_USER_ID_FKEY, Keys.SUBJECT_ROLE__SUBJECT_ROLE_ROLE_ID_FKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectRole as(String alias) {
        return new SubjectRole(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SubjectRole as(Name alias) {
        return new SubjectRole(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SubjectRole rename(String name) {
        return new SubjectRole(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubjectRole rename(Name name) {
        return new SubjectRole(name, null);
    }
}
