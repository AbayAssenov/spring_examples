package com.assenov.abay.bankdata.repository;

import com.assenov.abay.bankdata.model.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long>, JpaSpecificationExecutor<DataEntity> {

    List<DataEntity> findAllByUpdatedTimestampAfterAndUpdatedTimestampBefore(LocalDateTime start, LocalDateTime end);


}
