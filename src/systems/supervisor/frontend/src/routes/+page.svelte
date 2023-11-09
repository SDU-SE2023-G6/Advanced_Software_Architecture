<script lang="ts">

  import { onMount } from "svelte";
  import { ExampleControllerApi } from "../api/apis/ExampleControllerApi";
  import type { Order } from "../api";

  const exampleController = new ExampleControllerApi();

  let steps: string[] = [];
  let orders: Order[] = [];

  onMount(async () => {
    steps = [];
    orders = [];
    orders = await exampleController.getOrders(); // Hacky way to set the timer
    setInterval(async () => {
      orders = await exampleController.getOrders();
    }, 1000)
    

  });

  async function sendInstructions(order_steps: string[]) {
    exampleController.send({
      createOrderDTO: {
        steps: order_steps
      }
    })
  }

  function addStep() {
    steps = [...steps, ""];
  }

  function removeStep(index: number) {
    let newSteps = steps.slice();
    newSteps.splice(index, 1);
    steps = [...newSteps];
  }
</script>

<main>
  <h1>Supervisor Fronted</h1>
  <div class="container">
    <section class="instructions">
      <h2>Send instructions</h2>
      <label>Steps <button on:click={() => addStep()}>Add step+</button></label>
      {#each steps as step, index}
        <input bind:value={steps[index]}>
      {/each}
      <button on:click={() => sendInstructions(steps)}>Send</button>
    </section>

    <section class="feedbacks">
      <h2>Assembly feedbacks</h2>
      {#if orders.length > 0}
        {#each orders as order}
        <details>
          <summary class="feedback">{order.orderId}</summary>
            {#if order.steps}
            <ul style="padding-left: 20px">
              {#each order.steps as step}
                <li class="feedback">
                  {step.stepId } - {step.orderStatus}
                </li>
              {/each}
            </ul>
            {/if}
        </details>
        {/each}
      {/if}
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
