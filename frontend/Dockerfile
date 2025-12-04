# build stage
FROM node:18-alpine as build
WORKDIR /app
COPY package.json ./
RUN npm install
COPY public ./public
COPY src ./src
ARG REACT_APP_API_BASE
ENV REACT_APP_API_BASE=$REACT_APP_API_BASE
RUN npm run build

# runtime stage
FROM nginx:stable-alpine
COPY --from=build /app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
