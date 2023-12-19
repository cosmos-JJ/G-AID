package com.example.demo.Service;

import com.example.demo.Dto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor // 모든 변수들을 인수로 받는 생성자를 만들어내는 어노테이션
public class EmailService {
	
	@Autowired // 의존성 주입 어노테이션
	private JavaMailSender emailSender;
	// JavaMailSender = Java Mail API를 이용
			
    public void sendSimpleMessage(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage(); 
	// SimpleMailMessage = 단순 텍스트 구성 메일 메세지 생성할 때 이용
        
	message.setFrom("ks3226643@gmail.com"); // 메일 작성자
        message.setTo(mailDto.getAddress()); // 메일 수신자
        message.setSubject(mailDto.getTitle()); // 메일 제목
        message.setText(mailDto.getContent()); // 메일 내용
        emailSender.send(message); // 메일 전송
    }
}
