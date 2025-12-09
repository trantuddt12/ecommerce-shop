package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.LazyGroup;

@MappedSuperclass
@Getter
@Setter
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractLocalization<T extends AbstractEntity> {

    protected AbstractLocalization (String language) {
        this.setLanguage(language);
    }

    protected AbstractLocalization (String language, T object) {
        this.setLanguage(language);
        this.setObject(object);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5)
    private String language;  // "vi", "en", "de", ...

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyGroup(Fields.OBJECT)
    private T object;
}
