import type { Order } from "../api";
import { ExampleControllerApi } from "../api/apis/ExampleControllerApi";

export const load = async () =>  {
    const api = new ExampleControllerApi();
    const orders = await api.getOrders();
    return {
        orders: orders
    }
}
