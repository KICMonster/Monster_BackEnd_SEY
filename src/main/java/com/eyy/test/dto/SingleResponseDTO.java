package com.eyy.test.dto;

public class SingleResponseDTO<T> {
    private T data;

    public SingleResponseDTO(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
// 해당 클래스는 제네릭을 사용하여 다양한 타입의 데이터를 처리할 수 있도록 설계되었습니다.
// 이로 인해, EmailVerificationResult 뿐만 아니라 다른 종류의 데이터도 이 클래스를 통해 API 응답으로 사용할 수 있습니다.
