package vn.hoidanit.laptopshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        List<User> arrUsers = userService.getAllUsers();
        System.out.println(arrUsers);
        model.addAttribute("eric", test);
        model.addAttribute("hoidanit", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = userService.getAllUsers();
        System.out.println("checkUsers = " + users);
        model.addAttribute("user1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        System.out.println(" run here " + hoidanit);
        userService.handSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        System.out.println("check path id = " + id);
        Optional<User> userById = userService.getUserById(id);
        model.addAttribute("user", userById.get());
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        Optional<User> userById = userService.getUserById(id);
        model.addAttribute("user", userById.get());
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String updateUser(Model model, @ModelAttribute("user") User user) {
        Optional<User> userById = userService.getUserById(user.getId());
        User currentUser = userById.get();
        currentUser.setAddress(user.getAddress());
        currentUser.setFullName(user.getFullName());
        currentUser.setPhone(user.getPhone());
        userService.handSaveUser(currentUser);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/delete/{id}") // GET
    public String getPageUserDelete(Model model, @PathVariable long id) {
        Optional<User> userById = userService.getUserById(id);
        User currentUser = userById.get();
        model.addAttribute("user", currentUser);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete") // GET
    public String deleteUser(Model model, @ModelAttribute("user") User user) {
        System.out.println("delete " + user);
        userService.deleteUser(user.getId());
        return "redirect:/admin/user";
    }
}
