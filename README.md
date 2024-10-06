# Contest Platform

Functionalities:
- Contests
- Code execution is performed in seperate docker container.
- Kafka is used to increase throughput and make it fault-tolerant.

Deployment

- Build backend image using dockerfile.
- Push image to docker hub.
- Run docker-compose in EC2 instance.