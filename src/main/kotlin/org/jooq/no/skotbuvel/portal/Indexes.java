/*
 * This file is generated by jOOQ.
*/
package org.jooq.no.skotbuvel.portal;


import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.AbstractKeys;
import org.jooq.no.skotbuvel.portal.tables.member;
import org.jooq.no.skotbuvel.portal.tables.membership;
import org.jooq.no.skotbuvel.portal.tables.person;


/**
 * A class modelling indexes of tables of the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index member_pkey = Indexes0.member_pkey;
    public static final Index membership_pkey = Indexes0.membership_pkey;
    public static final Index person_pkey = Indexes0.person_pkey;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 extends AbstractKeys {
        public static Index member_pkey = createIndex("member_pkey", member.member, new OrderField[] { member.member.id }, true);
        public static Index membership_pkey = createIndex("membership_pkey", membership.membership, new OrderField[] { membership.membership.id }, true);
        public static Index person_pkey = createIndex("person_pkey", person.person, new OrderField[] { person.person.id }, true);
    }
}