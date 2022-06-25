package jp.kobespiral.satoshun.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.satoshun.todo.dto.ToDoForm;
import jp.kobespiral.satoshun.todo.entity.ToDo;
import jp.kobespiral.satoshun.todo.exception.ToDoAppException;
import jp.kobespiral.satoshun.todo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
   ToDoRepository tRepo;
   /**
    * ToDoを作成する (C)
    * @param form
    * @return
    */
   public ToDo createToDo(String mid,ToDoForm form) {
       //IDの重複チェック
    //    String mid = form.getMid();
    //    if (mRepo.existsById(mid)) {
    //        throw new ToDoAppException(ToDoAppException.MEMBER_ALREADY_EXISTS, mid + ": Member already exists");
    //    }
       ToDo t = new ToDo(null, form.getTitle(), mid, false, null, null);
       t.setCreatedAt(new Date());
       return tRepo.save(t);
   }

   /**
    * ToDoを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, seq + ": No such member exists"));
       return t;
   }
   /**
    * 全ToDoを取得する (R)
    * @return
    */
   public List<ToDo> getAllToDoes() {
       return tRepo.findToDoByDone(false);
   }

   /**
    * あるユーザのToDoを取得する (R)
    * @return
    */
    public List<ToDo> getToDoes(String mid) {
        return tRepo.findToDoByMidAndDone(mid,false);
    }

   /**
    * 全Doneを取得する (R)
    * @return
    */
    public List<ToDo> getAllDones() {
        return tRepo.findToDoByDone(true);
    }

    /**
    * あるユーザのDoneを取得する (R)
    * @return
    */
    public List<ToDo> getDones(String mid) {
        return tRepo.findToDoByMidAndDone(mid,true);
    }
   /**
    * ToDoを削除する (D)
    */
    /////////////////////////////????????????/
   public void deleteToDo(Long seq) {
       ToDo t = getToDo(seq);
       tRepo.delete(t);
   }

   public ToDo changeCondition(Long seq){
    ToDo t = getToDo(seq);
    if(t.isDone()){
        t.setDone(false);
        t.setDoneAt(null);
    }
    else{
        t.setDone(true);
        t.setDoneAt(new Date());
    }
    return tRepo.save(t);
   }
}
