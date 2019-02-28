FROM ibmcom/mq
RUN useradd gil -G mqm && \
    echo gil:gilpass | chpasswd
COPY 20-config.mqsc /etc/mqm/