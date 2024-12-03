package com.project.dudu.entities.util;

public interface IEntityAdapter<T> {
    void setCreateAt(T o);
    void setUpdateAt(T o);

}
