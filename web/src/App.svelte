<script>
  import EventList from "./lib/EventList.svelte";
  import OrderList from "./lib/OrderList.svelte";
  import { setupClientId } from "./lib/ClientIdUtils";

  setupClientId();

  let orderList;
  function handleMessage(event) {
    orderList.submitOrder(event.detail.event);
  }
</script>

<main>
  <div class="row">
    <div class="col-6">
      <EventList on:order={handleMessage} />
    </div>
    <div class="col-6">
      <OrderList bind:this={orderList} />
    </div>
  </div>
</main>
