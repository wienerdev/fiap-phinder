package br.com.fiap.apiphinder.domain.entity;

import br.com.fiap.apiphinder.domain.enums.WorkRecordType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "work_records")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private WorkRecordType type;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WorkRecord(LocalDateTime timestamp, WorkRecordType type, User user) {
        this.timestamp = timestamp;
        this.type = type;
        this.user = user;
    }

}