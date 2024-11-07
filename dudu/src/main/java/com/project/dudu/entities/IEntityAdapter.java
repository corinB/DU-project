package com.project.dudu.entities;

public interface IEntityAdapter<T> {
    void setCreateAt(T o);
    void setUpdateAt(T o);

}
