package com.smzk.delivery_service.controller.user;

import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.entity.user.AddressBook;
import com.smzk.delivery_service.service.user.UserAddressBookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
public class UserAddressBookController {

    private UserAddressBookService userAddressBookService;
    @Autowired
    public UserAddressBookController(UserAddressBookService userAddressBookService){
        this.userAddressBookService = userAddressBookService;
    }

    @PostMapping
    public Result UserAddressBookInsert(@Valid @RequestBody AddressBook addressBook){
        userAddressBookService.userAddressBookInset(addressBook);
        return Result.Success();
    }

    @GetMapping("/list")
    public Result UserAddressBookQuery(){
        List<AddressBook> addressBookList = userAddressBookService.userAddressBookQuery();
        return Result.Success(addressBookList);
    }

    @GetMapping("/default")
    public Result UserAddressBookQueryDefault(){
        AddressBook addressBook = userAddressBookService.userAddressBookQueryDefault();
        return Result.Success(addressBook);
    }

    @GetMapping("/{id}")
    public Result UserAddressBookQueryById(@Valid @PathVariable @NotNull Integer id){
        List<AddressBook> addressBook = userAddressBookService.userAddressBookQueryById(id);
        return Result.Success(addressBook);
    }

    @PutMapping
    public Result UserAddressBookUpdate(@RequestBody AddressBook addressBook){
        userAddressBookService.userAddressBookUpdate(addressBook);
        return Result.Success();
    }

    @PutMapping("/default")
    public Result UserAddressBookSetDefault(@Valid @NotNull Integer id){
        userAddressBookService.userAddressBookSetDefault(id);
        return Result.Success();
    }

    @DeleteMapping
    public Result UserAddressBookDelete(@Valid @NotNull Integer id){
        userAddressBookService.userAddressBookDelete(id);
        return Result.Success();
    }

}
