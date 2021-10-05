package com.example.carrentservice.controllers;

import com.example.carrentservice.entities.Bill;
import com.example.carrentservice.entities.Car;
import com.example.carrentservice.entities.User;
import com.example.carrentservice.entities.idClass.CarId;
import com.example.carrentservice.enums.CarClass;
import com.example.carrentservice.enums.FuelType;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import com.example.carrentservice.helpers.ImagesHelperUtil;
import com.example.carrentservice.services.BillService;
import com.example.carrentservice.services.BrandService;
import com.example.carrentservice.services.CarService;
import com.example.carrentservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/car")
public class CarController {

    BrandService brandService;
    CarService carService;
    UserService userService;
    BillService billService;

    @RequestMapping(value = {"/add","/updateForm"}, method = {RequestMethod.GET})
    public String addUpdate(Model model, @RequestParam(value = "selector", required = false) String carIdString, HttpServletRequest httpServletRequest) throws NoSuchElementInDatabaseException {
        if(httpServletRequest.getRequestURI().contains("add")) {
            model.addAttribute("car", new Car());
            model.addAttribute("addOrUpdateField", "Добавление");
            model.addAttribute("carClassList", CarClass.values());
            model.addAttribute("fuelTypeList", FuelType.values());
            model.addAttribute("brandList", brandService.getBrandList());
            model.addAttribute("exists", false);
        } else {
            List<String> plateAndCode = Arrays.stream(carIdString.split(" ")).collect(Collectors.toList());
            CarId carId = new CarId(plateAndCode.get(0), plateAndCode.get(1));
            model.addAttribute("car", carService.findCar(carId));
            model.addAttribute("addOrUpdateField", "Обновление");
            model.addAttribute("carClassList", CarClass.values());
            model.addAttribute("fuelTypeList", FuelType.values());
            model.addAttribute("brandList", brandService.getBrandList());
            model.addAttribute("exists", true);
        }
        return "/car/addUpdateForm";
    }

    @PostMapping("/addOrUpdate")
    public String addUpdateBrand(
            Model model,
            @RequestParam(value = "brandId") int brandId,
            @RequestParam(value = "imageFile", required = false) MultipartFile multipartFile,
            @ModelAttribute Car car) throws IOException, NoSuchElementInDatabaseException
    {
        if(carService.findCar(car.getId()) != null && !multipartFile.getOriginalFilename().isBlank()) {
            ImagesHelperUtil.deleteFile("src/main/resources/static/images", carService.findCar(car.getId()).getImg());
        }
        if(!multipartFile.getOriginalFilename().isBlank()){
            String fileName = UUID.randomUUID().toString() + StringUtils.cleanPath(multipartFile.getOriginalFilename());
            car.setImg(fileName);
            ImagesHelperUtil.saveFile("src/main/resources/static/images", fileName, multipartFile);
        }
        car.setBrand(brandService.findBrand(brandId));
        carService.addOrUpdateCar(car);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Car car) throws IOException{
        ImagesHelperUtil.deleteFile("src/main/resources/static/images", carService.findCar(car.getId()).getImg());
        carService.deleteCar(car.getId());
        return "redirect:/";
    }

    @GetMapping("/rent/{licencePlate}_{registrationCode}")
    public String getRentView(@PathVariable(name = "licencePlate") String licencePlate, @PathVariable(name = "registrationCode") String registrationCode, Model model){
        CarId carId = new CarId(licencePlate, registrationCode);
        Car car = carService.findCar(carId);
        model.addAttribute("car", car);
        return "car/rent";
    }

    @PostMapping("/rent/{licencePlate}_{registrationCode}")
    public String rentCar(
            @PathVariable(name = "licencePlate") String licencePlate,
            @PathVariable(name = "registrationCode") String registrationCode,
            @RequestParam(value = "phone") String phone,
            Bill bill)
    {
        User user = userService.findUserByPhone(phone);
        if(user == null){
            user = new User();
            user.setPhone(phone);
            userService.addOrUpdateUser(user);
        }

        Car car = carService.findCar(new CarId(licencePlate, registrationCode));
        bill.setCar(car);
        bill.setUser(user);
        billService.addOrUpdateBill(bill);
        return "redirect:/";
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

    public UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BillService getBillService() {
        return billService;
    }

    @Autowired
    public void setBillService(BillService billService) {
        this.billService = billService;
    }
}