package com.tacrolimus.backend.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "file_info")
public class FileInfo extends BaseEntity{
    private String name;
    private String url;
    private Integer size;
}
