package com.greggernaut.sshtracker;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.UserAuth;
import org.apache.sshd.server.auth.UserAuthPassword;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartServer {

    public static void main(String args[]) throws IOException {
        SshServer trap = SshServer.setUpDefaultServer();
        trap.setPort(2222);
        trap.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));

        List<NamedFactory<UserAuth>> userAuthFactories = new ArrayList<NamedFactory<UserAuth>>();
        userAuthFactories.add(new UserAuthPassword.Factory());
        trap.setUserAuthFactories(userAuthFactories);

        trap.setPasswordAuthenticator(new LoggingPasswordAuthenticator());

        trap.setCommandFactory(new DummyCommandFactory());

        trap.start();
    }


}
