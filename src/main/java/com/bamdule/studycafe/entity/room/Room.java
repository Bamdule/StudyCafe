package com.bamdule.studycafe.entity.room;

import com.bamdule.studycafe.entity.studycafe.StudyCafe;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_cafe_id", nullable = false)
    private StudyCafe studyCafe;

}
