#!/usr/bin/env bash
docker network create constructor-network
docker run --name constructor-postgres -e POSTGRES_PASSWORD=kaib7aeW --restart=always -d --net=constructor-network postgres
docker run -d --restart=always --name constructor-web  --net=constructor-network -e "VIRTUAL_HOST=constructor.simplex-software.ru" -e "LETSENCRYPT_HOST=constructor.simplex-software.ru" -e "LETSENCRYPT_EMAIL=info@simplex-software.ru" wwwsimplexsoftwareru/constructor-web:v0.1


#!/usr/bin/env bash

or
docker run -d -p 80:80 --name nginx --restart=always -v /var/run/docker.sock:/tmp/docker.sock:ro --net=constructor-network  jwilder/nginx-proxy
docker network connect constructor-network nginx

#!/usr/bin/env bash
docker build -t wwwsimplexsoftwareru/constructor-web:v0.1 .




#!/usr/bin/env bash
docker logs -f constructor-web



docker run -d -p 80:80  -p 443:443 --name nginx --restart=always \
-v /etc/nginx/conf.d  \
  -v /etc/nginx/vhost.d \
  -v /usr/share/nginx/html \
  -v /nginx/certs:/etc/nginx/certs:ro \
  -v /nginx/templates:/etc/docker-gen/templates:rw \
-v /var/run/docker.sock:/tmp/docker.sock:ro \
 --net=constructor-network  jwilder/nginx-proxy


#"rw" убрать? нужно создать файл  /path/to/templates/nginx.tmpl

docker run -d \
  --name nginx-gen \
  --net=constructor-network \
  --volumes-from nginx \
  -v /nginx/templates:/etc/docker-gen/templates:ro \
  -v /var/run/docker.sock:/tmp/docker.sock:ro \
  --label com.github.jrcs.letsencrypt_nginx_proxy_companion.docker_gen \
  jwilder/docker-gen \
  -notify-sighup nginx -watch -wait 5s:30s /etc/docker-gen/templates/nginx.tmpl /etc/nginx/conf.d/default.conf

docker run -d \
  --name nginx-letsencrypt \
  --volumes-from nginx \
  -v /nginx/certs:/etc/nginx/certs:rw \
  -v /var/run/docker.sock:/var/run/docker.sock:ro \
  jrcs/letsencrypt-nginx-proxy-companion