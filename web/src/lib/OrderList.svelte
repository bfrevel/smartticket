<script>
    import OrderListItem from "./OrderListItem.svelte";
    import { getClientId } from "./ClientIdUtils";
    import { onMount } from "svelte";

    export let clientId = getClientId();
    let order_api_url


    onMount(() => {
        order_api_url = window.location.hostname != 'web.smartticket' ? "/order-api" : "";

        var source = new EventSource(`${order_api_url}/order?clientId=${clientId}`);
        source.onmessage = (event) => {
            var json = JSON.parse(event.data);
            let order = orders.find((item) => item.id == json.id);
            if (order) {
                order.status = "Completed";
                orders = orders;
                setTimeout(() => {
                    console.log("Delayed for 3 second.");
                    orders = orders.filter((item) => item.id !== order.id);
                }, 3000);
            }
        };
    });

    let orders = [];
    export const submitOrder = (event) => {
        let order = {
            status: "Sending",
            event: event,
            timestamp: Date.now(),
        };

        orders = [order, ...orders];

        fetch(`${order_api_url}/order?clientId=${clientId}&eventId=${event.id}`, {
            method: "POST",
        })
            .then((res) => res.text())
            .then((orderid) => {
                order.id = orderid;
                order.status = "Processing";
                orders = orders;
            });
    };
</script>

<div id="order-list">
    {#each orders as order}
        <OrderListItem {order} />
    {/each}
</div>
