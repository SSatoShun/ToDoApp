// package jp.kobespiral.satoshun.todo.controller;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;


// import jp.kobespiral.satoshun.todo.dto.MemberForm;
// import jp.kobespiral.satoshun.todo.dto.LoginForm;
// import jp.kobespiral.satoshun.todo.dto.ToDoForm;
// import jp.kobespiral.satoshun.todo.entity.Member;
// import jp.kobespiral.satoshun.todo.entity.ToDo;
// import jp.kobespiral.satoshun.todo.exception.ToDoAppException;
// import jp.kobespiral.satoshun.todo.service.MemberService;
// import jp.kobespiral.satoshun.todo.service.ToDoService;
// import jp.kobespiral.satoshun.todo.dto.UserDetailsImpl;


// @Controller
// // @RequestMapping("/")

// public class ToDoController {
//     @Autowired
//    ToDoService tdService;
//    @Autowired
//    MemberService mService;
//    /**
//     * 管理者用・ユーザ登録ページ HTTP-GET /admin/register
//     * @param model
//     * @return
//     */

//    @GetMapping("/sign_in")
//    String showIndex(@RequestParam Map<String,String> params,@ModelAttribute LoginForm form,String mid,Model model) {
//     //パラメータ処理．ログアウト時は?logout, 認証失敗時は?errorが帰ってくる（WebSecurityConfig.java参照） 
// 		if (params.containsKey("sign_out")) {
// 			model.addAttribute("message", "サインアウトしました");
// 		} else if (params.containsKey("error")) {
// 			model.addAttribute("message", "サインインに失敗しました");
// 		} 
//         //model.addAttribute("loginForm", loginForm);
//         return "signin";
//     //    List<ToDo> todoes = tdService.getToDoes(mid);
//     //    model.addAttribute("todoes", todoes);
//     //    ToDoForm form = new ToDoForm();
//     //    model.addAttribute("ToDoForm", form);
//     ///model.addAttribute("MidForm",form);
       
//        ///return "login";
//    }

//    @GetMapping("/sign_in_success")
//    String login() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         Member m  = ((UserDetailsImpl) auth.getPrincipal()).getMember();
//         if (m.getRole().equals("ADMIN")) {
//             return "redirect:/admin/register";
//         }
//         return "redirect:/" + m.getMid() + "/todos";
//     }
// //    String loginMember(@Validated @ModelAttribute(name = "MidForm") MidForm form,BindingResult bindingResult,Model model) {
// //         if (bindingResult.hasErrors()) {
// //             // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
// //             return showLoginForm(form, form.getMid(),model);
// //         }
// //         boolean judge = mService.checkMember(form.getMid());
// //         if(judge){throw new ToDoAppException(ToDoAppException.NO_SUCH_MEMBER_EXISTS, "Such member is not exist! : " + form.getMid());}
// //        return "redirect:/users/"+form.getMid();
       
// //    }

// /**
//      * ユーザのToDoリストのページ
//      */
//     @GetMapping("/{mid}/todos")
//     String showToDoList(@PathVariable String mid, @ModelAttribute(name = "ToDoForm") ToDoForm form, Model model) {
//         checkIdentity(mid);

//         Member m = mService.getMember(mid);
//         model.addAttribute("member", m);
//         model.addAttribute("ToDoForm", form);
//         List<ToDo> todos = tdService.getToDoes(mid);
//         model.addAttribute("todos", todos);
//         List<ToDo> dones = tdService.getDones(mid);
//         model.addAttribute("dones", dones);
//         return "list";
//     }

//     /**
//      * 全員のToDoリストのページ
//      */
//     @GetMapping("/{mid}/todos/all")
//     String showAllToDoList(@PathVariable String mid, Model model) {
//         checkIdentity(mid);
//         Member m = mService.getMember(mid);
//         model.addAttribute("member", m);
//         List<ToDo> todos = tdService.getAllToDoes();
//         model.addAttribute("todos", todos);
//         List<ToDo> dones = tdService.getAllDones();
//         model.addAttribute("dones", dones);
//         return "alllist";
//     }

//     /**
//      * ToDoの作成．作成処理後，ユーザページへリダイレクト
//      */
//     @PostMapping("/{mid}/todos")
//     String createToDo(@PathVariable String mid, @Validated @ModelAttribute(name = "ToDoForm") ToDoForm form,
//             BindingResult bindingResult, Model model) {
//         checkIdentity(mid);

//         if (bindingResult.hasErrors()) {
//             return showToDoList(mid, form, model);
//         }
//         tdService.createToDo(mid, form);
//         return "redirect:/" + mid + "/todos";
//     }

//     /**
//      * ToDoの完了．完了処理後，ユーザページへリダイレクト
//      */
//     @GetMapping("/{mid}/todos/{seq}/done")
//     String doneToDo(@PathVariable String mid, @PathVariable Long seq, Model model) {
//         checkIdentity(mid);
//         tdService.changeCondition(seq);
//         return "redirect:/" + mid + "/todos";
//     }


//     /**
//      * 認可チェック．与えられたmidがログイン中のmidに等しいかチェックする
//      */
//     private void checkIdentity(String mid) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         Member m  = ((UserDetailsImpl) auth.getPrincipal()).getMember();
//         if (!mid.equals(m.getMid())) {
//             throw new ToDoAppException(ToDoAppException.INVALID_TODO_OPERATION, 
//             m.getMid() + ": not authorized to access resources of " + mid);
//         }
//     }

// //    @GetMapping("/users/{mid}")
// //    String showToDo(ToDoForm form,@PathVariable String mid,Model model){
// //     model.addAttribute("ToDoForm", form);
// //     model.addAttribute("ToDos", tdService.getToDoes(mid));
// //     model.addAttribute("Dones", tdService.getDones(mid));
// //     return "ToDos";
// //    }

// //    @PostMapping("/users/{mid}/todos")
// //    String addToDo(@Validated @ModelAttribute(name = "ToDoForm") ToDoForm form, BindingResult bindingResult,@PathVariable String mid,Model model){
// //     if (bindingResult.hasErrors()) {
// //         // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻る
// //         return showToDo(form, mid,model);
// //     }
// //     tdService.createToDo(mid, form);
// //     return "redirect:/users/" + mid;
// //    }

// //    @GetMapping("/users/{mid}/todos/{seq}/delete")
// //    String deleteToDo(@ModelAttribute(name = "ToDoForm") ToDoForm form, @PathVariable String mid,@PathVariable String seq,Model model){
// //     tdService.deleteToDo(Long.parseLong(seq));
// //     return "redirect:/users/" + mid;
// //    }
// //    @GetMapping("/users/{mid}/todos/{seq}")
// //    String change_to_condition(@ModelAttribute(name = "ToDoForm") ToDoForm form, @PathVariable String mid,@PathVariable String seq,Model model){
// //     tdService.changeCondition(Long.parseLong(seq));
// //     return "redirect:/users/" + mid;
// //    }

   

// //    @GetMapping("/users/todos")
// //    String showAllToDo(Model model){
// //     model.addAttribute("AllToDos", tdService.getAllToDoes());
// //     model.addAttribute("AllDones", tdService.getAllDones());
// //     return "alllist";
// //    }
// }