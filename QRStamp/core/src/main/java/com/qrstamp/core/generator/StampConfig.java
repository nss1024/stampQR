package com.qrstamp.core.generator;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StampConfig {
    private Long id;
    private String configName;
    private Double stampWidth;
    private Double pixelSize;
    private Double pixelDistance;
    private Double stampFrameWidth;
    private Double stampHeight;
}
