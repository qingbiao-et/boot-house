package com.etoak.mapper;

import com.etoak.bean.Area;
import org.apache.ibatis.annotations.Param;


import java.util.List;
public interface AreaMapper {
    List<Area> queryByPid(@Param("pid") int pid);

    Area queryById(@Param("id") Integer id);
}
