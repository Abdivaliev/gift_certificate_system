package com.epam.esm.service.impl;

import com.epam.esm.dao.AuditDao;
import com.epam.esm.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuditListenerTest {

    private AuditListener auditListener;
    private AuditDao auditDaoMock;

    @BeforeEach
    public void setUp() {
        auditDaoMock = mock(AuditDao.class);
        auditListener = new AuditListener();
        auditListener.init(auditDaoMock);
    }

    @Test
    public void onPersist() {
        BaseEntity baseEntity = new Order();
        baseEntity.setId(1L);

        auditListener.prePersist(baseEntity);

        ArgumentCaptor<Audit> argumentCaptor = ArgumentCaptor.forClass(Audit.class);
        verify(auditDaoMock).save(argumentCaptor.capture());

        Audit capturedAudit = argumentCaptor.getValue();
        assertEquals(1L, capturedAudit.getEntityId());
        assertEquals("Order", capturedAudit.getEntityType());
        assertEquals("INSERT", capturedAudit.getOperation());
        assertNotNull(capturedAudit.getTime());
    }

    @Test
    public void onUpdate() {
        BaseEntity baseEntity = new Tag();
        baseEntity.setId(1L);

        auditListener.preUpdate(baseEntity);

        ArgumentCaptor<Audit> argumentCaptor = ArgumentCaptor.forClass(Audit.class);
        verify(auditDaoMock).save(argumentCaptor.capture());

        Audit capturedAudit = argumentCaptor.getValue();
        assertEquals(1L, capturedAudit.getEntityId());
        assertEquals("Tag", capturedAudit.getEntityType());
        assertEquals("UPDATE", capturedAudit.getOperation());
        assertNotNull(capturedAudit.getTime());
    }

    @Test
    public void onRemove() {
        BaseEntity baseEntity = new GiftCertificate();
        baseEntity.setId(1L);

        auditListener.preRemove(baseEntity);

        ArgumentCaptor<Audit> argumentCaptor = ArgumentCaptor.forClass(Audit.class);
        verify(auditDaoMock).save(argumentCaptor.capture());

        Audit capturedAudit = argumentCaptor.getValue();
        assertEquals(1L, capturedAudit.getEntityId());
        assertEquals("GiftCertificate", capturedAudit.getEntityType());
        assertEquals("DELETE", capturedAudit.getOperation());
        assertNotNull(capturedAudit.getTime());
    }
}
