package jp.kobespiral.satoshun.todo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.kobespiral.satoshun.todo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotBlank
    String mid;
    @NotBlank
    @Size(min = 8)
    String password;
}
// @Data
// public class LoginForm {
//     @NotBlank
//     @Pattern(regexp = "[a-z0-9_\\-]{4,16}")
//     String mid;
//     @NotBlank
//     @Size(min = 8)
//     String password;

//     public Member toEntity(){
//         Member m = new Member(mid,"","","");
//         return m;
//     }
// }
