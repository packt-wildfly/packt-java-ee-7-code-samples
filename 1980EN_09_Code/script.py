from org.jboss.as.cli.scriptsupport import CLI
 
cli = CLI.newInstance()
cli.connect()
 
cli.cmd("cd /subsystem=naming")
 
result = cli.cmd(":jndi-view")
response = result.getResponse()
 
print 'JNDI VIEW ======================= '
print response
cli.disconnect()
