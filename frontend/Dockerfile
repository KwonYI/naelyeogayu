FROM node:12.16.1-alpine as builder
WORKDIR /frontend
ADD . .
RUN npm install
RUN npm run build

FROM nginx:stable-alpine as production
#RUN apk add python3 python3-dev py3-pip build-base libressl-dev libffi-dev
#RUN pip3 install --upgrade pip
#RUN pip3 install certbot-nginx
RUN mkdir -p /var/www/letsencrypt/.well-known/acme-challenge
COPY ./nginx/nginx.conf /etc/nginx/conf.d/default.conf
RUN true
COPY --from=builder /frontend/dist /usr/share/nginx/html
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
