import requests
import time

messagesPerSecond = input('How many messages should the Assembly system send per second?\n')
times = input("How often should the experiment be run?\n")

for i in range(int(times)):

    response = requests.get('http://localhost:8080/sensor-data/reset-bucket')
    print(response.text)

    time.sleep(5)

    print(f'Invoking Assembly system to send {messagesPerSecond} messages per second')
    response = requests.post('http://localhost:8085/sensor-data/start', params = { 'messagesPerSecond': messagesPerSecond } , json = {})
    print(response.text)

    time.sleep(10)

    response = requests.get('http://localhost:8085/sensor-data/stop')
    print(response.text)

    time.sleep(5)

    response = requests.get('http://localhost:8080/sensor-data/csv')

    with open(f"sensor-data-{i}.csv", "w") as text_file:
        text_file.write(response.text)
    print(f"sensor-data-{i}.csv created.\n\n")

    time.sleep(10)