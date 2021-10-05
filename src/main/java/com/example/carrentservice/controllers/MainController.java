package com.example.carrentservice.controllers;

import com.example.carrentservice.entities.Bill;
import com.example.carrentservice.entities.Car;
import com.example.carrentservice.entities.ComplaintsSuggestions;
import com.example.carrentservice.entities.User;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.enums.UserRole;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import com.example.carrentservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    BrandService brandService;
    CarService carService;
    BillService billService;
    ComplaintsSuggestionService complaintsSuggestionService;
    UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        setIndexPageModel(model);
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/complaintsSuggestions")
    public String complaintsSuggestions(Model model) {
        model.addAttribute("complaintSuggestion", new ComplaintsSuggestions());
        return "complaintsSuggestions";
    }

    @PostMapping("/complaintsSuggestions")
    public String complaintsSuggestions(Model model, @ModelAttribute ComplaintsSuggestions complaintSuggestion) {
        complaintsSuggestionService.saveOrUpdateComplaintSuggestion(complaintSuggestion);
        setIndexPageModel(model);
        return "index";
    }

    private void setIndexPageModel(Model model){
        model.addAttribute("brandList", brandService.getBrandList());
        model.addAttribute("averagePriceList", brandService.getAveragePriceList());
    }


    @GetMapping(value = {"/brand/update", "/car/update"})
    public String selectObjectForUpdate(Model model, HttpServletRequest request){
        if(request.getRequestURI().contains("brand")) {
            model.addAttribute("selectorList", brandService.getBrandNameList());
            model.addAttribute("type", "brand");
        } else {
            model.addAttribute("selectorList", carService.getCarLicencePlateList());
            model.addAttribute("additionalInfoList", carService.getCarRegistrationCodeList());
            model.addAttribute("type", "car");
        }
        return "selectIdList";
    }

    @PostMapping(value = "/search")
    public String findCar(Model model, @RequestParam(value = "search") String search){
        List<Car> foundCarList = carService.searchThroughAllCarsByAllFields(search);
        model.addAttribute("searchedText", search);
        model.addAttribute("carList", foundCarList);
        model.addAttribute("carClassList", getExistingCarClassList(foundCarList));
        return "search";
    }

    @RequestMapping(value = "/cabinet")
    public String getCabinet(Principal principal, Model model){
        User user = userService.findUserByPhone(principal.getName());
        model.addAttribute("billList", user.getBills());
        return "cabinet";
    }

    @GetMapping(value = "/login")
    public String getLoginForm(Model model){
        return "login";
    }

    @GetMapping(value = "/registration")
    public String getRegistrationForm(){
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String register(User user, Model model){
        User userFromDb = userService.findUserByPhone(user.getPhone());
        if(userFromDb != null){
            if(userFromDb.isEnabled()) {
                model.addAttribute("exist", true);
                return getLoginForm(model);
            }
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(UserRole.USER));
        userService.addOrUpdateUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/user/update")
    public String getUpdateUserView(Model model, Principal principal){
        User user = userService.findUserByPhone(principal.getName());
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping(value = "/user/update")
    public String updateUser(User updatedUser, Principal principal){
//        подмена id и обновление другого юзера фикс
        User originalUser = userService.findUserByPhone(principal.getName());
        originalUser.setPhone(updatedUser.getPhone());
        originalUser.setName(updatedUser.getName());
        originalUser.setSurname(updatedUser.getSurname());
        originalUser.setEmail(updatedUser.getEmail());
        userService.addOrUpdateUser(originalUser);
        return "redirect:/";
    }

    @GetMapping(value = "/user/updatePassword")
    public String getUpdatePasswordView(){
        return "updatePassword";
    }

    @PostMapping(value = "/user/updatePassword")
    public String updatePassword(Principal principal, @RequestParam(name = "password") String password){
        User user = userService.findUserByPhone(principal.getName());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userService.addOrUpdateUser(user);
        return "redirect:/";
    }

    public List<CarClass> getExistingCarClassList(List<Car> carList){
        List<CarClass> carClassList = new ArrayList<CarClass>();
        for (Car car : carList) {
            CarClass carClass = car.getCarClass();
            if(!carClassList.contains(carClass)){
                carClassList.add(carClass);
            }
        }
        return carClassList;
    }

    public BrandService getBrandService() {
        return brandService;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    public CarService getCarService() {
        return carService;
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    public BillService getBillService() {
        return billService;
    }

    @Autowired
    public void setBillService(BillService billService) {
        this.billService = billService;
    }

    public ComplaintsSuggestionService getComplaintsSuggestionService() {
        return complaintsSuggestionService;
    }

    @Autowired
    public void setComplaintsSuggestionService(ComplaintsSuggestionService complaintsSuggestionService) {
        this.complaintsSuggestionService = complaintsSuggestionService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
