import os
import sys
import json
import time

from dateutil import parser
from concurrent.futures.thread import ThreadPoolExecutor
from dataclasses import dataclass
from datetime import date, datetime
from typing import Dict, List

import requests


# region URLs
URL_CONFIRMED = "https://api.covid19api.com/country/united-states/status/confirmed"
URL_RECOVERED = "https://api.covid19api.com/country/united-states/status/recovered"
URL_FATAL = "https://api.covid19api.com/country/united-states/status/deaths"
# endregion


# region Utils
def exec_timing(fn):
    """Decorator used to time the function execution time length and print the result."""
    def inner(*args, **kwargs):
        _start_ = time.time()
        ret = fn(*args, **kwargs)
        _duration_ = time.time() - _start_

        print(f"{fn.__name__} executed {_duration_ * 1000} ms")

        return ret
    return inner
# endregion


@dataclass
class DataEntry:
    city: str
    city_code: str
    lat: float
    lon: float
    cases: int
    date_: date

    def __post_init__(self):
        self.lat = float(self.lat)
        self.lon = float(self.lon)
        self.cases = int(self.cases)

    def to_csv_entry(self) -> str:
        # Matches the order of the convention in README.md
        data = [
            self.date_.strftime("%Y-%m-%d"),
            self.city,
            self.city_code,
            f"{self.lat:.2f}",
            f"{self.lon:.2f}",
            self.cases]

        return ",".join([f'"{txt}"' for txt in data])


def download_json(url) -> List[Dict]:
    """
    Download the content at `url` and load it as a json object.

    Terminates the program if the status code is not 200.
    """
    response = requests.get(url)
    if not response.ok:
        print(f"JSON cannot be downloaded. (<{response.status_code}> {url})")
        exit(1)

    return json.loads(response.content)


def json_to_csv(case_type, json_data):
    """Parse json object into entries and append it to the data file."""
    entry_count = len(json_data)
    for idx, entry in enumerate(json_data, start=0):
        state = entry["Province"]

        entry = DataEntry(
            city=entry["City"], city_code=entry["CityCode"],
            lat=entry["Lat"], lon=entry["Lon"],
            cases=entry["Cases"], date_=parser.parse(entry["Date"]))

        with open(f"{case_type}/{state}.csv", "a+", encoding="utf-8") as f:
            f.write(entry.to_csv_entry() + "\n")

        if idx % 1000 == 0:
            print(f"{idx} / {entry_count} ({idx / entry_count:.2%})")


def task(out_dir, case_type, url):
    print(f"Creating directory for <{case_type} cases>...")
    out_dir = os.path.join(out_dir, case_type)
    dir_prep(out_dir)
    print(f"Creating directory for <{case_type} cases>...Completed")

    print(f"Downloading the data of <{case_type} cases>...")
    data = download_json(url)
    print(f"Downloading the data of <{case_type} cases>...Completed")

    print(f"Writing the data of <{case_type} cases>...")
    json_to_csv(out_dir, data)
    print(f"Writing the data of <{case_type} cases>...Completed")


def dir_prep(dir_check):
    """
    Directory preparation procedure.

    If the directory is empty, create one.
    If the directory exists, make sure it's empty. Otherwise, terminates the program.
    """
    if not os.path.isdir(dir_check):  # Check the existence of the directory
        os.mkdir(dir_check)
    elif os.listdir(dir_check):  # Ensure empty directory
        print("Output directory needs to be empty.")
        exit(1)


@exec_timing
def main(dir_):
    dir_prep(dir_)
    # task(dir_, "confirmed", URL_CONFIRMED)
    # task(dir_, "recovered", URL_RECOVERED)
    # task(dir_, "fatal", URL_FATAL)

    # Use multi processing to boost the speed
    with ThreadPoolExecutor(max_workers=4, thread_name_prefix="ProcessData") as executor:
        # Initiate `task` for each type of cases
        futures = [
            executor.submit(task, dir_, "confirmed", URL_CONFIRMED),
            executor.submit(task, dir_, "recovered", URL_RECOVERED),
            executor.submit(task, dir_, "fatal", URL_FATAL),
        ]

        # Non-lock call & Free resources when execution is done
        executor.shutdown(False)

        for completed in futures:
            completed.result()


if __name__ == '__main__':
    if len(sys.argv) != 2:
        output_dir = datetime.now().strftime("%Y-%m-%d %H-%M-%S")
    else:
        output_dir = sys.argv[1]

    print(f"Output directory: {output_dir}")
    main(output_dir)
