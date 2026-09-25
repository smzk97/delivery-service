package com.smzk.delivery_service.service.admin;


import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.CategoryInsertDTO;
import com.smzk.delivery_service.dto.CategoryQueryPageDTO;
import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.vo.admin.PageResultVO;

import java.util.List;

public interface CategoryService extends IService<Category> {
    void categoryUpdate(CategoryInsertDTO categoryUpdateDTO);

    PageResultVO categoryQueryPage(CategoryQueryPageDTO categoryQueryPageDTO);

    void categoryConvertStatus(Integer status, Integer id);

    void categoryInsert(CategoryInsertDTO categoryInsertDTO);

    void categoryDelete(Integer id);

    List<Category> categoryQueryType(Integer type);
}
