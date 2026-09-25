package com.smzk.delivery_service.service.admin;

import com.baomidou.mybatisplus.spring.service.IService;
import com.smzk.delivery_service.dto.MealInsertDTO;
import com.smzk.delivery_service.dto.SetmealQueryPageDTO;
import com.smzk.delivery_service.entity.admin.Setmeal;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import com.smzk.delivery_service.vo.admin.SetmealQueryByIdVO;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void setmealInsert(MealInsertDTO mealInsertDTO);

    SetmealQueryByIdVO setmealQueryById(Integer id);

    PageResultVO setmealQueryPage(SetmealQueryPageDTO setmealQueryPageDTO);

    void setmealConvertStatus(Integer status,Integer id);

    void setmealDelete(List<Integer> ids);

    void setmealUpdate(MealInsertDTO mealInsertDTO);
}
