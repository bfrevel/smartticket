<script>
  import { onMount } from "svelte";
  import EventListItem from "./EventListItem.svelte";

  let events;
  let event_api_url;

  onMount(() => {
    let hostnameArray = window.location.hostname.split(".");
    hostnameArray[0] = 'event-api'
    event_api_url = hostnameArray.join(".")
    loadEvents();
  });

  const loadEvents = async () => {
    if (event_api_url != null) {
      await fetch(event_api_url)
        .then((r) => r.json())
        .then((data) => {
          events = data;
        });
    }
  };
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
