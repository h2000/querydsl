package com.mysema.query.types;

/**
 * @author tiwe
 *
 * @param <T>
 */
public interface ParamExpression<T> extends Expression<T>{
    
    /**
     * @return
     */
    String getName();

    /**
     * @return
     */
    boolean isAnon();

    /**
     * @return
     */
    String getNotSetMessage();

}