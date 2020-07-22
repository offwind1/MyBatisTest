package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import tk.mybatis.simple.Provider.PrivilegeProvider;
import tk.mybatis.simple.domain.SysPrivilege;

public interface PrivilegeMapper {

    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "privilegeName", column = "privilege_name"),
            @Result(property = "privilegeUrl", column = "privilege_url"),
    })
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    SysPrivilege selectById(Long id);
}

