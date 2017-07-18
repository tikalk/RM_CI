package com.tikal.rm.ci;

import com.tikal.rm.ci.controller.CiController;
import com.tikal.rm.ci.service.JenkinsApi;
import com.tikal.rm.ci.service.JobType;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CiApplicationTests {

    @Autowired
    JenkinsApi jenkinsApi;
	@Autowired
	private CiController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
@Ignore
	public void jenkinsApi() throws IOException, InterruptedException {


		String url = "https://github.com/tikalk/RM_CI.git";
		String command = "mvn clean install";

		jenkinsApi.runBuild(JobType.JAVA,url,command);

	}
}
