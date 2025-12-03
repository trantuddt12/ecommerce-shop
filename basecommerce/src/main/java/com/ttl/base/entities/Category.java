package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "categories",
	uniqueConstraints = {
			@UniqueConstraint(name = Category.UK_CATEGORY_SLUG, columnNames = Category.Fields.SLUG)
	}
)
@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
@ToString(exclude = {"parent", "categories"})
@FieldNameConstants
public class Category extends AbstractEntity {

    public static final String UK_CATEGORY_SLUG = "uk_category_slug";

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;
    
    @Column(length=100 , nullable = false) 
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Fields.PARENT)
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = Fields.PARENT, fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = CategoryAttribute.Fields.CATEGORY, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryAttribute> attributes = new ArrayList<>();
}
