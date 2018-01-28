package com;

import com.spring.dao.SysUserDao;
import com.spring.model.SysUser;
import com.spring.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring.xml")
public class SysUserDaoTest {

    @Autowired
    private SysUserDao sysUserDao;

    @Test
    public void insertTest() {
        SysUser sysUser = new SysUser("aa", "bb");
        sysUserDao.insert(sysUser);
    }
}
