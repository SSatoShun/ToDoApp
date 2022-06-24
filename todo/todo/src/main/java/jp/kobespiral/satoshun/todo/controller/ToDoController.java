package jp.kobespiral.satoshun.todo.controller;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.satoshun.todo.dto.MemberForm;
import jp.kobespiral.satoshun.todo.dto.ToDoForm;
import jp.kobespiral.satoshun.todo.entity.ToDo;
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
   String showLoginForm(String mid,Model model) {
    //    List<ToDo> todoes = tdService.getToDoes(mid);
    //    model.addAttribute("todoes", todoes);
    //    ToDoForm form = new ToDoForm();
    //    model.addAttribute("ToDoForm", form);
    model.addAttribute("MemberForm",new MemberForm());
       
       return "login";
   }

   @GetMapping("/login")
   String loginMember(@ModelAttribute(name = "MemberForm") MemberForm form,Model model) {
        boolean judge = mService.checkMember(form.getMid());
        if(judge){return "redirect:/";}
       return "redirect:/users/"+form.getMid();
   }

   @GetMapping("/users/{mid}")
   String showToDo(@PathVariable String mid,Model model){
    model.addAttribute("ToDoForm", new ToDoForm());
    model.addAttribute("ToDos", tdService.getToDoes(mid));
    model.addAttribute("Dones", tdService.getDones(mid));
    return "ToDos";
   }

   @PostMapping("/users/{mid}/todos")
   String addToDo(@ModelAttribute(name = "ToDoForm") ToDoForm form, @PathVariable String mid,Model model){
    tdService.createToDo(mid, form);
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
