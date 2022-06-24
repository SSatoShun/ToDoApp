package jp.kobespiral.satoshun.todo.entity;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter/setterを自動作成 lombokがやってくれる
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Member {
    @Id
    String mid; //メンバーID
    String name; //氏名
}
