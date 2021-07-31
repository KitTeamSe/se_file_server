package com.se.fileserver.v1.application.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import com.se.fileserver.v1.file.domain.model.File;
import java.util.Random;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hamcrest.MatcherAssert;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

@DataJpaTest
public class FileTest {

  @Qualifier("fileJpaRepository")
  @Autowired
  private JpaRepository jpaRepository;

  @Test
  void service_길이가_40자_초과인_경우() {
    // given
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    String downloadUrl = "downloadUrl";
    String service = "service000service000service000service000service000service000service000";
    String fileType = "image/png";
    String originalName = "originalName";
    String saveName = "saveName";
    Long size = 1L;

    // when
    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    Set<ConstraintViolation<File>> violations = validator.validate(file);

    // then
    assertThat(violations).isNotEmpty();
    for (ConstraintViolation<File> violation : violations) {
      System.err.println(violation.getMessage());
    }
    validatorFactory.close();
  }

  @Test
  void service_길이가_2자_미만인_경우() {
    // given
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    String downloadUrl = "downloadUrl";
    String service = "j";
    String fileType = "image/png";
    String originalName = "originalName";
    String saveName = "saveName";
    Long size = 1L;

    // when
    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    Set<ConstraintViolation<File>> violations = validator.validate(file);

    // then
    assertThat(violations).isNotEmpty();
    for (ConstraintViolation<File> violation : violations) {
      System.err.println(violation.getMessage());
    }
    validatorFactory.close();
  }

  @Test
  void originalName이_255자_초과인_경우() {
    // given
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Random random = new Random();
    String downloadUrl = "downloadUrl";
    String service = "service";
    String fileType = "image/png";
    String originalName = random.ints('a', 'z').limit(256)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
    String saveName = "saveName";
    Long size = 1L;

    // when
    File file = new File(downloadUrl, service, fileType, originalName, saveName, size);
    Set<ConstraintViolation<File>> violations = validator.validate(file);

    // then
    assertThat(violations).isNotEmpty();
    for (ConstraintViolation<File> violation : violations) {
      System.err.println(violation.getMessage());
    }
    validatorFactory.close();
  }

  @Test
  void SAVENAME과_DOWNLOADURL이_UNIQUE하지_않은_경우() {
    String downloadUrl = "downloadUrl";
    String service = "service";
    String fileType = "image/png";
    String originalName = "originalName";
    String saveName = "saveName";
    Long size = 1L;

    File file1 = new File(downloadUrl, service, fileType, originalName, saveName, size);
    File file2 = new File(downloadUrl, service, fileType, originalName, saveName, size);

    try {
      jpaRepository.save(file1);
      jpaRepository.save(file2);
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
    }
  }

  @Test
  void NULLABLE이_FALSE인_속성이_NULL인_경우() {
    // given
    File file1 = new File(null, null, null, null, null, null);

    try {
      // when
      jpaRepository.save(file1);
    } catch (DataIntegrityViolationException e) {
      // then
      MatcherAssert.assertThat(e.getMessage(),
          is("could not execute statement; SQL [n/a]; constraint [null]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"));
    }
  }
}
