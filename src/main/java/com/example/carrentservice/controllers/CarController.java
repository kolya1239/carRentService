package com.example.carrentservice.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/car")
public class CarController {

    BrandService brandService;
    CarService carService;
    UserService userService;
    BillService billService;
    Cloudinary cloudinaryConfig;


    @RequestMapping(value = {"/add","/updateForm"}, method = {RequestMethod.GET})
    public String addUpdate(Model model, @RequestParam(value = "selector", required = false) String carIdString, HttpServletRequest httpServletRequest) throws NoSuchElementInDatabaseException {
        if(httpServletRequest.getRequestURI().contains("add")) {
            model.addAttribute("car", new Car());
            model.addAttribute("addOrUpdateField", "Добавление");
            model.addAttribute("exists", false);
        } else {
            List<String> plateAndCode = Arrays.stream(carIdString.split(" ")).collect(Collectors.toList());
            CarId carId = new CarId(plateAndCode.get(0), plateAndCode.get(1));
            model.addAttribute("car", carService.findCar(carId));
            model.addAttribute("addOrUpdateField", "Обновление");
            model.addAttribute("exists", true);
        }
        model.addAttribute("carLicencePlateList", carService.getCarLicencePlateList());
        model.addAttribute("carRegistrationCodeList", carService.getCarRegistrationCodeList());
        model.addAttribute("carClassList", CarClass.values());
        model.addAttribute("fuelTypeList", FuelType.values());
        model.addAttribute("brandList", brandService.getBrandList());
        return "car/addUpdateForm";
    }

    @PostMapping("/addOrUpdate")
    public String addUpdateBrand(
            Model model,
            @RequestParam(value = "brandId") int brandId,
            @RequestParam(value = "imageFile", required = false) MultipartFile multipartFile,
            @RequestParam(value = "oldLicencePlate", required = false) String oldLicencePlate,
            @RequestParam(value = "oldRegistrationCode", required = false) String oldRegistrationCode,
            @ModelAttribute Car car) throws Exception {
        CarId oldCarId = null;
        if(oldLicencePlate != null && oldRegistrationCode != null){
            oldCarId = new CarId(oldLicencePlate, oldRegistrationCode);
        }
        if(oldCarId != null && !car.getId().equals(oldCarId) && carService.findCar(car.getId()) != null){
            return "redirect:/";
        }
        if(!multipartFile.getOriginalFilename().isBlank()) {
            if(oldCarId != null){
                HashMap options = new HashMap();
                options.put("invalidate", true);
                String[] splittedCarImg = carService.findCar(oldCarId).getImg().split("/");
                String publicId = splittedCarImg[splittedCarImg.length-1].split("\\.")[0];
                cloudinaryConfig.uploader().destroy(publicId, options);
            }
            File img = convertMultiPartToFile(multipartFile);
            Map uploadResult = cloudinaryConfig.uploader().upload(img, ObjectUtils.emptyMap());
            car.setImg(uploadResult.get("url").toString());
        } else {
            car.setImg(carService.findCar(oldCarId).getImg());
        }
        car.setBrand(brandService.findBrand(brandId));
        carService.addOrUpdateCar(car);
        if(oldCarId != null && !car.getId().equals(oldCarId)){
            carService.deleteCar(oldCarId);
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Car car) throws IOException{
        HashMap options = new HashMap();
        options.put("invalidate", true);
        String[] splittedCarImg = carService.findCar(car.getId()).getImg().split("/");
        String publicId = splittedCarImg[splittedCarImg.length-1].split("\\.")[0];
        cloudinaryConfig.uploader().destroy(publicId, options);
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

    public Cloudinary getCloudinaryConfig() {
        return cloudinaryConfig;
    }

    @Autowired
    public void setCloudinaryConfig(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void showCloudinaryImages() throws Exception {
        ApiResponse result = cloudinaryConfig.search()
                .expression("resource_type:image")
                .execute();
        ArrayList images = (ArrayList) result.values().toArray()[0];
        for (int i = 0; i < images.size(); i++) {
            System.out.println(images.get(i));
        }
    }
}
