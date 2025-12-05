package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.LazyGroup;

@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
public abstract class AbstractLocalization<T extends AbstractEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String language;  // "vi", "en", "de", ...

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyGroup(Fields.OBJECT)
    private T object;
}
