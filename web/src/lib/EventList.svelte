<script>
  import { onMount } from "svelte";
  import EventListItem from "./EventListItem.svelte";
  let events;
  let event_api_url;

  onMount(async () => {
    event_api_url = window.location.hostname != 'web.smartticket' ? "/event-api" : "";

    await fetch(`${event_api_url}/event`)
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
