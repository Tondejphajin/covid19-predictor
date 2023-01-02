# COVID-19 Progression Predictor

The objective of this project is to raise public awareness about the impact of emergency policies on the COVID-19 pandemic situation in different countries. The program, written in Java, processes a time series of cumulative confirmed cases in various countries and makes projections based on certain scenarios. A brief report is also included to provide further information and analysis. The goal of this project is to educate the public about the potential consequences of national emergency policies on the COVID-19 pandemic and encourage informed decision-making.

## Data source

The relevant data set for this project can be found in the time_series_covid19_confirmed_global.csv file. This file contains the cumulative number of confirmed COVID-19 cases in different countries on a daily basis, starting from January 22, 2020 until the current date (which is updated daily with the latest available data). Each row of the CSV file represents the time series of cumulative confirmed cases reported in a specific area. Please note that for some countries, the data for various places (such as provinces or states) may be distributed across multiple rows in the file. You can download and open the file to see the specific information it contains.

## Data preprocessing

The getInfected() method in the main program reads the CSV file, extracts the time series of confirmed cases for relevant countries, stores them in the appropriate data structure, and returns it. The CountryInfected.java class represents the time series for a single country, with private data members including a String for the country name and an int array for the numbers of confirmed cases on different dates (starting from January 22, 2020). The getInfected() method extracts the time series of confirmed cases for all relevant countries from the CSV file, stores them in an array of CountryInfected objects, and returns it. This method also aggregates all the data from the various rows in the CSV file into a single time series for each country, and excludes any countries whose cumulative cases have never exceeded 100. In summary, getInfected() returns an array of CountryInfected objects, with the size corresponding to the number of countries with cumulative cases greater than 100 up to the current date. Note: Some country names in the CSV file, such as "Holy See," "Korea, South," and "Taiwan*," are not formatted correctly. The getInfected() method changes these names to "Vatican City," "South Korea," and "Taiwan," respectively, before storing the corrected names in the country data member of each element in the returned array.

## Predictive models
Two prediction models are implemented for the two following scenarios.
1. Do-nothing model: This model represents the natural progression of the COVID-19 outbreak with no interventions (such as lockdowns or vaccination) being applied. It fictitiously models the natural course by calculating the new number of infected cases based on the average ratios of numbers on consecutive days over the past four days.
2. S-curve model: This model represents the potential results of proper interventions from governmental policies, such as closures of schools and businesses, work-from-home orders, and encouragement of social distancing and vaccination. It consists of non-negative parameters (S, D, L, M) and projects the number of cases on a given day using a specific formula.

![Example of S-Curve](https://imgur.com/a/iT91vKW)
## Running the program

Instructions for how to run the COVID-19 progression predictor program.

## Built With

List of tools and libraries used in the project, including Java.

## Contributing

Instructions for how to contribute to the project.

## License

Include information about the license that the project is released under.

## Acknowledgments

Credit to anyone whose code was used, or any other resources that were helpful in the creation of the project.