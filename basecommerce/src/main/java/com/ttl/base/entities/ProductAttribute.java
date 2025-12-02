package com.ttl.base.entities;

//@Entity
//@Table(name = "scproduct_attribute",
//       uniqueConstraints = {
//           @UniqueConstraint(name = "uk_product_attribute", columnNames = {"product_id", "attribute_id"})
//       })
//@Getter @Setter
//@Builder
//@NoArgsConstructor @AllArgsConstructor
public class ProductAttribute { //đã có categoryAttribute rồi,, thì k cần class này

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Mối quan hệ với Product
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    // Mối quan hệ với Attribute
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "attribute_id", nullable = false)
//    private AttributeDef attributeDef;
//
//    @Column(nullable = false, length = 255)
//    private String value; // ví dụ: Red, Blue, M, L
//
//    // Constructor tiện dụng
//    public ProductAttribute(Product product, AttributeDef attributeDef, String value) {
//        this.product = product;
//        this.attributeDef = attributeDef;
//        this.value = value;
//    }
//    
//    public static ProductAttribute of(Product product, AttributeDef attributeDef, String value) {
//    	return ProductAttribute.builder().product(product).attributeDef(attributeDef).value(value).build();
//    }
}

