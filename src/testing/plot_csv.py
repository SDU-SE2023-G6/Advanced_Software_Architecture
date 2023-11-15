from matplotlib import pyplot as plt
from dataclasses import dataclass

@dataclass
class DataPoint:
    sensor_id: str
    assembly_timestamp: int
    supevisor_timestamp: int
    influxdb_timestamp: int

    def full_trip_time(self):
        return self.influxdb_timestamp - self.assembly_timestamp


def parse_csv(filepath):
    sensor_datapoints = []
    with open(filepath, "r") as file:
        lines = file.readlines()[1:] # skip first line 
        for line in lines:
            data = line.split(",")
            sensor_datapoints.append(DataPoint(data[0], int(data[1]), int(data[2]), int(data[3])))
    return sensor_datapoints

data = parse_csv("csv_files/2023-11-15 11:32:03.490976")

round_trip = [d.full_trip_time() for d in data]

plt.plot(round_trip[:100])
plt.show()

