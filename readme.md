# Configure Nexus
* Linux/macOS: add this to the file home/.m2/settings.xml
* Windows: add this to the file C:/Users/username/.m2/settings.xml
```xml
<settings>
    <mirrors>
        <mirror>
            <id>nexus-iisys-sysint</id>
            <mirrorOf>*</mirrorOf>
            <url>http://nexus.iisys.de/repository/sysint-maven-public/</url>
        </mirror>
        <mirror>
            <id>nexus-iisys</id>
            <mirrorOf>*</mirrorOf>
            <url>http://nexus.iisys.de/repository/maven-public/</url>
        </mirror>
    </mirrors>

    <servers>
        <server>
            <id>nexus-iisys-sysint</id>
            <username>IISYS_USER</username>
            <password>IISYS_PASSWORD</password>
        </server>
        <server>
            <id>nexus-iisys</id>
            <username>IISYS_USER</username>
            <password>IISYS_PASSWORD</password>
        </server>

    </servers>

    <profiles>


        <profile>
            <id>nexus-iisys-sysint</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>http://central</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
            
            <pluginRepositories>
                <pluginRepository>
                    <id>central</id>
                    <url>http://central</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </pluginRepository>
            </pluginRepositories>
        </profile>

        <profile>
            <id>nexus-iisys</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>http://central</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>

            <pluginRepositories>
                <pluginRepository>
                    <id>central</id>
                    <url>http://central</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </pluginRepository>
            </pluginRepositories>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>nexus-iisys-sysint</activeProfile>
    </activeProfiles>
</settings>
```
# Compilation
The project can be compiled with maven. The build target for the frontends is defined in the corresponding pom.xml. There should be a line
<arguments>run build-local</arguments>. The target build-local is defined in the package.json from the frontend.
The frontends use git to get the hash of the current branch that is added on the page (for version info). Therefore, git must be installed on the machine.
Alternatively the replace.build.js script can be changed.

# Usage with docker-compose
The folder docker_compose contains a compose file and additional docker-files to set up a docker stack with HiCuMES. To populate the missing folders the script package_hicumes.bat can be used. The paths inside the script have to be changed to the correct paths. It will build the necessary components and copy them inside the folder docker_compose_build. It has the same folder structure as the docker_compose folder. It can be copied completely or just selectively.

For deployment there are multiple addresses that have to be changed:
* Inside the docker-compose.yml the keycloak url has to be changed (MP_JWT_VERIFY_PUBLICKEY_LOCATION, MP_JWT_VERIFY_ISSUER)
* Every frontend has a environment.ts file where the url for the HiCuMES Backend (baseUrl) and the keycloak url has to be changed
* The user and password for the database has to be changed inside the docker-compose and inside the Dockerfile in the wildfly folder

The stack can be deployed with docker compose up. On first startup the images will be built. If changes where made to the builds, the script rebuild.sh can be used. It will stop the stack, delete the images, remove volumes that don't keep data and redeploy the stack.

After the stack is running, users can be added to keycloak inside the admin-UI (default http://localhost:8181 - credentials admin:admin). The users have to be added to the group worker or admin.

Inside the testData folder a container can be built and run, that can deploy a BPMN file to camunda and coredata and mappings to HiCuMES (examples can be found in manufacturingTerminal/testData/demoCompany). The path "/docker_compose/testData/Deployment" inside deploy.sh may be changed to the correct path.

The backends use OpenJDK 11 (preferred Adopt OpenJ9 (IBM Semeru) 11 - https://adoptopenjdk.net/)
