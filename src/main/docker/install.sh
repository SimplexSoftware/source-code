#!/usr/bin/env bash
docker network create source-code-network
docker run --name source-code-postgres -e POSTGRES_PASSWORD=Uiphie4E --restart=always -d --net=source-code-network postgres
docker run -d --restart=always --name source-code-web  --net=source-code-network -e "VIRTUAL_HOST=source-code.club" -e "LETSENCRYPT_HOST=source-code.club" -e "LETSENCRYPT_EMAIL=info@simplex-software.ru" wwwsimplexsoftwareru/source-code-web:v0.1

#!/usr/bin/env bash
docker build -t wwwsimplexsoftwareru/source-code-web:v0.1 .


#!/usr/bin/env bash
docker logs -f source-code-web


docker run -d -p 80:80  -p 443:443 --name nginx --restart=always \
-v /etc/nginx/conf.d  \
  -v /etc/nginx/vhost.d \
  -v /usr/share/nginx/html \
  -v /nginx/certs:/etc/nginx/certs:ro \
  -v /nginx/templates:/etc/docker-gen/templates:rw \
-v /var/run/docker.sock:/tmp/docker.sock:ro \
 --net=source-code-network  jwilder/nginx-proxy


docker run -d \
  --name nginx-gen \
  --net=source-code-network \
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