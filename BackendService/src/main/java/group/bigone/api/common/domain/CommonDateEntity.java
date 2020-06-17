package group.bigone.api.common.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class CommonDateEntity {
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}