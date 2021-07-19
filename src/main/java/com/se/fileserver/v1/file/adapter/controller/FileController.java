package com.se.fileserver.v1.file.adapter.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/file-server/v1/")
public class FileController {

  // TODO: 파일 태스크
  // 파일 업로드

  // 파일 멀티 업로드 (파일 업로드 모듈 사용)

  // 파일 다운로드 (url return해주기)

  // 파일 삭제

  // return SuccessResponse ? ResponseEntity ? --> VO DTO 에 물려있어서 우선 VO DTO 확실하게 맞추는게 먼저일
  // Repository듯 --> 도메인 내 Service에서는 Protocol을 바라보고, 인프라의 Repository는 다중상속을 받아서 사용 (인터페이스는 다중상속에 충돌나지 않음)
  // VO DTO

}
