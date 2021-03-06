/*
 * Copyright 2011, Mysema Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mysema.query.sql;

import com.mysema.query.types.Ops;

/**
 * PostgresTemplates is an SQL dialect for Postgres
 *
 * tested with Postgres 8.4
 *
 * @author tiwe
 *
 */
public class PostgresTemplates extends SQLTemplates {

    public PostgresTemplates() {
        this('\\',false);
    }
    
    public PostgresTemplates(boolean quote) {
        this('\\',quote);
    }

    public PostgresTemplates(char escape, boolean quote) {
        super("\"", escape, quote);
        // type mappings
        addClass2TypeMappings("numeric(3,0)", Byte.class);
        addClass2TypeMappings("double precision", Double.class);

        // String
        add(Ops.CONCAT, "{0} || {1}");
        add(Ops.MATCHES, "{0} ~ {1}");
        add(Ops.INDEX_OF, "strpos({0},{1})-1");
        add(Ops.INDEX_OF_2ARGS, "strpos({0},{1})-1"); //FIXME

        // like without escape
        if (escape == '\\') {
            add(Ops.LIKE, "{0} like {1}");
            add(Ops.ENDS_WITH, "{0} like {%1}");
            add(Ops.ENDS_WITH_IC, "{0l} like {%%1}");
            add(Ops.STARTS_WITH, "{0} like {1%}");
            add(Ops.STARTS_WITH_IC, "{0l} like {1%%}");
            add(Ops.STRING_CONTAINS, "{0} like {%1%}");
            add(Ops.STRING_CONTAINS_IC, "{0l} like {%%1%%}");    
        }        
        
        // Number
        add(Ops.MathOps.RANDOM, "random()");
        add(Ops.MathOps.LOG, "ln({0})");
        add(Ops.MathOps.LOG10, "log({0})");

        // Date / time
        add(Ops.DateTimeOps.YEAR, "extract(year from {0})");
        add(Ops.DateTimeOps.YEAR_MONTH, "extract(year from {0}) * 100 + extract(month from {0})");
        add(Ops.DateTimeOps.MONTH, "extract(month from {0})");
        add(Ops.DateTimeOps.WEEK, "extract(week from {0})");
        add(Ops.DateTimeOps.DAY_OF_MONTH, "extract(day from {0})");
        add(Ops.DateTimeOps.DAY_OF_WEEK, "extract(dow from {0}) + 1");
        add(Ops.DateTimeOps.DAY_OF_YEAR, "extract(doy from {0})");
        add(Ops.DateTimeOps.HOUR, "extract(hour from {0})");
        add(Ops.DateTimeOps.MINUTE, "extract(minute from {0})");
        add(Ops.DateTimeOps.SECOND, "extract(second from {0})");

    }

}
