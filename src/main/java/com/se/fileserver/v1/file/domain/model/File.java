package com.se.fileserver.v1.file.domain.model;

import com.se.fileserver.v1.common.domain.model.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.Getter;

@Entity
@Getter
public class File extends BaseEntity {
  // TODO: 파일 태스크
  // 저장 디렉토리 -> c:/file/se/

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 255)
  @Column(nullable = false, unique = true)
  private String downloadUrl;

  @Size(min = 2, max = 40)
  @Column(nullable = false)
  private String service;     // se/ pickple/ ..

  @Size(min = 2, max = 40)
  @Column(nullable = false)
  private String fileType;

  @Size(max = 255)
  @Column(nullable = false)
  private String originalName;

  @Size(max = 255)
  @Column(nullable = false, unique = true)
  private String saveName;

  @Column(nullable = false)
  private Long size;

  /* constructor */
  protected File() {}

  public File(@Size(max = 255) String downloadUrl,
      @Size(min = 2, max = 40) String service,
      @Size(min = 2, max = 40) String fileType,
      @Size(max = 255) String originalName,
      @Size(max = 255) String saveName, Long size) {
    this.downloadUrl = downloadUrl;
    this.service = service;
    this.fileType = fileType;
    this.originalName = originalName;
    this.saveName = saveName;
    this.size = size;
  }
}
