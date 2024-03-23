package br.com.fiap.apiphinder.domain.repository;

import br.com.fiap.apiphinder.domain.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {

    List<WorkRecord> findAllByUserUsername(String username);
}
