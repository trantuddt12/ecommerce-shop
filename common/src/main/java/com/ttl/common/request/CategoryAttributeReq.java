package com.ttl.common.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryAttributeReq {

    private Long categoryId;

    private Long attributeDefId;

    // flag: attribute này bắt buộc phải nhập không?
//    @Column(nullable = false)
    private boolean required;

    // flag: attribute này là filter chính trong search?
//    @Column(nullable = false)
    private boolean filterable;

    // flag: attribute này có phải dùng để tạo SKU variant không?
//    @Column(nullable = false)
    private boolean variant;
}
