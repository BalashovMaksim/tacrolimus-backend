package com.tacrolimus.backend.model;

import com.tacrolimus.backend.enu.SantaPairStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "santa_pair")
public class SantaPair extends BaseEntity{
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
