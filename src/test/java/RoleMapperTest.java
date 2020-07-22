import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.domain.SysRole;
import tk.mybatis.simple.mapper.RoleMapper;

import java.util.List;

public class RoleMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = mapper.selectById(1L);
                Assert.assertNotNull(role);
                Assert.assertEquals("管理员", role.getRoleName());
            }
        }, RoleMapper.class);

    }

    @Test
    public void testSelectById2() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = mapper.selectById2(1L);
                Assert.assertNotNull(role);
                Assert.assertEquals("管理员", role.getRoleName());
            }
        }, RoleMapper.class);
    }

    @Test
    public void testSelectAll() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                List<SysRole> roleList = mapper.selectAll();
                Assert.assertNotNull(roleList);
                Assert.assertTrue(roleList.size() > 0);
                Assert.assertNotNull(roleList.get(0).getRoleName());
            }
        }, RoleMapper.class);
    }

    @Test
    public void testInsert() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = new SysRole();
                role.setId(5L);
                role.setEnabled(1);
                role.setCreateBy(1L);
                mapper.insert(role);
            }
        }, RoleMapper.class);
    }

    @Test
    public void testInsert2() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = new SysRole();
                role.setId(5L);
                role.setEnabled(1);
                role.setCreateBy(1L);
                mapper.insert2(role);
            }
        }, RoleMapper.class);
    }

    @Test
    public void testInsert3() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = new SysRole();
                role.setId(5L);
                role.setEnabled(1);
                role.setCreateBy(1L);
                mapper.insert3(role);
            }
        }, RoleMapper.class);
    }

    @Test
    public void testUpdateById() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                SysRole role = new SysRole();
                role.setId(1L);
                role.setEnabled(1);
                role.setCreateBy(1L);
                mapper.updateById(role);
            }
        }, RoleMapper.class);
    }

    @Test
    public void testDeleteById() {
        testSession(new ITest<RoleMapper>() {
            @Override
            public void test(RoleMapper mapper) {
                mapper.deleteById(1L);
            }
        }, RoleMapper.class);
    }





}
