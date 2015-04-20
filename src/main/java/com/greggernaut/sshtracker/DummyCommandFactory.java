package com.greggernaut.sshtracker;

import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.command.UnknownCommand;

/**
 * Don't allow any commands
 */
public class DummyCommandFactory implements CommandFactory{
    @Override
    public Command createCommand(String s) {
        System.out.println("Attempted to run " + s);
        return new UnknownCommand(s);
    }
}
