package tk.mybatis.simple.test;

import tk.mybatis.simple.domain.SysUser;

import java.util.List;

public interface UserMapper {
    List<SysUser> selectAll();
}
