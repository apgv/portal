/*
 * This file is generated by jOOQ.
*/
package org.jooq.no.skotbuvel.portal;


import javax.annotation.Generated;

import org.jooq.no.skotbuvel.portal.tables.member;
import org.jooq.no.skotbuvel.portal.tables.membership;
import org.jooq.no.skotbuvel.portal.tables.person;


/**
 * Convenience access to all tables in 
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>member</code>.
     */
    public static final member member = org.jooq.no.skotbuvel.portal.tables.member.member;

    /**
     * The table <code>membership</code>.
     */
    public static final membership membership = org.jooq.no.skotbuvel.portal.tables.membership.membership;

    /**
     * The table <code>person</code>.
     */
    public static final person person = org.jooq.no.skotbuvel.portal.tables.person.person;
}