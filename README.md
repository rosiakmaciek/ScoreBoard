Assumptions:

- The main purpose of the application is to work with the results table, summaries will be generated less frequently,
- Country name input is always correct and in Pascal Case, provided from the frontend using, for example, a drop-down list.
However, I've added an additional check to verify whether a country exists in case there are differences between the country list on the frontend and the one provided by the Java libraries,
- Updated results will always be provided as an integer,
- Correcting the result is possible – resetting, providing a lower number of goals than in the previous update, or updating with the same result (we assume that Scout may have made an error).
