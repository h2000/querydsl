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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mysema.codegen.model.Types;
import com.mysema.query.codegen.EntityType;

public class OriginalNamingStrategyTest {

    private NamingStrategy namingStrategy = new OriginalNamingStrategy();
    
    private EntityType entityModel = new EntityType(Types.OBJECT);
    
    @Test
    public void GetClassName() {
        assertEquals("user_data", namingStrategy.getClassName("user_data"));
        assertEquals("u", namingStrategy.getClassName("u"));
        assertEquals("us",namingStrategy.getClassName("us"));
        assertEquals("u_", namingStrategy.getClassName("u_"));
        assertEquals("us_",namingStrategy.getClassName("us_"));
    }
    
    @Test
    public void GetPropertyName() {
        assertEquals("while_col", namingStrategy.getPropertyName("while", entityModel));
        assertEquals("name", namingStrategy.getPropertyName("name", entityModel));
        assertEquals("user_id", namingStrategy.getPropertyName("user_id", entityModel));
        assertEquals("accountEvent_id", namingStrategy.getPropertyName("accountEvent_id", entityModel));
    }

    @Test
    public void GetPropertyNameForInverseForeignKey(){
        assertEquals("_fk_superior", namingStrategy.getPropertyNameForInverseForeignKey("fk_superior", entityModel));
    }
    
    @Test
    public void GetPropertyNameForForeignKey(){
        assertEquals("fk_superior", namingStrategy.getPropertyNameForForeignKey("fk_superior", entityModel));
        assertEquals("FK_SUPERIOR", namingStrategy.getPropertyNameForForeignKey("FK_SUPERIOR", entityModel));        
    }
    
    @Test
    public void GetDefaultVariableName(){
        assertEquals("object", namingStrategy.getDefaultVariableName(entityModel));
    }
}
