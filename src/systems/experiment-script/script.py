import requests
import time

messagesPerSecond = input('How many messages should the Assembly system send per second?\n')

response = requests.get('http://localhost:8080/sensor-data/reset-bucket')
print(response.text)

time.sleep(5)

print(f'Invoking Assembly system to send {messagesPerSecond} messages per second')
response = requests.post('http://localhost:8081/sensor-data/start', params = { 'messagesPerSecond': messagesPerSecond } , json = {})
print(response.text)

time.sleep(5)

response = requests.get('http://localhost:8081/sensor-data/stop')
print(response.text)

time.sleep(5)

response = requests.get('http://localhost:8080/sensor-data/csv')
with open("sensor-data.csv", "w") as text_file:
    text_file.write(response.text)
print("sensor-data.csv created.")