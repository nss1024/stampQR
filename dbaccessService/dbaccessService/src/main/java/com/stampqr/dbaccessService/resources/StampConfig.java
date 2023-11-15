package com.stampqr.dbaccessService.resources;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="stamp_config")
public class StampConfig {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="config_name")
    private String configName;
    @Column(name="stamp_width")
    private Double stampWidth;
    @Column(name="pixel_size")
    private Double pixelSize;
    @Column(name="pixels_distance")
    private Double pixelDistance;
    @Column(name="stamp_frame_width")
    private Double stampFrameWidth;
    @Column(name="stamp_height")
    private Double stampHeight;


}
