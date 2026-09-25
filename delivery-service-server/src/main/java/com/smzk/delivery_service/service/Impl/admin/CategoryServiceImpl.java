package com.smzk.delivery_service.service.Impl.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.smzk.delivery_service.dto.CategoryInsertDTO;
import com.smzk.delivery_service.dto.CategoryQueryPageDTO;
import com.smzk.delivery_service.entity.admin.Category;
import com.smzk.delivery_service.mapper.admin.CategoryMapper;
import com.smzk.delivery_service.service.admin.CategoryService;
import com.smzk.delivery_service.utils.ThreadLocalUtils;
import com.smzk.delivery_service.vo.admin.PageResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<BaseMapper<Category>,Category> implements CategoryService {

    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper){
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void categoryUpdate(CategoryInsertDTO categoryUpdateDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDTO,category);
        this.update(category,new LambdaUpdateWrapper<Category>().eq(Category::getId,categoryUpdateDTO.getId()));
    }

    @Override
    public PageResultVO categoryQueryPage(CategoryQueryPageDTO categoryQueryPageDTO) {
        IPage<Category> categoryIPage = new Page<>(categoryQueryPageDTO.getPage(),categoryQueryPageDTO.getPageSize());
        String name = categoryQueryPageDTO.getName();
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<Category>()
                .like(name != null && !name.isEmpty(),Category::getName,'%'+name+'%')
                .eq(Category::getType,categoryQueryPageDTO.getType());
        IPage<Category> page = this.page(categoryIPage, lambdaQueryWrapper);
        List<Integer> lists = page.getRecords().stream().map(Category::getId).toList();
        List<Category> categories = this.listByIds(lists);
        return new PageResultVO(page.getTotal(),categories);
    }

    @Override
    public void categoryConvertStatus(Integer status, Integer id) {
        LambdaUpdateWrapper<Category> lambdaUpdateWrapper = new LambdaUpdateWrapper<Category>()
                .set(Category::getUpdateTime,LocalDateTime.now())
                .set(Category::getUpdateUser,ThreadLocalUtils.getEmployee().getId())
                .set(Category::getStatus,status)
                .eq(Category::getId,id);
        this.update(lambdaUpdateWrapper);
    }

    @Override
    public void categoryInsert(CategoryInsertDTO categoryInsertDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryInsertDTO,category);
        this.save(category);
    }

    @Override
    public void categoryDelete(Integer id) {
        this.removeById(id);
    }

    @Override
    public List<Category> categoryQueryType(Integer type) {
        return this.list(new LambdaUpdateWrapper<Category>().eq(Category::getType,type));
    }
}
