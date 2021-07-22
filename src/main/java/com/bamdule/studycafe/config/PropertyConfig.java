package com.bamdule.studycafe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PropertyConfig {

    private boolean useSeatSchedule = false;
}
