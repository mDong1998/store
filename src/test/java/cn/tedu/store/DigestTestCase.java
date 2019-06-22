package cn.tedu.store;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DigestTestCase {

	@Test
	public void md5() {
		String str = "1234567";
		String salt = UUID.randomUUID().toString();
		System.err.println(salt);
		String md5 = DigestUtils.md5DigestAsHex((salt + str).getBytes());
		System.err.println(md5);
	}

}