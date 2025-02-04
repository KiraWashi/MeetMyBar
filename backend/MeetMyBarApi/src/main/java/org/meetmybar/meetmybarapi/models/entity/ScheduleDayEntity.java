package org.meetmybar.meetmybarapi.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "SCHEDULE_DAY")
public class ScheduleDayEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "opening", nullable = false)
    private LocalTime opening;

    @NotNull
    @Column(name = "closing", nullable = false)
    private LocalTime closing;

    @Size(max = 100)
    @NotNull
    @Column(name = "day", nullable = false, length = 100)
    private String day;

}