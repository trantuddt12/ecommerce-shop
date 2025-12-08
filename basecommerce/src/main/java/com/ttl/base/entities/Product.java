package com.ttl.base.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttl.common.dto.ProductStatus;
import com.ttl.common.dto.ProductType;
import com.ttl.core.entities.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.LazyGroup;

import java.math.BigDecimal;
import java.util.*;

import static com.ttl.core.entities.AbstractLocalization.defaultLocale;

@Table(name = "products")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class Product extends AbstractEntity {

    @OneToMany(mappedBy = "object", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "language") // Key của Map sẽ là mã ngôn ngữ (vd: "vi", "en")
    private Map<String, ProductL10N> localizations = new HashMap<>();

    @Column
    private String code;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal price;

    @Column(name = Fields.SELLER_ID, nullable = false)
    private Long sellerId;

    @Column(name = Fields.CATEGORY_ID, nullable = false)
    private Long categoryId;

    @Column(name = Fields.BRAND_ID, nullable = false)
    private Long brandId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private ProductType type = ProductType.SIMPLE;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product baseProduct;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_2_attributes",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    @LazyGroup(Fields.ATTRIBUTES)
    private Set<Attribute> attributes = new HashSet<>();

    //with TYPE = PRODUCT.VARIANT not include field variants
    @OneToMany(mappedBy = Fields.BASE_PRODUCT, fetch = FetchType.LAZY)
    @LazyGroup(Fields.VARIANTS)
    private List<Product> variants = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @Builder.Default
    @LazyGroup(Fields.GALLERY_IMAGES)
    private Set<Image> galleryImages = new HashSet<>();

    private ProductL10N getCurrentLocalization() {
        return localizations.get(defaultLocale.getLanguage()) != null ? localizations.get(defaultLocale.getLanguage()) : new ProductL10N();
    }

    public void setName(String name) {
        ProductL10N localization = getCurrentLocalization();
        localization.setName(name);
        localization.setLanguage(localization.getLanguage());
        localization.setObject(this);
    }

    public void setDescription(String description) {
        ProductL10N localization = getCurrentLocalization();
        localization.setDescription(description);
        localization.setLanguage(localization.getLanguage());
        localization.setObject(this);
    }

    public String getName() {
        ProductL10N localization = getCurrentLocalization();
        return localization != null ? localization.getName() : "";
    }

    public String getDescription() {
        ProductL10N localization = getCurrentLocalization();
        return localization != null ? localization.getDescription() : "";
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
