# Sweatbank Backend Project
A backend project for the Sweatbank leasing application, which uses Java and the Spring Framework

## Steps for running the project
1. Start docker on your machine
2. Run the command `docker compose up`
3. Go to `localhost:8080` in your browser
4. To stop the application, press `CTRL + C`
5. Remove stopped docker containers using `docker compose down`

## Database
This project uses the PostgreSQL database. It is automatically ran together with the Java Spring application when using `docker compose up`.
To view the database on the admin dashboard, you can go to `localhost:8888` on your browser and login with `username: admin@admin.com` and `password: test1234`.
