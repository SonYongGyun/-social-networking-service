package kr.co.mz.sns.enums;

import kr.co.mz.sns.converter.LegacyCommonType;
import kr.co.mz.sns.exception.NoSuchStateException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PostVisibility implements LegacyCommonType {

    PUBLIC("001", "공개"),
    PRIVATE("010", "비공개"),
    MAN("100", "친구만 공개");

    private final String code;
    private final String label;

    PostVisibility(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static PostVisibility ofCode(String code) {
        return Arrays.stream(PostVisibility.values())
                .filter(e -> e.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new NoSuchStateException(String.format("상태코드에 code=[%s]가 존재하지 않습니다.", code)));
    }

}
