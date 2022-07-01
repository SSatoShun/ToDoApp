package jp.kobespiral.satoshun.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jp.kobespiral.satoshun.todo.entity.Member;
import lombok.Data;
@Data
public class MidForm {
    @NotBlank
    @Pattern(regexp = "[a-z0-9_\\-]{4,16}")
    String mid;

    public Member toEntity(){
        Member m = new Member(mid,"");
        return m;
    }
}
