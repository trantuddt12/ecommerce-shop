package com.search.index;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandIndex {
    private String id;
    private String name;
    private String description;
}
