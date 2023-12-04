import os
import requests
import time

id = input("What is the id of the experiment?\n")
filename = f"./results-3/scalability-experiment-{id}.csv"
os.makedirs(os.path.dirname(filename), exist_ok=True)
with open(filename, "w") as text_file:
    text_file.write("msg_per_sec,delivered,sent\n")
    text_file.close()

experiment_time = 10
messages_per_second_list = [5000, 10000, 15000]

for i in range(5):
    for messages_per_second in messages_per_second_list:

        time.sleep(5)

        response = requests.get('http://localhost:8080/sensor-data/reset-bucket')
        print(response.text)

        time.sleep(5)

        print(f'Invoking assembly system to send {messages_per_second} messages per second.')
        response = requests.post('http://localhost:8085/sensor-data/start', params = { 'messagesPerSecond': messages_per_second}, json = {})
        print(response.text)

        time.sleep(experiment_time)

        response = requests.get('http://localhost:8085/sensor-data/stop')
        print(response.text)

        response = requests.get('http://localhost:8080/sensor-data/message-count')
        messages_per_second_str = str(messages_per_second)
        sent = str(messages_per_second * experiment_time)
        count = str(response.text)

        with open(filename, "a") as text_file:
            text_file.write(f"{messages_per_second_str},{count},{sent}\n")
            text_file.close()

print(f"{filename} created.\n\n")
