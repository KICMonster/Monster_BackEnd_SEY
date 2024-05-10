package com.eyy.test.controller;

public class BasicLoginController {

    /*
    명준 작업_기본로그인 구현
    로컬 DB연결 - 테스트 계정 insert해서 작업
    프론트에서 로그인 요청 보내는 방법 자유.
    대신 계정없을경우 회원가입페이지로 보내는 로직과 jwt토큰 방식을 사용하므로 jwt프로바이더 클래스와 필터클래스는 로그인 과정에서 연결해야함.
    추가 로직 필요시 JWTProvider클래스에 기본 로직을 추가하거나 DTO를 따로 사용해도 상관없음.
    but 최종적으로는 member테이블에 저장된 정보를 가져와서 로그인을 진행해야함.
    보안설정 문제있을시 sequrityconfig클래스에서 formLogin부분에 보안설정 추가.
    로그인 성공시 jwt토큰을 프론트로 반환해주고 반환해준 jwt토큰을 프론트에서 로컬스토리지에 저장하는 로직 추가.
     */
}
