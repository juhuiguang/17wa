package com.alienlab;

import com.alienlab.db.AlienEntity;
import com.alienlab.db.Dao;
import com.alienlab.wa17.entity.main.MainTbAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() {
		AlienEntity<MainTbAccount> alienAccount=new AlienEntity<MainTbAccount>();
		Dao dao=new Dao();
		List l=dao.getDataSet("select * from tb_account");
		List result=alienAccount.list2T(l,MainTbAccount.class);
		System.out.println(result);

	}

}
