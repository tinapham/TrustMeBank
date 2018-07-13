FROM sameersbn/bind:9.10.3-20180127
 
RUN rm /etc/bind/named.conf.options
RUN rm /etc/bind/named.conf.default-zones

COPY db.trustmebank.com /etc/bind/db.trustmebank.com
COPY named.conf.default-zones /etc/bind/named.conf.default-zones
COPY named.conf.options /etc/bind/named.conf.options

