package src.com.example;

import com.jcraft.jsch.UserInfo;

public class TestUserInfo implements UserInfo {
    private String passphrase;
    private String password;

    @Override

    public String getPassphrase() {
        return this.passphrase;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean promptPassword(String message) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String message) {
        return false;
    }

    @Override
    public boolean promptYesNo(String message) {
        return false;
    }

    @Override
    public void showMessage(String message) {

    }
}
