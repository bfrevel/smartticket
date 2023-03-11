<script>
  import { onMount } from "svelte";
  import EventListItem from "./EventListItem.svelte";
  let events;

  onMount(async () => {
    await fetch("http://smartticket.localdev.me/event")
      .then((r) => r.json())
      .then((data) => {
        events = data;
      });
  });
</script>

{#if events}
  {#each events as event}
    <EventListItem {event} on:order />
  {/each}
{:else}
  <div class="spinner-border" role="status">
    <span class="visually-hidden">Loading...</span>
  </div>
{/if}
