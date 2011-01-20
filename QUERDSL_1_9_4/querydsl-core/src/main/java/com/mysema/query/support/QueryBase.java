/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.support;

import com.mysema.query.QueryModifiers;
import com.mysema.query.types.Expr;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Param;
import com.mysema.query.types.expr.EBoolean;

/**
 * QueryBase provides a stub for Query implementations
 *
 * @author tiwe
 * @version $Id$
 */
public abstract class QueryBase<Q extends QueryBase<Q>> {

    protected final QueryMixin<Q> queryMixin;

    public QueryBase(QueryMixin<Q> queryMixin) {
        this.queryMixin = queryMixin;
    }

    public Q groupBy(Expr<?>... o) {
        return queryMixin.groupBy(o);
    }

    public Q having(EBoolean... o) {
        return queryMixin.having(o);
    }

    public Q orderBy(OrderSpecifier<?>... o) {
        return queryMixin.orderBy(o);
    }

    public Q where(EBoolean... o) {
        return queryMixin.where(o);
    }

    public String toString() {
        return queryMixin.toString();
    }

    public Q limit(long limit) {
        return queryMixin.limit(limit);
    }

    public Q offset(long offset) {
        return queryMixin.offset(offset);
    }

    public Q restrict(QueryModifiers modifiers) {
        return queryMixin.restrict(modifiers);
    }

    public <P> Q set(Param<P> param, P value){
        return queryMixin.set(param, value);
    }

}