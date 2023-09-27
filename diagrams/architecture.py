from diagrams import Cluster, Diagram
from diagrams.onprem.network import Nginx
from diagrams.programming.framework import Svelte
from diagrams.custom import Custom
from diagrams.onprem.queue import Rabbitmq
from diagrams.onprem.inmemory import Redis
from diagrams.onprem.database import Postgresql

with Diagram("architecture"):
    
    with Cluster("web"):
        nginx = Nginx("nginx")
        web = Svelte("web")
        web - nginx

    with Cluster("event"):
        event_api = Custom("event-api", "./custom/quarkus.png")
        event_api >> [Redis("event-redis"),
                     Postgresql("event_postgresql")]

    with Cluster("order"):
        order_api = Custom("order-api", "./custom/quarkus.png")
        order_rabbitmq = Rabbitmq("order-rabbitmq")
        order_api >> order_rabbitmq
        order_processor = Custom("order-processor", "./custom/quarkus.png")
        order_rabbitmq >> order_processor
    

    web >> event_api
    web >> order_api