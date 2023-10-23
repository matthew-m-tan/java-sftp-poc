package src.com.example;

import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import src.com.example.SftpClient;

public class Main {
    @SuppressWarnings("deprecation")
    public static void main(String[] argv) {
        // Hardcoded values, these will be parameterized in CS
        String host = "cs-dashboard.rfcode.com", username = "administrator";
        SftpClient client = new SftpClient(username, host);
        client.sftpWithJsch();
    }

}