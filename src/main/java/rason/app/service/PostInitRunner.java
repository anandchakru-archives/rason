package rason.app.service;

import static rason.app.util.RasonConstant.BEAN_PIR;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service(value = BEAN_PIR)
public class PostInitRunner implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		//anything to do post init.
	}
}