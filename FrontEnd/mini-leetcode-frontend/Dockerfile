FROM node:12.18.1
WORKDIR /app
COPY package.json ./
RUN npm install
COPY src src
COPY .env .env
COPY public public
COPY .editorconfig .editorconfig
COPY default.conf default.conf
COPY craco.config.js craco.config.js
CMD ["npm", "start"]

#FROM node:12.18.1 as react-build
#WORKDIR /app
#COPY package.json .
#RUN npm i
#COPY . ./
#RUN npm run build
#
#FROM nginx:alpine
#COPY default.conf /etc/nginx/conf.d/default.conf
#COPY --from=react-build /app/build /usr/share/nginx/html
#EXPOSE 80
#CMD ["nginx", "-g", "daemon off;"]
