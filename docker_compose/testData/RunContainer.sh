docker run --rm --name hicumes-deploy --mount type=bind,source="./Deployment",target="/opt/testdata/Deployment" --network="docker-hicumes_default" -it hicumes.deploy
