package jp.kobespiral.satoshun.todo.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import jp.kobespiral.satoshun.todo.entity.ToDo;
@Data
public class ToDoForm {
    @NotBlank
    @Pattern(regexp = "[a-z0-9_\\-]{3,64}")
    String title;

    // public ToDo toEntity(){
    //     ToDo t = new ToDo(null, title, title, false, null, null);
    //     return t;
    // }
}
