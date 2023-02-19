# Url Shortener

An open source ready for usage url shortener.

## Prerequisites
Before you begin, ensure you have met the following requirements:
- Java JDK 17 or later
- A running mongodb instance.

## Public instance:
I have a public instance running that you can consume/use:
```
 https://s.mgbarbosa.dev/api
```

You can access the Swagger docs with
```
https://s.mgbarbosa.dev/api/docs
```

Feel free to use the API (as long there's no abuse) for shortening/testing the API.

## Running with Docker-Compose
There's already a configured `docker-compose.yaml` file in the project root, so you can 
easily start the project locally following the instructions:

1. Open `docker-compose.yml`
2. Change the required environment variables
3. If you build the image locally, change the image property
4. Start the apps with `docker-compose up -d`

You should be able to reach the app accessing `http://localhost:8080/api/v1/ping` on your browser

## Manually running it with Docker

- Make sure that you have docker running 
- Pull the image with: `docker pull ghcr.io/m4urici0gm/url-shortener:latest`

Start the container with
```
    docker run --name some-url-shortener \
        --restart always \
        -e 'MONGODB_URL=<your-mongodb-connection-string>' \
        -e 'JWT_ISSUER=http://localhost:8080' \ 
        -e 'JWT_AUDIENCE=http://localhost:8080' \
        -e 'JWT_EXPIRATION_SECONDS=7200' \
        -e 'JWT_REFRESH_TOKEN_EXPIRATION_SECONDS=7200' \
        -p 8080:8080 \
        -d ghcr.io/m4urici0gm/url-shortener:latest
```
Check if the app is running with `curl 'http://localhost:8080/api/ping' | json_pp`.
If everything went fine, you should be able to see the response like this:
```json
{
   "message" : "Im alive and well!"
}
```

## Running it locally (dev environment)
1. Fork the repository
2. Make sure you have a running mongodb instance
3. Creates (if it doesnt exist) a .env file on the project root (You can use the template below)
4. Build the project with ```./gradlew clean build```
5. Run the project with ```./gradlew bootRun```
6. The app should be running at ```http://localhost:8080```

### Template file for .env
```
MONGODB_URL=mongodb://root:blueScreen#666@localhost:27017/url-shortener
JWT_ISSUER=http://localhost:8080/
JWT_AUDIENCE=url-shortener
JWT_SECRET=SOME_RANDOM_STRING
JWT_EXPIRATION_SECONDS=7200
JWT_REFRESH_TOKEN_EXPIRATION_SECONDS=7200
```

## Contributing to the project
To contribute to this project, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes.
4. Make sure that all unit tests passes with `./gradlew clean build jacocoTestReport`
5. Push to the original branch: `git push origin <project_name>/<location>`
6. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

## Contributors

Thanks to the following people who have contributed to this project:

* [@m4urici0gm](https://github.com/m4urici0gm) 📖

## Contact

If you want to contact me you can reach me at contact@mgbarbosa.dev

## License
<!--- If you're not sure which open license to use see https://choosealicense.com/--->

This project uses the following license: [Mozilla Public License Version 2.0](https://github.com/M4urici0GM/url-shortener/blob/main/LICENSE.md).

Done with :heart: