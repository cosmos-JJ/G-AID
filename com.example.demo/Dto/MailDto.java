package com.example.demo.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// DTO = 데이터 전송 객체 - 프로세스 사이에서 데이터를 전송하는 객체
@Getter // 접근자
@Setter // 설정자
@NoArgsConstructor // 기본 생성자 어노테이션
public class MailDto {
    private String address;
    private String title;
    private String content;
    
    MailDto(String address, String title, String content)
    {
    	this.address=address;
    	this.title=title;
    	this.content=content;
    }
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content=content;
	}

}
