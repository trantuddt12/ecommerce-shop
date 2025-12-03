package com.ttl.core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.util.ProxyUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldNameConstants
@Getter
@Setter
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	@CreatedBy
	@Column(name = Fields.CREATED_BY, updatable = false)
	protected String createdBy;
	
	@CreatedDate
	@Column(name = Fields.CREATED_AT, updatable = false)
    protected LocalDateTime createdAt;
	
	@LastModifiedBy
	@Column(name = Fields.LAST_MODIFIED_BY)
    protected String lastModifiedBy;
	
	@LastModifiedDate
	@Column(name = Fields.LAST_MODIFIED_AT)
    protected LocalDateTime lastModifiedAt;

    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += Optional.ofNullable(getId()).map(objId -> objId.hashCode() * 31).orElse(0);
        return hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        final AbstractEntity that = (AbstractEntity) obj;
        return Optional.ofNullable(getId()).map(objId -> objId.equals(that.getId())).orElse(false);
    }

//    @Override
//    public String toString() {
//        try {
//            final Object entityService = AopProxyUtils.getSingletonTarget(BeanUtils.getBean(ENTITY_SERVICE_BEAN_NAME));
//            final Object isNew = MethodUtils.invokeMethod(entityService, true, "isNew", this);
//            if (Boolean.TRUE.equals(isNew)) {
//                return String.format("%s (<unsaved>)", this.getClass().getSimpleName());
//            } else {
//                return String.format("%s (%s)", this.getClass().getSimpleName(), this.getId());
//            }
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

}
