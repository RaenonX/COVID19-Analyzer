import csv
import os
import sys
import time

from concurrent.futures.thread import ThreadPoolExecutor
from dataclasses import dataclass, field
from datetime import date, datetime, timedelta
from typing import List

import requests

# region URLs
DATE_START = date(2020, 1, 22)
URL_PARENT = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/" \
             "master/csse_covid_19_data/csse_covid_19_daily_reports"


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


def get_list_by_idx(list_, idx, default):
    if 0 <= idx < len(list_):
        return list_[idx]
    else:
        return default


# endregion


state_full_to_abbr = {
    "Alabama": "AL",
    "Alaska": "AK",
    "Arizona": "AZ",
    "Arkansas": "AR",
    "California": "CA",
    "Colorado": "CO",
    "Connecticut": "CT",
    "Delaware": "DE",
    "Florida": "FL",
    "Georgia": "GA",
    "Hawaii": "HI",
    "Idaho": "ID",
    "Illinois": "IL",
    "Indiana": "IN",
    "Iowa": "IA",
    "Kansas": "KS",
    "Kentucky": "KY",
    "Louisiana": "LA",
    "Maine": "ME",
    "Maryland": "MD",
    "Massachusetts": "MA",
    "Michigan": "MI",
    "Minnesota": "MN",
    "Mississippi": "MS",
    "Missouri": "MO",
    "Montana": "MT",
    "Nebraska": "NE",
    "Nevada": "NV",
    "New Hampshire": "NH",
    "New Jersey": "NJ",
    "New Mexico": "NM",
    "New York": "NY",
    "North Carolina": "NC",
    "North Dakota": "ND",
    "Ohio": "OH",
    "Oklahoma": "OK",
    "Oregon": "OR",
    "Pennsylvania": "PA",
    "Rhode Island": "RI",
    "South Carolina": "SC",
    "South Dakota": "SD",
    "Tennessee": "TN",
    "Texas": "TX",
    "Utah": "UT",
    "Vermont": "VT",
    "Virginia": "VA",
    "Washington": "WA",
    "West Virginia": "WV",
    "Wisconsin": "WI",
    "Wyoming": "WY",
    "District of Columbia": "DC",
    "D.C.": "DC",
    "Marshall Islands": "MH",
    "Chicago": "IL",
    "Grand Princess Cruise Ship": "Grand Princess",
    "U.S.": "Virgin Islands",
    "US": "Unassigned",
    "Unassigned Location (From Diamond Princess)": "Diamond Princess",
    "United States Virgin Islands": "Virgin Islands",
    "TX (From Diamond Princess)": "TX",
    "NE (From Diamond Princess)": "NE",
    "CA (From Diamond Princess)": "CA"
}


no_convert_counter = {}


@dataclass
class Header:
    idx_state: int
    idx_confirmed: int
    idx_fatal: int

    idx_county: int = field(default=-1)
    idx_country: int = field(default=-1)
    idx_lat: int = field(default=-1)
    idx_lon: int = field(default=-1)

    idx_recovered: int = field(default=-1)
    idx_active: int = field(default=-1)

    idx_date: int = field(default=-1)

    def to_header_str(self) -> str:
        ret = []
        dict_ = {
            self.idx_state: "state",
            self.idx_country: "country",
            self.idx_confirmed: "confirmed",
            self.idx_fatal: "fatal",
            self.idx_recovered: "recovered",
            self.idx_county: "county",
            self.idx_lat: "lat",
            self.idx_lon: "lon",
            self.idx_active: "active",
            self.idx_date: "date",
        }

        for i in range(10):
            item = dict_.get(i)
            if item:
                ret.append(item)

        return ",".join(ret)

    @staticmethod
    def parse(header_column) -> 'Header':
        idx_state = -1
        idx_country = -1
        idx_confirmed = -1
        idx_fatal = -1
        idx_recovered = -1

        idx_county = -1
        idx_lat = -1
        idx_lon = -1
        idx_active = -1

        for i in range(len(header_column)):
            item = header_column[i].lower()

            if "state" in item:
                idx_state = i
            elif "country" in item:
                idx_country = i
            elif "admin2" in item:
                idx_county = i
            elif "confirm" in item:
                idx_confirmed = i
            elif "death" in item:
                idx_fatal = i
            elif "recover" in item:
                idx_recovered = i
            elif "lat" in item:
                idx_lat = i
            elif "lon" in item:
                idx_lon = i
            elif "active" in item:
                idx_active = i

        return Header(idx_state=idx_state, idx_country=idx_country, idx_confirmed=idx_confirmed,
                      idx_fatal=idx_fatal, idx_recovered=idx_recovered, idx_county=idx_county,
                      idx_lat=idx_lat, idx_lon=idx_lon, idx_active=idx_active)

    @staticmethod
    def default():
        return Header(idx_date=0, idx_county=1, idx_state=2, idx_lat=3, idx_lon=4, idx_confirmed=5, idx_fatal=6)


@dataclass
class DataEntry:
    county: str
    state: str
    country: str
    lat: float
    lon: float
    confirmed: int
    fatal: int
    date_: date

    def to_entry(self) -> str:
        # Matches the order of the convention in README.md
        data = [
            self.date_.strftime("%Y-%m-%d"),
            self.county,
            self.state,
            self.confirmed or "0",
            self.fatal or "0"
        ]

        return ",".join([txt.strip() for txt in data])

    @property
    def can_be_used(self) -> bool:
        # Recovered cases is counted as a whole entry
        if "recovered" in self.state.lower():
            return False

        return self.country == "US"

    @staticmethod
    def parse(header: Header, date_: date, data: List[str]):
        country = get_list_by_idx(data, header.idx_country, "")
        county = get_list_by_idx(data, header.idx_county, "")
        state = get_list_by_idx(data, header.idx_state, "").strip()

        # For some legacy data, state and county are not separated yet
        if country == "US":
            if ", " in state:
                county, state = state.split(", ")
                county = county.strip().replace("County", "")
            elif county.lower() == "madison":
                county = "Dane"

        if state in state_full_to_abbr:
            state = state_full_to_abbr[state]

        return DataEntry(
            date_=date_, county=county, state=state, country=country,
            lat=get_list_by_idx(data, header.idx_lat, ""),
            lon=get_list_by_idx(data, header.idx_lon, ""),
            confirmed=get_list_by_idx(data, header.idx_confirmed, 0),
            fatal=get_list_by_idx(data, header.idx_fatal, 0))


def download_csv(date_: date) -> List[DataEntry]:
    """
    Download the content at `url` and convert it to data entry.

    Terminates the program if the status code is not 200.
    """
    ret = []

    # Get the file
    url = f"{URL_PARENT}/{date_.strftime('%m-%d-%Y')}.csv"
    response = requests.get(url)
    if not response.ok:
        print(f"File download failed. (<{response.status_code}> {url})")

    # Parse to data entry
    data = response.content.decode('utf-8').splitlines()

    header = Header.parse(data[0].split(","))

    csv_reader = csv.reader(data[1:], delimiter=",", quotechar="\"")
    for entry in csv_reader:
        de = DataEntry.parse(header, date_, entry)
        if de.can_be_used:
            ret.append(de)

    return ret


def write_data(dir_: str, data: List[DataEntry]):
    """Write the data entries in `data` into a single file."""

    def _write_to_file_(content: str):
        with open(f"{dir_}/data.csv", "a+", encoding="utf-8") as f:
            f.write(content)

    entry_count = len(data)

    # Write header
    # _write_to_file_(Header.default().to_header_str() + "\n")

    temp = ""

    for idx, entry in enumerate(data, start=0):
        temp += entry.to_entry() + "\n"

        if idx % 10000 == 0:
            print(f"{idx} / {entry_count} ({idx / entry_count:.2%})")
            _write_to_file_(temp)
            temp = ""

    _write_to_file_(temp)


def get_all_data() -> List[DataEntry]:
    data = []

    # Use multi processing to boost the speed
    with ThreadPoolExecutor(max_workers=10, thread_name_prefix="ProcessData") as executor:
        futures = []
        current = DATE_START
        while current <= date.today():
            futures.append(executor.submit(download_csv, current))
            current += timedelta(days=1)

        # Non-lock call & Free resources when execution is done
        executor.shutdown(False)

        for completed in futures:
            data.extend(completed.result())

    return data


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

    print(f"Downloading data...")
    data = get_all_data()
    print(f"Downloading data...Completed")

    print(f"Writing the data to <{dir_}>...")
    write_data(dir_, data)
    print(f"Writing the data to <{dir_}>...Completed")


if __name__ == '__main__':
    if len(sys.argv) != 2:
        output_dir = datetime.now().strftime("%Y-%m-%d %H-%M-%S")
    else:
        output_dir = sys.argv[1]

    print(f"Output directory: {output_dir}")
    main(output_dir)
