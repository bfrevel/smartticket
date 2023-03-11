<script>
    import { fly } from "svelte/transition";

    export let order;
</script>

<div
    class="alert alert-info alert-dismissible"
    role="alert"
    id=""
    out:fly={{ x: 200, duration: 1000 }}
>
    <div>Event: <strong>{order.event.name}</strong></div>

    <div>Status: <strong>{order.status}</strong></div>
    {#if order.id}
        <div>Order: <strong>{order.id}</strong></div>
    {/if}
    <button
        type="button"
        class="btn-close"
        data-bs-dismiss="alert"
        aria-label="Close"
    />
</div>
