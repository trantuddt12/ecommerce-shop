package com.ttl.base.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "sccategory",
	uniqueConstraints = {
			@UniqueConstraint(name = "uk_category_slug", columnNames = "slug")
	}
)
@Entity
@AllArgsConstructor @NoArgsConstructor
@Builder(toBuilder = true)
@Getter @Setter
@ToString(exclude = {"parent", "categories"})
//@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Category extends AuditMetadata{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @EqualsAndHashCode.Include
    private Long id; // dùng Long thay vì long cũng là best practice

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length=100 , nullable = false) 
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();
    
    @Builder.Default
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryAttribute> attributes = new ArrayList<CategoryAttribute>();

	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Category)) return false;
		Category other = (Category)obj;
		return id != null && id.equals(other.id);
	}
}
