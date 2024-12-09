sudo docker compose down

sudo docker volume rm docker-hicumes_frontend_config_db_editor
sudo docker volume rm docker-hicumes_frontend_config_mft
sudo docker volume rm docker-hicumes_frontend_config_schema_mapper
sudo docker volume rm docker-hicumes_frontend_config_feinplanung
sudo docker volume rm docker-hicumes_frontend_data_db_editor
sudo docker volume rm docker-hicumes_frontend_data_mft
sudo docker volume rm docker-hicumes_frontend_data_schema_mapper
sudo docker volume rm docker-hicumes_frontend_data_feinplanung
sudo docker volume rm docker-hicumes_hicumes-backend-dbg

sudo docker rmi hicumes.mft
sudo docker rmi hicumes.camunda
sudo docker rmi hicumes.db-editor
sudo docker rmi hicumes.schmema-mapper
sudo docker rmi hicumes.backend
sudo docker rmi hicumes.feinplanung
sudo docker rmi hicumes.fp_frontend
sudo docker rmi hicumes.deploy

sudo docker compose up -d
