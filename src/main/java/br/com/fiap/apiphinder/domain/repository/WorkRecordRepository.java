package br.com.fiap.apiphinder.domain.repository;

import br.com.fiap.apiphinder.domain.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {
}
