package com.tacrolimus.backend.model;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "santa_pair")
public class SantaPair extends BaseEntity{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "santa_id")
    private SantaRegistration santa;

    @OneToOne
    @JoinColumn(name = "recipient_id")
    private SantaRegistration recipient;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo file;

    @Enumerated(EnumType.STRING)
    private SantaPairStatusEnum status;
    private String comment;
}
