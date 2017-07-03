package com.iqmsoft.boot.batch.integration;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.iqmsoft.boot.batch.integration.BatchConfig;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = { TestConfig.class, BatchConfig.class })
public class BatchTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testSampleJob() throws Exception {
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("file_path", "src/test/resources/sample.dat");
		
		jobParametersBuilder.addDate("sample", new Date());
		JobExecution jobExecution = jobLauncherTestUtils.
				launchJob(jobParametersBuilder.toJobParameters());
		System.out.println(jobExecution.getExitStatus());
	}
}