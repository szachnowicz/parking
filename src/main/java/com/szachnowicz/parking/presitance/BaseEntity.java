package com.szachnowicz.parking.presitance;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue
    @Getter
    protected Long id;
    private String uuid = UUID.randomUUID().toString();

    @Override
    public final int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public final boolean equals(Object that) {
        return this == that || that instanceof BaseEntity
                && Objects.equals(uuid, ((BaseEntity) that).uuid);
    }
}
