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

import javax.inject.Inject;
import javax.inject.Named;

import com.mysema.codegen.model.SimpleType;
import com.mysema.codegen.model.Type;
import com.mysema.query.codegen.QueryTypeFactory;

public final class SQLQueryTypeFactory implements QueryTypeFactory{

    private final int stripStart, stripEnd;

    private final String prefix, suffix;
    
    @Inject
    public SQLQueryTypeFactory(
            @Named(SQLCodegenModule.BEAN_PREFIX) String beanPrefix,
            @Named(SQLCodegenModule.BEAN_SUFFIX) String beanSuffix,
            @Named(SQLCodegenModule.PREFIX) String prefix,
            @Named(SQLCodegenModule.SUFFIX) String suffix) {
        this.stripStart = beanPrefix.length();
        this.stripEnd = beanSuffix.length();
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public Type create(Type type) {
        String packageName = type.getPackageName();
        String simpleName = type.getSimpleName();        
        simpleName = prefix + simpleName.substring(stripStart, simpleName.length()-stripEnd) + suffix;
        return new SimpleType(packageName + "." + simpleName, packageName, simpleName);
    }


}
