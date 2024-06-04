    package com.gxxstudy.mapper;

    import com.baomidou.mybatisplus.core.conditions.Wrapper;
    import com.baomidou.mybatisplus.core.mapper.BaseMapper;
    import com.gxxstudy.model.User;
    import org.apache.ibatis.annotations.Mapper;
    import org.springframework.stereotype.Repository;

    public interface UserMapper extends BaseMapper<User> {
    }