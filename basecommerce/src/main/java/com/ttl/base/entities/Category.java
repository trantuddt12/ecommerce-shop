package com.ttl.base.entities;

import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.LazyGroup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.ttl.core.entities.AbstractLocalization.defaultLocale;

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

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "language")
    private Map<String, CategoryL10n> localizations = new HashMap<>();
    
    @Column(length=100 , nullable = false) 
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = Fields.PARENT)
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = Fields.PARENT, fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();
    
//    @Builder.Default
//    @OneToMany(mappedBy = CategoryAttribute.Fields.CATEGORY, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<CategoryAttribute> attributes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "categories_2_attributes",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    @LazyGroup(Fields.ATTRIBUTES)
    private Set<Attribute> attributes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Builder.Default
    @JoinColumn(name = "category_id")
    private Set<Image> galleryImages = new HashSet<>();


    private CategoryL10n getCurrentLocalization() {
        return localizations.get(defaultLocale.getLanguage()) != null ? localizations.get(defaultLocale.getLanguage()) : new CategoryL10n();
    }

    public void setName(String name) {
        CategoryL10n localization = getCurrentLocalization();
        localization.setName(name);
        localization.setLanguage(localization.getLanguage());
        localization.setObject(this);
    }

    public void setDescription(String description) {
        CategoryL10n localization = getCurrentLocalization();
        localization.setDescription(description);
        localization.setLanguage(localization.getLanguage());
        localization.setObject(this);
    }

    public String getName() {
        CategoryL10n localization = getCurrentLocalization();
        return localization != null ? localization.getName() : "";
    }

    public String getDescription() {
        CategoryL10n localization = getCurrentLocalization();
        return localization != null ? localization.getDescription() : "";
    }
}
