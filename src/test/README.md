Example of running services in multiple files with depends on. You can run the project using `docker-compose -f docker-compose.frontend.yaml -f docker-compose.db.yaml up`. 
The following can also be used to run all docker compose files in a dir: `docker-compose $(find docker* | sed -e 's/^/-f /') up -d` [Source](https://medium.com/@piotr.macha/how-to-split-your-huge-docker-compose-into-multiple-files-3c8866e495dd) If you have sed.