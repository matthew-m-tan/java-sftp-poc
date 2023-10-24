package src.com.example;

import com.jcraft.jsch.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.OpenMode;
import net.schmizz.sshj.sftp.RemoteFile;
import net.schmizz.sshj.sftp.SFTPClient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.EnumSet;

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
            final JSch jsch = new JSch();
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

    @SuppressWarnings("deprecation")
    public void sftpWithSSHJ(){
        final SSHClient sshj = new SSHClient();
        try {
            try {
                sshj.loadKnownHosts();
                sshj.connect(this.host);
                String password = new String(System.console().readPassword("Enter password: "));
                sshj.authPassword(this.username, password);
                final SFTPClient sftpClient = sshj.newSFTPClient();
                try {
                    RemoteFile rf = sftpClient.open("SSHJ_TEST", EnumSet.of(OpenMode.CREAT, OpenMode.WRITE));
                    OutputStream os = rf.new RemoteFileOutputStream(0, 10); // Honestly not sure what the point of these params are
                    os.write(("Hello World  (Written at " + new Date().toLocaleString() + ")\n\n").getBytes());
                }catch(Exception err){
                    System.err.println("Couldn't open stream");
                } finally {
                    sftpClient.close();
                }
            } catch (IOException err) {
                System.err.println("Could not connect (or close maybe) to host " + this.host);
            } finally {
                sshj.disconnect();
            }
        }catch(IOException err) {
            System.err.println("Failed to disconnect from the stream. Should we be concerned?");
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
