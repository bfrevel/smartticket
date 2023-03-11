<script>
    import OrderListItem from "./OrderListItem.svelte";
    import { getClientId } from "./ClientIdUtils";


    export let clientId = getClientId();

    let orders = [];
    export const submitOrder = (event) => {
        let order = {
            status: "Sending",
            event: event,
        };

        orders = [order, ...orders];

        fetch(
            `http://smartticket.localdev.me/order?clientId=${clientId}&eventId=${event.id}`,
            {
                method: "POST",
            }
        )
            .then((res) => res.text())
            .then((orderid) => {
                order.id = orderid;
                order.status = "Processing";
                orders = orders;
            });
    };

    var source = new EventSource(
        `http://smartticket.localdev.me/order?clientId=${clientId}`
    );
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
</script>

<!-- <div bind:this={container} /> -->

{#each orders as order}
    <OrderListItem {order} />
{/each}
