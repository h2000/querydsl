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
package com.mysema.query.sql.ddl;

import java.util.List;

import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.support.ForeignKeyData;

/**
 * ForeignKeyBuilder is part of the fluent interface of CreateTableClause
 * 
 * @author tiwe
 *
 */
@Deprecated
public class ForeignKeyBuilder {

    private final List<ForeignKeyData> foreignKeys;
    
    private final CreateTableClause clause;
    
    private final String name;
    
    private final String[] foreignColumns;

    private final SQLTemplates templates;
    
    public ForeignKeyBuilder(CreateTableClause clause, SQLTemplates templates, 
            List<ForeignKeyData> foreignKeys, String name, String[] columns) {
        this.clause = clause;
        this.templates = templates;
        this.foreignKeys = foreignKeys;
        this.name = name;
        this.foreignColumns = columns;
    }

    public CreateTableClause references(String table, String... parentColumns) {
        ForeignKeyData foreignKey = new ForeignKeyData(name, null, templates.quoteIdentifier(table), null);
        for (int i = 0; i < parentColumns.length; i++) {
            foreignKey.add(
                templates.quoteIdentifier(foreignColumns[i]), 
                templates.quoteIdentifier(parentColumns[i]));
        }
        foreignKeys.add(foreignKey);
        return clause;
    }

}
