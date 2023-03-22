<script>
  import { createEventDispatcher } from "svelte";
  const dispatch = createEventDispatcher();
  export let event;

  function order() {
    dispatch("order", {
      event: event
    });
  }
</script>

<div class="row p-3">
  <div class="card" title="event-{event.id}">
    <div class="card-body">
      <h5 class="card-title">{event.name}</h5>
      <p class="card-text">
        City: <b>{event.city}</b><br />
        Location: <b>{event.location}</b><br />
        Begin: <b>{event.begin}</b>
      </p>

      <button on:click={order} type="button" class="btn btn-primary">
        Order Tickets
      </button>

    </div>
  </div>
</div>
