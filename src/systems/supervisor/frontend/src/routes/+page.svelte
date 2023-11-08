<script lang="ts">
    
  export let data;

  import { onMount } from "svelte";

  let step_id = "";
  let order_id = "";
  let feedbacks = ["test", "test2"];

  onMount(async () => {
    step_id = "";
    order_id = "";
    await pullFeedbacks();
  });

  async function sendInstructions() {
    const response = await fetch("http://localhost:8000", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ step_id, order_id })
    });
    step_id = "";
    order_id = "";
  }
  async function pullFeedbacks() {
    const response = await fetch("http://localhost:8000", {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    });
    const pulledList = await response.json();
    //push to feedbacks
    for(const feedback of pulledList){
      feedbacks.push(feedback);
    }
  }
</script>

<main>
  <h1>Supervisor Fronted</h1>
  <p>
    {{ data }}
  </p>
  <div class="container">
    <section class="instructions">
      <h2>Send instructions</h2>
      <label>Step ID: <input bind:value={step_id}></label>
      <label>Order ID: <input bind:value={order_id}></label>
      <button on:click={sendInstructions}>Send</button>
    </section>

    <section class="feedbacks">
      <h2>Assembly feedbacks</h2>
      <ul>
        {#each feedbacks as feedback}
          <li class="feedback">{feedback}</li>
        {/each}
      </ul>
    </section>
  </div>
</main>

<style>
  main {
    font-family: Arial, sans-serif;
    width: 80%;
    margin: 50px auto;
    border: 1px solid #e0e0e0;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  h1 {
    text-align: center;
    margin-bottom: 30px;
  }

  .container {
    display: flex;
    gap: 50px;
  }

  .instructions {
    flex: 0 0 30%;
  }

  .feedbacks {
    flex: 1;
  }

  label {
    display: block;
    margin: 10px 0;
  }

  input, button {
    width: 100%;
    padding: 10px;
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    margin-top: 5px;
  }

  button {
    cursor: pointer;
    background-color: #007BFF;
    color: white;
    border: none;
  }

  button:hover {
    background-color: #0056b3;
  }

  ul{
    padding: 0;
  }

  .feedback {
    padding: 10px;
    border: 1px solid #e0e0e0;
    border-radius: 5px;
    margin-bottom: 10px;
    /* remove bullet */
    list-style: none;
    background: #f0f0f0;
  }
</style>
