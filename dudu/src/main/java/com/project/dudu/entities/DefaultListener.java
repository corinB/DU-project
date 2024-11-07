package com.project.dudu.entities;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class DefaultListener {
    @PrePersist
    public void perPersist(Object o){
        if (o instanceof IEntityAdapter){
            ((IEntityAdapter) o).setCreateAt(LocalDateTime.now());
            ((IEntityAdapter) o).setUpdateAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o){
        if (o instanceof IEntityAdapter){
            ((IEntityAdapter) o).setUpdateAt(LocalDateTime.now());
        }

    }
}
