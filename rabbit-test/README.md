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
             