package br.com.mvp.cadastroclienteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.com.mvp.cadastroclienteapi")
@EntityScan(basePackages = "br.com.mvp.cadastroclienteapi.model")
public class CadastroclienteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastroclienteApiApplication.class, args);
    }

}
