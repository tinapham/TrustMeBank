#GET the base default nginx image from docker hub
FROM nginx
 
#Delete the Existing default.conf
RUN rm /etc/nginx/conf.d/default.conf 
RUN mkdir -p /var/www/ca
#Copy the custom default.conf to the nginx configuration
COPY default.conf /etc/nginx/conf.d
COPY trustmebank.com.cert.pem /etc/nginx/conf.d/trustmebank.com.cert.pem
COPY trustmebank.com.key.pem /etc/nginx/conf.d/trustmebank.com.key.pem
COPY ca.cert.pem /var/www/ca/ca.crt

#RUN envsubst < /etc/nginx/conf.d/nginx.template > /etc/nginx/conf.d/default.conf
#RUN apt-get update -qq && apt install openssl -y

