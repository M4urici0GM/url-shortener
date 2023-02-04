# Url Shortener

An open source ready for usage url shortener.

## Prerequisites
Before you begin, ensure you have met the following requirements:
- Java JDK 17 or later
- A running mongodb instance.

## Running it locally (dev environment)
1. Fork the repository
2. Creates (if it doesnt exist) a .env file on the project root
3. Build the project with ```./gradlew clean build```
4. Run the project with ```./gradlew bootRun```
5. The app should be running at ```http://localhost:8080```

### Template file for .env
```
MONGODB_URL=mongodb://root:blueScreen#666@localhost:27017/url-shortener
JWT_ISSUER=http://localhost:8080/
JWT_AUDIENCE=url-shortener
JWT_SECRET=SOME_RANDOM_STRING
JWT_EXPIRATION_SECONDS=7200
JWT_REFRESH_TOKEN_EXPIRATION_SECONDS=7200
```

## Contributing to tcproxy
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

This project uses the following license: [GPL-2.0](https://github.com/M4urici0GM/tcproxy/blob/main/LICENSE.md).

Done with :heart: