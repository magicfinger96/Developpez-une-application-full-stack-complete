
# Monde de Dév (MDD)

## Setup the DDB:
- Download MySQL command line client from your browser
- Set your own password and username
- To prevent encoding issue, add thoses lines inside `my.ini` file (mine is inside `C:\ProgramData\MySQL\MySQL Server 8.0`):
```
[client]
default-character-set=utf8mb4

[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_general_ci

[mysql]
default-character-set=utf8mb4
```

- Copy the absolute path of `data.sql` which is inside `Back\src\main\resources` folder of this project
- In the MySQL CLI, enter: `source ` followed by the absolute path of `data.sql`

## Run the Front:
- Open a terminal inside `front` folder
- Execute `npm install` to install the dependencies
- Then execute `npm run start` to run the front project

## Run the back:
- Create the environment variables (`MDD_JWT_PUBLIC_KEY`, `MDD_JWT_PRIVATE_KEY`, `MDD_DDB_USERNAME`, `MDD_DDB_PASSWORD`) with the secret keys
- Open a terminal inside `back` folder
- Execute `mvn spring-boot:run` (If `mvn` is not recognized, follow instructions [here](https://www.baeldung.com/install-maven-on-windows-linux-mac))

You are now ready to go!

## Swagger documentation:
http://localhost:8080/swagger-ui/index.html
