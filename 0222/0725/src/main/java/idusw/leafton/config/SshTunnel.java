package idusw.leafton.config;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;

public class SshTunnel {
    private static final String SSH_HOST = "220.67.173.102";
    private static final int SSH_PORT = 110;
    private static final String SSH_USERNAME = "passion";
    private static final String SSH_PASSWORD = "cometrue";
    private static final String REMOTE_HOST = "127.0.0.1";
    private static final int REMOTE_PORT = 3306;
    private static final int LOCAL_PORT = 3308;

    public void start() throws IOException, JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(SSH_USERNAME, SSH_HOST, SSH_PORT);
        session.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        session.setPortForwardingL(LOCAL_PORT, REMOTE_HOST, REMOTE_PORT);
    }
}
