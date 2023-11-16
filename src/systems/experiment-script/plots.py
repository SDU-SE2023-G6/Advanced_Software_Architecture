from matplotlib import pyplot as plt
from dataclasses import dataclass
from typing import List

ASSEMBLY_KEY = "assembly_timestamp"
SUPERVISOR_KEY = "supevisor_timestamp"
INFLUX_KEY = "influxdb_timestamp"

@dataclass
class DataPoint:
    sensor_id: str
    assembly_timestamp: int
    supevisor_timestamp: int
    influxdb_timestamp: int

    def assembly_to_influx(self):
        return self.influxdb_timestamp - self.assembly_timestamp
    
    def assembly_to_supervisor(self):
        return self.supevisor_timestamp - self.assembly_timestamp
    
    def supervisor_to_influx(self):
        return self.influxdb_timestamp - self.supevisor_timestamp


def parse_csv(filepath):
    sensor_datapoints = []
    with open(filepath, "r") as file:
        lines = file.readlines()[1:] # skip first line 
        for line in lines:
            data = line.split(",")
            sensor_datapoints.append(DataPoint(data[0], int(data[1]), int(data[2]), int(data[3])))
    return sensor_datapoints


def interval_aggregation(data: List[DataPoint], sort_key: str, time_interval: int = 100) -> List[List[DataPoint]]:
    """Aggregate datapoints into buckets of 'time_interval'"""
    data = list(data)

    datapoint_buckets = []
    while len(data) != 0:
        first_timestamp = getattr(data[0], sort_key) # get the first timestamp of the sorted dat
        bucket = []
        for index, datapont in enumerate(data):
            if getattr(datapont, sort_key) < first_timestamp + time_interval:
                bucket.append(datapont)
                data.pop(index - 1)
        datapoint_buckets.append(bucket)
    
    return datapoint_buckets

def sum_by_key(datapoints: List[List[DataPoint]], func):
    """Sum and get mean for every bucket in the nested list"""
    return list(
        map(
            lambda datapoint_list: sum(func(datapoint) for datapoint in datapoint_list)/len(datapoint_list),
            datapoints
            ))

def count_datapoint_per_bucket(datapoints: List[List[DataPoint]]):
    """Loop through 2d array and count every item and return a 1d array of the count"""
    return list(
        map(lambda datapoint_list: len(datapoint_list), datapoints)
    )

def prep_data_for_plot(data: List[DataPoint], sum_key: str, interval: int, func): # Func is a lambda for transforming data
    """Get the place interval into buckets of data and then sum and mean those buckets"""
    aggragated_data = interval_aggregation(data, sum_key)
    return sum_by_key(aggragated_data, func)

def plot_2x2_graph(data: List[DataPoint], interval: int):
    interval = 100
    assembly_to_influx = prep_data_for_plot(data, ASSEMBLY_KEY, interval, lambda x: x.assembly_to_influx())
    assembly_to_supervisor = prep_data_for_plot(data, ASSEMBLY_KEY, interval, lambda x: x.assembly_to_supervisor())
    supervisor_to_influx = prep_data_for_plot(data, SUPERVISOR_KEY, interval, lambda x: x.supervisor_to_influx())
    message_throughput = count_datapoint_per_bucket(interval_aggregation(data, SUPERVISOR_KEY, interval)) # Don't care about key only buckets and their size

    fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2)

    ax1.plot(assembly_to_influx)
    ax1.title.set_text("Assembly to InfluxDB time (ms)")

    ax2.plot(assembly_to_supervisor)
    ax2.title.set_text("Assembly to Supervisor time (ms)")

    ax3.plot(supervisor_to_influx)
    ax3.title.set_text("Supervisor to InfluxDB time (ms)")

    ax4.plot(message_throughput)
    ax4.title.set_text(f"Message throughput aggregated per {interval} interval (ms)")

    plt.show()

data = parse_csv("csv/4-supervisor-sensor-data-10000msg.csv")

plot_2x2_graph(data, 100)

plt.show()

