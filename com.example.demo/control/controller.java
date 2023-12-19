package com.example.demo.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.Dto.MailDto;
import com.example.demo.Service.EmailService;

@Controller
public class controller {
	
	private final EmailService emailService;
	 
    public controller(EmailService emailService) {
        this.emailService = emailService;
    }
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	String getindex() {
		return "index.html";
	}
	@RequestMapping(value = "gaid")
	String gaid() {
		return "action_mode.html";
	}
	@RequestMapping(value = "/devinfo")
	String devinfo(Model mo) {
		ArrayList<String> array_memory = new ArrayList<>();
		ArrayList<String> array_cpu = new ArrayList<>();
		ArrayList<String> array_gpu = new ArrayList<>();
	
		try {
			
			//cmd를 통해 Memory,Cpu,gpu 접근
			Process info_memory = Runtime.getRuntime().exec("wmic os get TotalVisibleMemorySize"); //Total MEMORY
			Process info_memory_free = Runtime.getRuntime().exec("wmic os get FreePhysicalMemory"); //Free MEMORY
			Process info_cpu = Runtime.getRuntime().exec("wmic cpu get /format:list"); //CPU
			Process info_gpu = Runtime.getRuntime().exec("nvidia-smi -q -d memory"); //GPU
			
			//출력 데이터 버퍼에 저장
			BufferedReader reader_memory = new BufferedReader(new InputStreamReader(info_memory.getInputStream(),Charset.forName("MS949"))); 
			BufferedReader reader_memory_free = new BufferedReader(new InputStreamReader(info_memory_free.getInputStream(),Charset.forName("MS949")));
			BufferedReader reader_cpu = new BufferedReader(new InputStreamReader(info_cpu.getInputStream(),Charset.forName("MS949")));
			BufferedReader reader_gpu = new BufferedReader(new InputStreamReader(info_gpu.getInputStream(),Charset.forName("MS949")));
			
			    //한줄씩읽어서 배열에 저장
		        String line_memory = null;
		        while ((line_memory = reader_memory.readLine()) != null) 
		        {
		            array_memory.add(line_memory);
		        }
		        
		        String line_memory_free = null;
		        while ((line_memory_free = reader_memory_free.readLine()) != null) 
		        {
		            array_memory.add(line_memory_free);
		        }
		        
		        String line_cpu = null;
		        while ((line_cpu = reader_cpu.readLine()) != null) 
		        {
		            array_cpu.add(line_cpu);
		        }
		        
		        String line_gpu = null;
		        while ((line_gpu = reader_gpu.readLine()) != null) 
		        {
		            array_gpu.add(line_gpu);
		        }
		        
		        //split,index이용하여 데이터 정재
		        String str_gpu_total = array_gpu.get(10).split(":")[1].split("M")[0];
		        String str_gpu_used = array_gpu.get(11).split(":")[1].split("M")[0];
		        
		        float flo_gpu_total = Float.parseFloat(str_gpu_total);
		        float flo_gpu_used = Float.parseFloat(str_gpu_used);
		        int int_gpu_percent =(int)(flo_gpu_used/flo_gpu_total*100);
		          
		        String str_cpu_percent = array_cpu.get(56).split("=")[1];
		        int int_cpu_percent = Integer.parseInt(str_cpu_percent);	
		        
		        String str_memory_total = array_memory.get(2).split(" ")[0];
		        String str_memory_free = array_memory.get(8).split(" ")[0];
		        
		        float flo_memory_total = Float.parseFloat(str_memory_total);
		        float flo_memory_free = Float.parseFloat(str_memory_free);
		        int int_memory_percent = (int)((flo_memory_total-flo_memory_free)/flo_memory_total*100);
		        
		       
		        mo.addAttribute("gpu_percent", int_gpu_percent);
		        mo.addAttribute("cpu_percent", int_cpu_percent);
		        mo.addAttribute("memory_percent", int_memory_percent);
		        
		        //cpu 세부정보
		        ArrayList<String> array_cpu_detail = new ArrayList<>();
		        array_cpu_detail.add(array_cpu.get(62).split("=")[1]); //cpu Name
		       
		        mo.addAttribute("cpu_detail", array_cpu_detail);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return "dev_info.html";
	}
	@RequestMapping(value = "jog")
	String jog() {
		return "jog.html";
	}
	@RequestMapping(value = "setting", method=RequestMethod.GET)
	String setting() {
		return "/setting.html";
	}

    @RequestMapping(value="setting", method=RequestMethod.POST)
    public String sendMail2(MailDto mailDto) {
       
    	emailService.sendSimpleMessage(mailDto);
        System.out.println("메일 전송 완료");
        
        return "/setting.html";
    }
	@RequestMapping(method = RequestMethod.POST, value = "login")
	String postindex() {
		
		database db = new database();
		db.data();
		
		String SQL = "SELECT * FROM LOGIN";
		
		try {
			ResultSet re = db.data().executeQuery(SQL);
			while(re.next()) {
				System.out.print(re.getString("ID"));
				System.out.print(re.getString("PW"));
			}
			
		}
		catch(Exception e) {e.printStackTrace();}
		
		return "action_mode.html";
	}
}
