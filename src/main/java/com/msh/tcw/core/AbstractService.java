package com.msh.tcw.core;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void save(T model) {
        mapper.insert(model);
    }

    public void save(List<T> models) {
        for (T t : models) {
            mapper.insertAllColumn(t);
        }
    }

    public void deleteById(Integer id) {
        mapper.deleteById(id);
    }

    public void deleteByIds(String ids) {
        mapper.deleteById(ids);
    }

    public void update(T model) {
        mapper.updateById(model);
    }

    public T findById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<T> findAll() {
        return mapper.selectList(new EntityWrapper<T>());
    }
}
