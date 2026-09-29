package com.smzk.delivery_service.service.Impl.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.entity.user.AddressBook;
import com.smzk.delivery_service.entity.user.UserThreadLocal;
import com.smzk.delivery_service.enums.ErrorCode;
import com.smzk.delivery_service.exception.BusinessException;
import com.smzk.delivery_service.mapper.user.UserAddressBook;
import com.smzk.delivery_service.service.user.UserAddressBookService;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressBookServiceImpl extends ServiceImpl<BaseMapper<AddressBook>,AddressBook> implements UserAddressBookService {
    @Override
    public void userAddressBookInset(AddressBook addressBook) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        addressBook.setUserId(user.getId());
        this.save(addressBook);
    }

    @Override
    public List<AddressBook> userAddressBookQuery() {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        return this.list(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getUserId,user.getId()));
    }

    @Override
    public AddressBook userAddressBookQueryDefault() {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        return this.getOne(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getUserId,user.getId()).eq(AddressBook::getIsDefault,1));
    }

    @Override
    public List<AddressBook> userAddressBookQueryById(Integer id) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        return this.list(new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getId,id));
    }

    @Override
    public void userAddressBookUpdate(AddressBook addressBook) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        this.update(addressBook,new LambdaQueryWrapper<AddressBook>().eq(AddressBook::getId,addressBook.getId()));
    }

    @Override
    public void userAddressBookSetDefault(Integer id) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        this.update(new LambdaUpdateWrapper<AddressBook>().eq(AddressBook::getUserId,user.getId()).set(AddressBook::getIsDefault,0));
        this.update(new LambdaUpdateWrapper<AddressBook>().eq(AddressBook::getId,id).set(AddressBook::getIsDefault,1));
    }

    @Override
    public void userAddressBookDelete(Integer id) {
        UserThreadLocal user = ThreadLocalUtils.getUser();
        if(user == null){
            throw new BusinessException(ErrorCode.BUSINESS_FAIL,"user上下文变量为空");
        }
        this.removeById(id);
    }
}
