package com.smzk.delivery_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smzk.delivery_service.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
