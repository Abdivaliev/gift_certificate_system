package com.epam.esm.service.impl;

import com.epam.esm.dao.AuditDao;
import com.epam.esm.entity.Audit;
import com.epam.esm.entity.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import static java.time.LocalDateTime.now;

@Component
@Transactional
public class AuditListener {
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    private static AuditDao auditDao;

    @Autowired
    public void init(AuditDao auditDao) {
        AuditListener.auditDao = auditDao;
    }

    @PrePersist
    public void prePersist(BaseEntity baseEntity) {
        baseEntity.setCreatedDate(now());
        audit(baseEntity, INSERT);
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity) {
        baseEntity.setUpdatedDate(now());
        audit(baseEntity, UPDATE);
    }

    @PreRemove
    public void preRemove(BaseEntity baseEntity) {
        audit(baseEntity, DELETE);
    }

    private void audit(BaseEntity baseEntity, String action) {
        Audit audit = new Audit();

        audit.setEntityId(baseEntity.getId());
        audit.setEntityType(baseEntity.getClass().getSimpleName());
        audit.setOperation(action);
        audit.setTime(now());
        auditDao.save(audit);
    }
}
