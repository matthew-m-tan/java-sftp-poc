package src.com.example;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class SftpClient {
    private String username, host;

    public SftpClient(String username, String host) {
        this.username = username;
        this.host = host;
    }


    @SuppressWarnings("deprecation")
    public void sftpWithJsch() {
        try {
            // Enable or disable strict checking of known_hosts here. Existing CS seems not to check known_hosts (vulnerable to MITM)
            JSch jsch = new JSch();
            JSch.setConfig("StrictHostKeyChecking", "yes");
            jsch.setKnownHosts("~/.ssh/known_hosts");
            Session session = jsch.getSession(this.username, this.host, 22);

            // Read user input and authenticate
            String password = new String(System.console().readPassword("Enter password: "));
            session.setPassword(password);
            session.connect();
            ChannelSftp channel = (ChannelSftp)session.openChannel( "sftp" );
            channel.connect();

            // Send payload over the wire & write to file on the server
            OutputStream out = channel.put("SFTP_TEST");
            out.write(("Hello World  (Written at " + new Date().toLocaleString() + ")\n\n").getBytes());

            // Close out the connection
            channel.exit();
            session.disconnect();
        }catch(JSchException err) {
            System.err.println("Could not open the ssh session: " + err);
        }catch(SftpException err) {
            System.err.println("Sftp channel could not be established: " + err);
        }catch(IOException err)  {
            System.err.println("Error writing to the opened sftp stream: " + err);
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username =  username;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
