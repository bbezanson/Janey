# Janey is a simple webapp for bug/issue tracking on software projects.

## Status - March 11, 2010
Janey is currently under heavy development and is no where near ready for deployment for real work usage. Here are some tips for building janey.

1. First you need to get the sources, obviously you did that because you are reading this.  When that is complete, you need to build using maven. I use a mac for development and here is the maven version that I am using

- Apache Maven 2.2.0 (r788681; 2009-06-26 08:04:01-0500)
- Java version: 1.6.0_17
- Java home: /System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home
- Default locale: en_US, platform encoding: MacRoman
- OS name: "mac os x" version: "10.6.2" arch: "x86_64" Family: "mac"

1. I am prettry sure that everything that this project uses is in the maven central repo accept for dojo. You will need to download the dojo 1.4.1 source from dojotoolkit.org uncompress it from the bin.gz format and recompress it as a zip. Then upload it to your local repository. mvn should give you the command to perform the upload.

- see the maven wesite for info on how to use maven.

1. when you have it building a completed war (located in janey-webapp/target) you can then deploy that to your server. As of this writing this has only been used on tomcat.

1. next you need to set up the db, this has only been tested with postgresql and the setup script is in etc/.

1. When the webapp loads, go to the misc tab in the main page and follow those instructions.

## Some Goals:
- add support for hsqldb for extremely easy deployment (@Brian)
- add support for LDAP (as well as internal user mgmt) (@Brian)
- add issue/bug searching. (@Wade)
- add security for admin functions. (@Wade)
- still a lot to do with UI (@Wade)

Brian Bezanson: brian@bezanson.org
Wade Girard: wade@tikiwade.com
