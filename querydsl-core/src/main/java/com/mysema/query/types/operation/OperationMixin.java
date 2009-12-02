/*
 * Copyright (c) 2009 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.types.operation;

import java.util.Collections;
import java.util.List;

import com.mysema.query.types.expr.Expr;

/**
 * @author tiwe
 *
 * @param <OP>
 * @param <RT>
 */
public class OperationMixin<OP, RT> implements Operation<OP, RT> {

    private final List<Expr<?>> args;
    
    private final Operator<OP> operator;
    
    private final Expr<RT> self;
    
    public OperationMixin(Expr<RT> self, Operator<OP> operator, List<Expr<?>> args){
        this.self = self;
        this.operator = operator;
        this.args = Collections.unmodifiableList(args);
    }
    
    @Override
    public Expr<RT> asExpr() {
        return self;
    }

    @Override
    public Expr<?> getArg(int i) {
        return args.get(i);
    }

    @Override
    public List<Expr<?>> getArgs() {
        return args;
    }
    
    @Override
    public Operator<OP> getOperator() {
        return operator;
    }

    @Override
    public Class<? extends RT> getType() {
        return self.getType();
    }

}