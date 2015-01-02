package com.greggernaut.sshtracker;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.command.UnknownCommand;

/**
 * Created by Greg on 1/2/2015.
 */
public class DummyCommandFactory implements CommandFactory{
    @Override
    public Command createCommand(String s) {
        return new UnknownCommand(s);
    }
}
