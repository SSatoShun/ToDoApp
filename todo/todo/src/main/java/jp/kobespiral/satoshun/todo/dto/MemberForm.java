package jp.kobespiral.satoshun.todo.dto;
import lombok.Data;
import jp.kobespiral.satoshun.todo.entity.Member;
@Data
public class MemberForm {
    String mid;
    String name;

    public Member toEntity(){
        Member m = new Member(mid,name);
        return m;
    }
}
