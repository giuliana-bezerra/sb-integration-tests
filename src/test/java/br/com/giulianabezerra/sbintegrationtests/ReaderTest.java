package br.com.giulianabezerra.sbintegrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReaderTest {
  @Autowired
  private ItemReader<Pessoa> reader;

  @Test
  public void testFileReader()
      throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
    FlatFileItemReader<Pessoa> fileReader = (FlatFileItemReader<Pessoa>) reader;
    fileReader.open(new ExecutionContext());

    Pessoa pessoa;
    int id = 1;
    while ((pessoa = fileReader.read()) != null) {
      assertEquals(id, pessoa.getId());
      id++;
    }
  }
}
