package com.Pubrunda.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class CreationEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {

    private final T object;

    public CreationEvent(Object source, T object) {
        super(source);
        this.object = object;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(object));
    }

    public T getCreatedObject() {
        return object;
    }

}