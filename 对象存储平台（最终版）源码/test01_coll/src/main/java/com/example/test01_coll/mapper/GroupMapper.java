package com.example.test01_coll.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test01_coll.pojo.Group;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GroupMapper extends BaseMapper<Group> {

    List<Map<Object,Object>> getUserList(int bucketId);

}
