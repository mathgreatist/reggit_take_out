package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
