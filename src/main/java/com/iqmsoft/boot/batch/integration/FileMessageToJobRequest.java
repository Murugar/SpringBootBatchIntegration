package com.iqmsoft.boot.batch.integration;

import java.io.File;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

public class FileMessageToJobRequest {

	private Job job;

	private String fileParameterName = "input.file.name";

	public void setFileParameterName(String fileParameterName) {
		this.fileParameterName = fileParameterName;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	@Transformer
	public JobLaunchRequest toRequest(Message<File> message) {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

		jobParametersBuilder.addString(fileParameterName, 
				message.getPayload().getAbsolutePath());
		
		jobParametersBuilder.addDate("test", new Date());

		return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
	}
}