package group.bigone.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStep {
    private Long userNo;

    private short stepType;

    private short stateCode;

    private LocalDateTime regDate;
}

