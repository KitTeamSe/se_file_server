# se_file_server

## Docker 이용 가이드

현재 java의 경우 project에서 jar 파일 빌드 이후에 그걸 docker image로 wrapping 하는 방법을 사용중인 것으로 추정됨
* 아마 dependency를 download하는 시간이 아까워 이를 캐싱하기 위함일듯

그래서 아래의 순서에 맞추어 작업해야 테스트가 가능함.

1. 개발을 진행(.java 파일들 수정)
2. gradle을 이용하여 build 진행
3. `make build` 실행 : docker image 만들기
4. `make run` 실행 : app이 도커에서 실행됨, test는 돌아가지 않음

### 테스트를 할 때는?(mysql을 참조하는 integration test)

1. 개발을 진행
2. make up 실행 후 app죽이기 혹은 docker 명령어를 통해 mysql만 올리기
3. test 실행



 
