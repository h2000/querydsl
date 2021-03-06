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
package com.mysema.query.codegen;

import java.io.IOException;

import javax.inject.Inject;

import net.jcip.annotations.Immutable;

import org.apache.commons.collections15.Transformer;

import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.model.ClassType;
import com.mysema.codegen.model.Constructor;
import com.mysema.codegen.model.Parameter;
import com.mysema.codegen.model.Type;
import com.mysema.codegen.model.TypeCategory;
import com.mysema.codegen.model.Types;
import com.mysema.commons.lang.Assert;
import com.mysema.query.types.ConstructorExpression;
import com.mysema.query.types.Expression;
import com.mysema.query.types.expr.NumberExpression;

/**
 * ProjectionSerializer is a Serializer implementation for DTO types
 *
 * @author tiwe
 *
 */
@Immutable
public final class ProjectionSerializer implements Serializer{

    private final TypeMappings typeMappings;

    @Inject
    public ProjectionSerializer(TypeMappings typeMappings){
        this.typeMappings = Assert.notNull(typeMappings,"typeMappings");
    }

    protected void intro(EntityType model, CodeWriter writer) throws IOException {
        String simpleName = model.getSimpleName();
        Type queryType = typeMappings.getPathType(model, model, false);

        // package
        if (!queryType.getPackageName().isEmpty()) {
            writer.packageDecl(queryType.getPackageName());
        }

        // imports
        writer.imports(Expression.class.getPackage(), NumberExpression.class.getPackage());

        // javadoc
        writer.javadoc(queryType + " is a Querydsl Projection type for " + simpleName);

        // class header
//        writer.suppressWarnings("serial");
        Type superType = new ClassType(TypeCategory.SIMPLE, ConstructorExpression.class, model);
        writer.beginClass(queryType, superType);
        writer.privateStaticFinal(Types.LONG_P, "serialVersionUID", String.valueOf(model.hashCode()));
    }

    protected void outro(EntityType model, CodeWriter writer) throws IOException {
        writer.end();
    }

    @Override
    public void serialize(final EntityType model, SerializerConfig serializerConfig, 
            CodeWriter writer) throws IOException{
        // intro
        intro(model, writer);

        String localName = writer.getRawName(model);
        
        for (Constructor c : model.getConstructors()) {
            // begin
            writer.beginConstructor(c.getParameters(), new Transformer<Parameter,Parameter>() {
                @Override
                public Parameter transform(Parameter p) {
                    return new Parameter(p.getName(), typeMappings.getExprType(p.getType(), 
                            model, false, false, true));
                }
            });

            // body
            writer.beginLine("super(" + localName + ".class");
            writer.append(", new Class[]{");
            boolean first = true;
            
            for (Parameter p : c.getParameters()) {
                if (!first) {
                    writer.append(", ");
                }
                if (p.getType().getPrimitiveName() != null) {
                    writer.append(p.getType().getPrimitiveName()+".class");
                } else {
                    writer.append(writer.getRawName(p.getType()));
                    writer.append(".class");
                }
                first = false;
            }
            writer.append("}");

            for (Parameter p : c.getParameters()) {
                writer.append(", " + p.getName());
            }

            // end
            writer.append(");\n");
            writer.end();
        }

        // outro
        outro(model, writer);
    }

}
