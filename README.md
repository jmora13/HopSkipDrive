# HopSkipDrive

1. List of third party libraries used
  - Retrofit, Dagger - Hilt, Room DB, Google Play Services Location / Maps, Material Components
  
2. For Android, include a link to APK file
  - https://drive.google.com/drive/folders/1LLopjhUvwd3Q6sFSwnMy0swd7ZfJLddk?usp=sharing
  
3. Any notes and improvements you'd make
  - The OnClick function in the main recyclerview to start the Ride Details activity only registers
    if you click the holder (surrounding area), but won't register if you click the recycler view
  - I used put/getExtra to send all of the data from the Ride List Adapter to the Ride Details activity,
    which looks a little messy. It probably would've been cleaner to call the data from the database, 
    but I would've had to parse the date and time strings again. To avoid putting more parsing functions
    in the main, I chose to just send the data from the adapter. I'm sure there's a cleaner way to pass
    the data between classes however.
  - As far as the testability of my code, it definitely has a lot of room for improvement. I used the 
    double bang operator (!!) everywhere, which means that the code would only work under the exact 
    conditions for the project, but if the data changes, then the app which surely crash. It would be 
    beneficial to set default values using the elvis operator (?:). It would also would've been
    useful to write unit tests.
  - I set the PrimaryKey of the data tables to the "startsAt" variable because the tripId was the same
    between all the ride items. 
  - There are 1-2 ride items that have the same coordinates, so the Google Maps fragment placed them in
    the same spot.
