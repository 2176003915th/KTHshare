package idusw.leafton;

import idusw.leafton.config.SshTunnel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class LeaftonApplication {

    public static void main(String[] args) {
        SshTunnel sshTunnel = new SshTunnel();
        try {
            sshTunnel.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(LeaftonApplication.class, args);
    }

}

