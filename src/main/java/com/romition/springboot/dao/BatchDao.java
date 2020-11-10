package com.romition.springboot.dao;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

//@Repository
@Transactional
public class BatchDao<T, ID extends Serializable> implements GenericDao<T, ID> {

    private static final Log log = LogFactory.get();

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final JpaContext jpaContext;

    public BatchDao(JpaContext jpaContext) {
        this.jpaContext = jpaContext;
    }    

    @Override
    public <S extends T> void saveInBatch(Iterable<S> entities) {

        if (entities == null) {
            throw new IllegalArgumentException("The given Iterable of entities not be null!");
        }

        int i = 0;

        for (S entity : entities) {
            getEntityManager(entity).persist(entity);

            i++;

            // Flush a batch of inserts and release memory
            if (i % batchSize == 0 && i > 0) {
                log.info("Flushing the EntityManager containing {0} entities ...", i);

                getEntityManager(entity).flush();
                getEntityManager(entity).clear();
                i = 0;
            }
        }

        if (i > 0) {
            log.info("Flushing the remaining {0} entities ...", i);

            getEntityManager(entities.iterator().next()).flush();
            getEntityManager(entities.iterator().next()).clear();
        }        
    }

    protected EntityManager getEntityManager(T entity) {
        return jpaContext.getEntityManagerByManagedType(entity.getClass());
    }
}
