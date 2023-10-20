package src.com.example;

import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] argv) {
        try {
            // Hardcoded values, these will be parameterized in CS
            String host = "cs-dashboard.rfcode.com", username = "administrator", password = "Let's not hardcode this in plaintext";

            // Enable or disable strict checking of known_hosts here. Existing CS seems not to check known_hosts (vulnerable to MITM)
            JSch jsch = new JSch();
            JSch.setConfig("StrictHostKeyChecking", "yes");
            jsch.setKnownHosts("~/.ssh/known_hosts");
            Session session = jsch.getSession(username, host, 22);

            // Read user input and authenticate
            password = new String(System.console().readPassword("Enter password: "));
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
}