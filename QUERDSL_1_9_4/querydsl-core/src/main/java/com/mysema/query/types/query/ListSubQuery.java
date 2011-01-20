/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.types.query;

import java.util.List;

import com.mysema.query.QueryMetadata;
import com.mysema.query.types.Expr;
import com.mysema.query.types.Operator;
import com.mysema.query.types.Ops;
import com.mysema.query.types.SubQueryExpression;
import com.mysema.query.types.Visitor;
import com.mysema.query.types.expr.EBoolean;
import com.mysema.query.types.expr.ECollectionBase;
import com.mysema.query.types.expr.OSimple;

/**
 * List result subquery
 *
 * @author tiwe
 *
 * @param <A>
 */
public final class ListSubQuery<A> extends ECollectionBase<List<A>,A> implements SubQueryExpression<List<A>>{

    private static final long serialVersionUID = 3399354334765602960L;

    private final Class<A> elementType;

    private final SubQueryMixin<List<A>> subQueryMixin;

    @SuppressWarnings("unchecked")
    public ListSubQuery(Class<A> elementType, QueryMetadata md) {
        super((Class)List.class);
        this.elementType = elementType;
        this.subQueryMixin = new SubQueryMixin<List<A>>(this,md);
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
       return subQueryMixin.equals(o);
    }

    @Override
    public EBoolean exists() {
        return subQueryMixin.exists();
    }

    public Class<A> getElementType() {
        return elementType;
    }

    @Override
    public QueryMetadata getMetadata() {
        return subQueryMixin.getMetadata();
    }

    @Override
    public int hashCode(){
        return subQueryMixin.hashCode();
    }

    @Override
    public EBoolean notExists() {
        return subQueryMixin.notExists();
    }

    @SuppressWarnings("unchecked")
    public Expr<?> as(Expr<?> alias) {
        return OSimple.create(alias.getType(),(Operator)Ops.ALIAS, this, alias);
    }

}