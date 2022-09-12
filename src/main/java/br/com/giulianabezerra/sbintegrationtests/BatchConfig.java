package br.com.giulianabezerra.sbintegrationtests;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job job(Step step) {
    return jobBuilderFactory
        .get("job")
        .start(step)
        .build();
  }

  @Bean
  public Step step(ItemReader<Pessoa> reader, ItemWriter<Pessoa> writer) {
    return stepBuilderFactory
        .get("step")
        .<Pessoa, Pessoa>chunk(1)
        .reader(reader)
        .writer(writer)
        .build();
  }

  @Bean
  public ItemReader<Pessoa> reader() {
    return new FlatFileItemReaderBuilder<Pessoa>()
        .name("reader")
        .resource(new FileSystemResource("files/pessoas.csv"))
        .comments("--")
        .delimited()
        .names("nome", "email", "dataNascimento", "idade", "id")
        .targetType(Pessoa.class)
        .build();
  }

  @Bean
  public ItemWriter<Pessoa> writer() {
    return items -> items.forEach(System.out::println);
  }
}
