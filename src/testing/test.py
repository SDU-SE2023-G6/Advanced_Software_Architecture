import requests
import json
from datetime import datetime
import time
import os

ASSEMBLY_HOST = "http://localhost:8081"
SUPERVISOR_HOST = "http://localhost:8080"

def start_test(requests_per_second: int = 1000):
    res = requests.post(f"{ASSEMBLY_HOST}/sensor-data/start", data={"messagesPerSecond": requests_per_second})
    if res.ok:
        print(res.text)
    else: 
        raise Exception(f"Failed: {res.text}")

def stop_test(requests_per_second: int = 1000):
    res = requests.get(f"{ASSEMBLY_HOST}/sensor-data/stop")
    if res.ok:
        print(res.text)
    else: 
        raise Exception(f"Failed: {res.text}")

def reset_bucket():
    res = requests.get(f"{SUPERVISOR_HOST}/sensor-data/reset-bucket")
    if res.ok:
        print(res.text)
    else:
        raise Exception(f"Failed: {res.text}")

def download_csv(export_name: str = None):
    os.makedirs("./csv_files", exist_ok=True)
    res = requests.get(f"{SUPERVISOR_HOST}/sensor-data/csv")
    if res.ok:
        filename = export_name if export_name is not None else datetime.now() 
        with open(f"./csv_files/{filename}", "w") as file:
            file.write(res.text)
    else:
        raise Exception(f"Failed download: {res.text}")


if __name__ == "__main__":
    requests_per_second = 1000
    reset_bucket()
    start_test(requests_per_second)
    time.sleep(10)
    stop_test()
    download_csv()
