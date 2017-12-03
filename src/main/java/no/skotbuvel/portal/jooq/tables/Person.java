/*
 * This file is generated by jOOQ.
*/
package no.skotbuvel.portal.jooq.tables;


import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import no.skotbuvel.portal.jooq.DefaultSchema;
import no.skotbuvel.portal.jooq.Indexes;
import no.skotbuvel.portal.jooq.Keys;
import no.skotbuvel.portal.jooq.tables.records.PersonRecord;

import org.jooq.Field;
import org.jooq.Identity;
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
public class Person extends TableImpl<PersonRecord> {

    private static final long serialVersionUID = -1674912631;

    /**
     * The reference instance of <code>person</code>
     */
    public static final Person PERSON = new Person();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PersonRecord> getRecordType() {
        return PersonRecord.class;
    }

    /**
     * The column <code>person.id</code>.
     */
    public final TableField<PersonRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('person_id_seq'::regclass)", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>person.original_id</code>.
     */
    public final TableField<PersonRecord, Integer> ORIGINAL_ID = createField("original_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>person.active</code>.
     */
    public final TableField<PersonRecord, Boolean> ACTIVE = createField("active", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>person.full_name</code>.
     */
    public final TableField<PersonRecord, String> FULL_NAME = createField("full_name", org.jooq.impl.SQLDataType.VARCHAR(54).nullable(false), this, "");

    /**
     * The column <code>person.email</code>.
     */
    public final TableField<PersonRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(40), this, "");

    /**
     * The column <code>person.phone</code>.
     */
    public final TableField<PersonRecord, String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR(15), this, "");

    /**
     * The column <code>person.address</code>.
     */
    public final TableField<PersonRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>person.created_by</code>.
     */
    public final TableField<PersonRecord, String> CREATED_BY = createField("created_by", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>person.created_date</code>.
     */
    public final TableField<PersonRecord, OffsetDateTime> CREATED_DATE = createField("created_date", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE.nullable(false), this, "");

    /**
     * The column <code>person.deleted_by</code>.
     */
    public final TableField<PersonRecord, String> DELETED_BY = createField("deleted_by", org.jooq.impl.SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>person.deleted_date</code>.
     */
    public final TableField<PersonRecord, OffsetDateTime> DELETED_DATE = createField("deleted_date", org.jooq.impl.SQLDataType.TIMESTAMPWITHTIMEZONE, this, "");

    /**
     * Create a <code>person</code> table reference
     */
    public Person() {
        this(DSL.name("person"), null);
    }

    /**
     * Create an aliased <code>person</code> table reference
     */
    public Person(String alias) {
        this(DSL.name(alias), PERSON);
    }

    /**
     * Create an aliased <code>person</code> table reference
     */
    public Person(Name alias) {
        this(alias, PERSON);
    }

    private Person(Name alias, Table<PersonRecord> aliased) {
        this(alias, aliased, null);
    }

    private Person(Name alias, Table<PersonRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PERSON_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PersonRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PERSON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PersonRecord> getPrimaryKey() {
        return Keys.PERSON_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PersonRecord>> getKeys() {
        return Arrays.<UniqueKey<PersonRecord>>asList(Keys.PERSON_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person as(String alias) {
        return new Person(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Person as(Name alias) {
        return new Person(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Person rename(String name) {
        return new Person(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Person rename(Name name) {
        return new Person(name, null);
    }
}