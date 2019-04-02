###Using the management tag
RUN: `docker run -d --hostname gil-rabbit-host --name gil-rabbit-name -p 5672:5672 -p 8585:15672 -e RABBITMQ_DEFAULT_USER=gilberto -e RABBITMQ_DEFAULT_PASS=gil -e RABBITMQ_DEFAULT_VHOST=gil_vhost rabbitmq:3-management`
LOGS: `docker logs gil-rabbit-name`


###REDIS
`docker run --name gil-redis -d redis`
`docker run --name gil-redis -d redis redis-server --appendonly yes` with persistence storage: data stored /data

###IBM IBM MQ
`docker build .`
`docker volume create qm1data` if you wanna persist configs or messages across container runs
`docker run --env LICENSE=accept --env MQ_QMGR_NAME=QM1 --publish 1414:1414 --publish 9443:9443 --volume qm1data:/mnt/mqm --detach ibmcom/mq`

`docker exec --tty --interactive ${CONTAINER_ID} dspmq` run commands directly
`docker exec --tty --interactive ${CONTAINER_ID} runmqsc QM1` to run config commands

`ALTER AUTHINFO(SYSTEM.DEFAULT.AUTHINFO.IDPWOS) AUTHTYPE(IDPWOS) CHCKCLNT(NONE) CHCKLOCL(NONE)` removes authentication. Restart the queue manager for this change to take effect.



###PCF Agent for MQ   [using MQ Java PCF API to retrieve information from an MQ installation]
`
     PCFMessageAgent agent = new PCFMessageAgent(host, port, "SYSTEM.DEF.SVRCONN");
     PCFMessage pcfCmd = new PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL);
     pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, channelName);
     PCFMessage[] pcfResponse = agent.send(pcfCmd);
 `
 
 ####Some Error and solution
 The issue is that you are attempting to connect to the channel SYSTEM.DEV.SVRCONN. By default there is a CHLAUTH rule that blocks access to channels named SYSTEM.*.
 You can create a new SVRCONN channel that does not have a name that starts with SYSTEM and it would get past this check
 
 ###Links
 `https://github.com/ibm-messaging/mq-docker`
 `https://www.ibm.com/developerworks/websphere/library/techarticles/1311_jin/1311_jin.html`
 `http://localhost:8080/wmq-monitoring/`
 `https://github.com/rabbitmq/hop` for rabbit queue info access