package jp.kobespiral.satoshun.todo.dto;
import lombok.Data;
import jp.kobespiral.satoshun.todo.entity.ToDo;
@Data
public class ToDoForm {
    String title;

    // public ToDo toEntity(){
    //     ToDo t = new ToDo(null, title, title, false, null, null);
    //     return t;
    // }
}
