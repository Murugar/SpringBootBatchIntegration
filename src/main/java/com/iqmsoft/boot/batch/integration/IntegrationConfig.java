package com.iqmsoft.boot.batch.integration;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.integration.launch.JobLaunchingMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileReadingMessageSource.WatchEventType;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;

@Configuration
public class IntegrationConfig {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job firstJob;

	protected DirectChannel inputChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow firstFlow() {
		// @formatter:off
		return IntegrationFlows //
				.from(fileReader(), c -> c.poller(Pollers.fixedDelay(2000)))//
				.channel(inputChannel()) //
				.transform(fileToJobRequest()) //
				.handle(jobLaunchingHandler()) //
				.handle(jobExecution -> {
					System.out.println(jobExecution.getPayload());
				}) //
				.get();
		// @formatter:on
	}

	@Bean
	public MessageSource<File> fileReader() {
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setDirectory(new File("input"));
		source.setFilter(new SimplePatternFileListFilter("*.dat"));
		source.setUseWatchService(true);
		source.setWatchEvents(WatchEventType.CREATE);
		return source;
	}

	@Bean
	FileMessageToJobRequest fileToJobRequest() {
		FileMessageToJobRequest transformer = new FileMessageToJobRequest();
		transformer.setJob(firstJob);
		transformer.setFileParameterName("file_path");
		return transformer;
	}

	@Bean
	JobLaunchingMessageHandler jobLaunchingHandler() {
		JobLaunchingMessageHandler handler = new JobLaunchingMessageHandler(jobLauncher);
		return handler;
	}

	

}