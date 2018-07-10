#GET the base default nginx image from docker hub
FROM nginx
 
#Delete the Existing default.conf
RUN rm /etc/nginx/conf.d/default.conf 
 
#Copy the custom default.conf to the nginx configuration
COPY default.conf /etc/nginx/conf.d

#RUN envsubst < /etc/nginx/conf.d/nginx.template > /etc/nginx/conf.d/default.conf
RUN apt-get update -qq && apt install openssl -y
RUN openssl req -subj '/CN=trustmebank' -x509 -newkey rsa:4096 -nodes -keyout /etc/nginx/conf.d/key.pem -out /etc/nginx/conf.d/cert.pem -days 365

