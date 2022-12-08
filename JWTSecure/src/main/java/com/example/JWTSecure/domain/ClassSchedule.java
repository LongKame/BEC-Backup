package com.example.JWTSecure.domain;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "class_schedule")
public class ClassSchedule {

    @Id
    @SequenceGenerator(
            name = "class_schedule_sequence",
            sequenceName = "class_schedule_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "class_schedule_sequence"
    )
    private Long id;
    @Column(name="class_id")
    private Long classId;
    @Column(name="slot_th")
    private int slot_th;
    @Column(name="date")
    private LocalDate date;
    @Column(name="room_id")
    private Long roomId;
    @Column(name="slot_of_date")
    private Long slotOfDate;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    public ClassSchedule(Long id, Long classId, int slot_th, LocalDate date, Long roomId, Long slotOfDate, LocalDateTime updatedAt) {
        this.id = id;
        this.classId = classId;
        this.slot_th = slot_th;
        this.date = date;
        this.roomId = roomId;
        this.slotOfDate = slotOfDate;
        this.updatedAt = updatedAt;
    }
}
