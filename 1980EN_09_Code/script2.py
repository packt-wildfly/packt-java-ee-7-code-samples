from org.jboss.as.cli.scriptsupport import CLI

cli = CLI.newInstance()
cli.connect()

cli.cmd("cd /core-service=platform-mbean/type=memory/")

result = cli.cmd(":read-resource(recursive=false,proxies=false,include-runtime=true,include-defaults=true)")

response = result.getResponse()
enabled = response.get("result").get("heap-memory-usage")

used = enabled.get("used").asInt()

if used > 512000000:
    print "Over 1/2 Gb Memory usage "
else:
    print 'Low usage!'

cli.disconnect()
