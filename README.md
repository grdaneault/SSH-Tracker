SSH-Tracker
===========

A simple ssh "server" that logs failed login attempts and show simple statistics.

Project Goals
-------------
* Show a realtime graph of failed login attempts
* Show most frequent source IPs, usernames, and passwords
* Generate IP tables rules for blocking known offenders

Running SSH Tracker
-------------------

compile with maven and build the assembly

java -cp ssh-tracker-1.0-SNAPSHOT-jar-with-dependencies.jar com.greggernaut.sshtracker.StartServer
