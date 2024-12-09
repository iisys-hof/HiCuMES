# Start with the Ubuntu base image
FROM ubuntu:latest

# Set the working directory
WORKDIR /home

# Copy the initialization files and script to the container
COPY ./init.sh /home/init.sh
COPY ./hicumesFP_init.sql /home/hicumesFP_init.sql
COPY ./hicumes_init.sql /home/hicumes_init.sql

# Update the package list and install the MySQL client
RUN apt update && apt install -y mysql-client

# Ensure the script and SQL files have proper permissions
RUN chmod +x /home/init.sh

# Set the default command to execute the initialization script
CMD ["bash", "-c", "/home/init.sh"]
