# COVID-19 Analyzer

COVID-19 data analyzer for the final project of UW-Madison CS 400 Spring 2020 ateam #87.

### Team member

- Ray Hsieh `XT216` [github.com/RaenonX](https://github.com/RaenonX)
- Andy Lin `XT219` [github.com/yayen-lin](https://github.com/yayen-lin)
- Shari Sung `XT248` [github.com/sharisung](https://github.com/sharisung) / [github.com/tsung6](https://github.com/tsung6)
- Mark Wu `XT119` [github.com/markwu7](https://github.com/markwu7)

### References

Main case count data source
- https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_daily_reports
    - From JHU CSSE
    
Population Data
- https://simplemaps.com/data/us-cities
    - Used to calculate cases/100K population.
    
# Notes
- [About the case data](https://github.com/RaenonX/CS400-AT87/issues/5)
- [About commit message](https://github.com/RaenonX/CS400-AT87/issues/4)

# Bugs (copied from GitHub Issues)

### #30 - GUI not filling vertically after resizing

#### Description
Elements of the filtered section not docking to the bottom when the main form is resized. 

#### Expected Behavior
Elements for the filtered section should be docked to the bottom when resizing.

#### Steps to reproduce
1. Resize the form

#### Notes
- The elements seem to have the max height limit. However, `getMaxWidth()` returns `-1`(not limited).
- `VBox.setVgrow()` not working.

# Features

### Automatically download data from the data source

#### Description
Download the data and store it locally and use it on application startup.

If failed to download, use the latest locally stored data file.
