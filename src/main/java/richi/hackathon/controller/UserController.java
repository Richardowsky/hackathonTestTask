package richi.hackathon.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import richi.hackathon.models.*;
import richi.hackathon.repos.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("userinfo")
public class UserController {
    private ResponseEntity responseEntity;
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final CountryRepo countryRepo;
    private final CityRepo cityRepo;
    private final StreetRepo streetRepo;

    @Autowired
    public UserController(UserRepo userRepo, AddressRepo addressRepo, CountryRepo countryRepo, CityRepo cityRepo, StreetRepo streetRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.countryRepo = countryRepo;
        this.cityRepo = cityRepo;
        this.streetRepo = streetRepo;
    }

    @GetMapping
    @JsonView(Views.FullUser.class)
    public List<String> list() {
        List<User> userList = userRepo.findAll();
        List<String> userString = new ArrayList<>();
        for (User userDB : userList) {
            userString.add(userDB.printUserInfo());
        }
        return userString;
    }

    @GetMapping("/get/{firstname}/{lastname}")
    public String getOneUser(@PathVariable String firstname, @PathVariable String lastname) {
        List<User> userListFromDB = userRepo.findAll();
        User user = new User(firstname, lastname);
        for (User userDB : userListFromDB) {
            if (user.toString().equals(userDB.toString())) {
                return response(200) + userDB.printUserInfo();
            }
        }
        return response(204) + " 'User " + firstname + " " + lastname + " doesn't exist'";
    }

    @PostMapping("/add")
    public String create(@RequestParam String firstname,
                         @RequestParam String lastname,
                         @RequestParam String country,
                         @RequestParam String city,
                         @RequestParam String street) {
        List<User> userListFromDB = userRepo.findAll();
        List<Country> countriesFromDB = countryRepo.findAll();
        List<City> cityFromDB = cityRepo.findAll();
        List<Street> streetFromDB = streetRepo.findAll();
        User user = new User();
        Address address = new Address();
        Country country1 = new Country(country);
        boolean counryTest = false;
        for (Country countryDB : countriesFromDB) {
            if (country1.toString().equals(countryDB.toString())) {
                counryTest = true;
                country1 = countryDB;
                break;
            }
        }
        if (!counryTest) {
            return response(406) + " 'Address " + country + " " + city + " " + street + " doesn't exist'";
        }
        City city1 = new City(city);
        boolean cityTest = false;
        for (City cityDB : cityFromDB) {
            if (city1.toString().equals(cityDB.toString())) {
                cityTest = true;
                city1 = cityDB;
                break;
            }
        }
        if (!cityTest) {
            return response(406) + " 'Address " + country + " " + city + " " + street + " doesn't exist'";
        }
        Street street1 = new Street(street);
        boolean streetTest = false;
        for (Street streetDB : streetFromDB) {
            if (street1.toString().equals(streetDB.toString())) {
                streetTest = true;
                street1 = streetDB;
                break;
            }
        }
        if (!streetTest) {
            return response(406) + " 'Address " + country + " " + city + " " + street + " doesn't exist'";
        }
        user.setFirstName(firstname);
        user.setLastName(lastname);
        address.setCountry(country1);
        address.setCity(city1);
        address.setStreet(street1);
        user.setAddress(address);
        for (User userDB : userListFromDB) {
            if (user.toString().equals(userDB.toString())) {
                return response(204) + " 'User " + firstname + " " + lastname + " already exist'";
            }
        }
        addressRepo.save(address);
        userRepo.save(user);
        return response(201) + " 'User " + firstname + " " + lastname + " was created!!'";
    }

    @DeleteMapping("/delete/{firstname}/{lastname}")
    public String delete(@PathVariable String firstname, @PathVariable String lastname) {
        List<User> userListFromDB = userRepo.findAll();
        User user = new User(firstname, lastname);
        for (User userDB : userListFromDB) {
            if (user.toString().equals(userDB.toString())) {
                userRepo.delete(userDB);
                return response(200) + userDB.toString() + " deleted!";
            }
        }
        return response(204) + " 'User " + firstname + " " + lastname + " doesn't exist'";
    }

    private String response(int code){
        return "'status': " + code +" 'message': ";
    }
}
