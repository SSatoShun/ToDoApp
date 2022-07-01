package jp.kobespiral.satoshun.todo.controller;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.satoshun.todo.dto.MemberForm;
import jp.kobespiral.satoshun.todo.dto.MidForm;
import jp.kobespiral.satoshun.todo.dto.ToDoForm;
import jp.kobespiral.satoshun.todo.entity.ToDo;
import jp.kobespiral.satoshun.todo.exception.ToDoAppException;
import jp.kobespiral.satoshun.todo.service.MemberService;
import jp.kobespiral.satoshun.todo.service.ToDoService;


@Controller
@RequestMapping("/")

public class ToDoController {
    @Autowired
   ToDoService tdService;
   @Autowired
   MemberService mService;
   /**
    * 管理者用・ユーザ登録ページ HTTP-GET /admin/register
    * @param model
    * @return
    */

   @GetMapping("/")
   String showLoginForm(MidForm form,String mid,Model model) {
    //    List<ToDo> todoes = tdService.getToDoes(mid);
    //    model.addAttribute("todoes", todoes);
    //    ToDoForm form = new ToDoForm();
    //    model.addAttribute("ToDoForm", form);
    model.addAttribute("MidForm",form);
       
       return "login";
   }

   @GetMapping("/login")
   String loginMember(@Validated @ModelAttribute(name = "MidForm") MidForm form,BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
            return showLoginForm(form, form.getMid(),model);
        }
        boolean judge = mService.checkMember(form.getMid());
        if(judge){throw new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, "Such member is not exist! : " + form.getMid());}
       return "redirect:/users/"+form.getMid();
       
   }

   @GetMapping("/users/{mid}")
   String showToDo(ToDoForm form,@PathVariable String mid,Model model){
    model.addAttribute("ToDoForm", form);
    model.addAttribute("ToDos", tdService.getToDoes(mid));
    model.addAttribute("Dones", tdService.getDones(mid));
    return "ToDos";
   }

   @PostMapping("/users/{mid}/todos")
   String addToDo(@Validated @ModelAttribute(name = "ToDoForm") ToDoForm form, BindingResult bindingResult,@PathVariable String mid,Model model){
    if (bindingResult.hasErrors()) {
        // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
        return showToDo(form, mid,model);
    }
    tdService.createToDo(mid, form);
    return "redirect:/users/" + mid;
   }

   @GetMapping("/users/{mid}/todos/{seq}/delete")
   String deleteToDo(@ModelAttribute(name = "ToDoForm") ToDoForm form, @PathVariable String mid,@PathVariable String seq,Model model){
    tdService.deleteToDo(Long.parseLong(seq));
    return "redirect:/users/" + mid;
   }
   @GetMapping("/users/{mid}/todos/{seq}")
   String change_to_condition(@ModelAttribute(name = "ToDoForm") ToDoForm form, @PathVariable String mid,@PathVariable String seq,Model model){
    tdService.changeCondition(Long.parseLong(seq));
    return "redirect:/users/" + mid;
   }

   

   @GetMapping("/users/todos")
   String showAllToDo(Model model){
    model.addAttribute("AllToDos", tdService.getAllToDoes());
    model.addAttribute("AllDones", tdService.getAllDones());
    return "alllist";
   }
}
