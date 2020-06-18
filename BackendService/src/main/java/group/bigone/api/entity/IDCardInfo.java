package group.bigone.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IDCardInfo {
    private String idCardImage;

    private String name;

    private String birthday;

    private String idCardSeqNo;
}
