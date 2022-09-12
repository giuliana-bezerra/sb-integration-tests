package br.com.giulianabezerra.sbintegrationtests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
public class JobTest {
  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void testJob() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
    assertEquals(BatchStatus.COMPLETED, stepExecution.getStatus());
    assertEquals(10, stepExecution.getReadCount());
    assertEquals(10, stepExecution.getWriteCount());
  }
}
