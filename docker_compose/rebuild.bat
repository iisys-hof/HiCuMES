@echo off

REM Stop Docker Compose
docker compose down

REM Remove Docker Volumes
docker volume rm docker-hicumes_frontend_config_db_editor
docker volume rm docker-hicumes_frontend_config_mft
docker volume rm docker-hicumes_frontend_config_schema_mapper
docker volume rm docker-hicumes_frontend_config_feinplanung
docker volume rm docker-hicumes_frontend_data_db_editor
docker volume rm docker-hicumes_frontend_data_mft
docker volume rm docker-hicumes_frontend_data_schema_mapper
docker volume rm docker-hicumes_frontend_data_feinplanung
docker volume rm docker-hicumes_hicumes-backend-dbg

REM Remove Docker Images
docker rmi hicumes.mft
docker rmi hicumes.camunda
docker rmi hicumes.db-editor
docker rmi hicumes.schmema-mapper
docker rmi hicumes.backend
docker rmi hicumes.feinplanung
docker rmi hicumes.fp_frontend
docker rmi hicumes.deploy

REM Start Docker Compose
docker compose up -d
