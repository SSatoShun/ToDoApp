package jp.kobespiral.satoshun.todo.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.satoshun.todo.entity.ToDo;
@Repository

public interface ToDoRepository extends CrudRepository<ToDo,Long>{
    List<ToDo> findAll();
    List<ToDo> findToDoByMidAndDone(String mid,boolean done);
    List<ToDo> findToDoByDone(boolean done);
}
