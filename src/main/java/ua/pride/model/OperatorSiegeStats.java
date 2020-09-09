package ua.pride.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Accessors(chain = true)
public class OperatorSiegeStats {
    private String overallOperatorKd;
    private String overallOperatorHours;
    private String overallWinrate;
    private String seasonalOperatorKd;
    private String seasonalOperatorHours;
    private String seasonalWinrate;
}
