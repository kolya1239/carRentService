package com.example.carrentservice.controllers;

import com.example.carrentservice.entities.Brand;
import com.example.carrentservice.exceptions.NoSuchElementInDatabaseException;
import com.example.carrentservice.services.BrandService;
import com.example.carrentservice.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/brand")
public class BrandController {

    BrandService brandService;
    CarService carService;

    @RequestMapping(value = {"/add","/updateForm"}, method = {RequestMethod.GET})
    public String addUpdate(Model model, @RequestParam(value = "selector", required = false) String name, HttpServletRequest httpServletRequest) throws NoSuchElementInDatabaseException {
        if(httpServletRequest.getRequestURI().contains("add")) {
            model.addAttribute("brand", new Brand());
            model.addAttribute("addOrUpdateField", "Добавление");
            model.addAttribute("exists", false);
        } else {
            model.addAttribute("brand", brandService.findBrandByName(name));
            model.addAttribute("addOrUpdateField", "Обновление");
            model.addAttribute("exists", true);
        }
        return "/brand/addUpdateForm";
    }

    @PostMapping("/addOrUpdate")
    public String addUpdateBrand(Model model, @ModelAttribute Brand brand){
        brandService.addOrUpdateBrand(brand);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBrand(Model model, @ModelAttribute Brand brand) throws NoSuchElementInDatabaseException {
        brandService.deleteBrand(brand.getId());
        return "redirect:/";
    }

    @RequestMapping("/{brandName}")
    public String getBrandInfo(@PathVariable String brandName, Model model) throws NoSuchElementInDatabaseException {
        Brand brand = brandService.findBrandByName(brandName);
        model.addAttribute("brand", brand);
        model.addAttribute("carList", brand.getCars());
        model.addAttribute("carClassList", brandService.getExistingCarClassList(brand));
        return "brand/index";
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
}
