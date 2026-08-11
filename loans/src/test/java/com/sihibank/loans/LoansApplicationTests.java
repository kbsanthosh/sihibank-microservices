package com.sihibank.loans;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Skipping integration test during Paketo image build")
class LoansApplicationTests {

	@Test
	void contextLoads() {
	}

}
