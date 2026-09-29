package com.smzk.delivery_service.service.user;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.entity.user.AddressBook;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface UserAddressBookService extends IService<AddressBook> {
    void userAddressBookInset(AddressBook addressBook);

    List<AddressBook> userAddressBookQuery();

    AddressBook userAddressBookQueryDefault();

    List<AddressBook> userAddressBookQueryById(@Valid @NotNull Integer id);

    void userAddressBookUpdate(AddressBook addressBook);

    void userAddressBookSetDefault(@Valid @NotNull Integer id);

    void userAddressBookDelete(@Valid @NotNull Integer id);
}
