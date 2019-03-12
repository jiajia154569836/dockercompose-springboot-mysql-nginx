package com.neo.controller;

import com.neo.entity.Visitor;
import com.neo.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class VisitorController {

    @Autowired
    private VisitorRepository repository;
	
    @RequestMapping("/")
    public String index(HttpServletRequest request) throws UnknownHostException {
        String ip=request.getRemoteAddr();
        Visitor visitor=repository.findByIp(ip);
        if(visitor==null){
            visitor=new Visitor();
            visitor.setIp(ip);
            visitor.setTimes(1);
        }else {
            visitor.setTimes(visitor.getTimes()+1);
        }
        repository.save(visitor);
        InetAddress address = InetAddress.getLocalHost();
        //System.out.println(address.getHostName());//主机名
        return "当前服务器名:"+address.getHostName()+"当前服务器ip"+address.getHostAddress()+"I have been seen ip "+visitor.getIp()+" "+visitor.getTimes()+" times.";
    }
}