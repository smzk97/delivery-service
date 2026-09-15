package com.smzk.delivery_service.service;


import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.CategoryInsertDTO;
import com.smzk.delivery_service.dto.CategoryQueryPageDTO;
import com.smzk.delivery_service.dto.CategoryUpdateDTO;
import com.smzk.delivery_service.entity.Category;
import com.smzk.delivery_service.vo.PageResultVO;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void categoryUpdate(CategoryUpdateDTO categoryUpdateDTO);

    PageResultVO categoryQueryPage(CategoryQueryPageDTO categoryQueryPageDTO);

    void categoryConvertStatus(Integer status, Integer id);

    void categoryInsert(CategoryInsertDTO categoryInsertDTO);

    void categoryDelete(Integer id);

    List<Category> categoryQueryType(Integer type);
}
