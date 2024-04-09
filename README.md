# Sweatbank Backend Project
A backend project for the Sweatbank leasing application, which uses Java and the Spring Framework

# Steps for running the project
1. Create a docker image with `docker build -t sweaterbank:latest .`
2. Run the docker container using `docker run -p 8080:8080 sweaterbank:latest`
3. Go to `localhost:8080` in your browser

# Docker info
- Instead of the `latest` tag for the image, you can set it to whatever you want, for example `0.0.1`.
- `-p 8080:8080` argument binds the container port `8080` to the host port of `8080`.
- More information on the docker build command https://docs.docker.com/reference/cli/docker/image/build/
- More information on the docker run command https://docs.docker.com/reference/cli/docker/container/run/
