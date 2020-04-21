import csv


def main():
    pop_count = {}

    print("Reading population count...")
    with open("../.res/data/uspops_org.csv", "r", encoding="utf-8") as f:
        csv_reader = csv.reader(f.readlines()[1:], delimiter=",", quotechar="\"")
        for entry in csv_reader:
            state_abbr = entry[2]
            county = entry[5]
            population = int(float(entry[10]))

            if state_abbr not in pop_count:
                pop_count[state_abbr] = {}

            if county not in pop_count[state_abbr]:
                pop_count[state_abbr][county] = 0

            pop_count[state_abbr][county] += population

    print("Writing minimized population file...")
    with open("../.res/data/uspops.csv", "a+", encoding="utf-8") as f:
        # Write header
        f.write("state,county,population\n")

        for state, county_pop in pop_count.items():
            print(f"Writing {state}...")
            s = ""
            for county, pop in county_pop.items():
                s += f"{state},{county},{pop}\n"
            f.write(s)


if __name__ == '__main__':
    main()
