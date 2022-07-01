package jp.kobespiral.satoshun.todo.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.kobespiral.satoshun.todo.entity.Member;
@Data
public class MemberForm {
    @Pattern(regexp = "[a-z0-9_\\-]{4,16}")
    String mid;

    @NotBlank
    @Size(min = 1, max = 32)
    String name;

    public Member toEntity(){
        Member m = new Member(mid,name);
        return m;
    }
}
